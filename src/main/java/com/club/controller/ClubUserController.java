 package com.club.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.club.constant.Cnst;
import com.club.dao.ClubInfoDao;
import com.club.dao.ClubUserDao;
import com.club.dao.UserDao;
import com.club.entity.JsonResult;
import com.club.entity.RedisClub;
import com.club.redis.StringUtils;
import com.club.service.ClubUserService;
import com.club.util.GameUtil;

@Controller
public class ClubUserController {
	@Resource
	private ClubInfoDao clubInfoDao;
	@Resource
	private ClubUserService clubUserService;
	@Resource
	private ClubUserDao clubUserDao;
	@Resource
	private UserDao userDao;
	@RequestMapping("/clubUsers")//成员列表
	@ResponseBody
	public JsonResult clubUsers(Integer clubId,Long userId,Long queryUserId,Integer page){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"登录已过时，重新登录",null,"");
			if(!StringUtils.isNum(clubId+"") ||!StringUtils.isNum(userId+"") ||!StringUtils.isNum(page+"") ){
				return new JsonResult("15");
			}
			Integer user_Id = userDao.findUserId(Integer.valueOf(userId+""));
			System.out.println("成员列表=================>"+clubId+userId+page);
			JSONObject users = clubUserService.findAllUser(clubId, page,user_Id);
			return new JsonResult(users);
		}catch(Exception e){
			System.out.println(e);
			return new JsonResult(e);
		}
		
	}
	@RequestMapping("/clubApply")//入会申请列表
	@ResponseBody
	public JsonResult clubApply(Integer clubId,Long userId,Long queryUserId,Integer page){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"登录已过时，重新登录",null,"");
//			System.out.println("入会申请列表=================>"+clubId+userId+queryUserId+page);
			if(!StringUtils.isNum(clubId+"") ||!StringUtils.isNum(userId+"") ||!StringUtils.isNum(page+"") ){
				return new JsonResult("15");
			}
			JSONObject users = clubUserService.findJoinUser(clubId, page);
			return new JsonResult(users);
		}catch(Exception e){
			System.out.println(e);
			return new JsonResult(e);
		}
	}
	@RequestMapping("/clubLeave")//离会申请列表
	@ResponseBody
	public JsonResult clubLeave(Integer clubId,Long userId,Long queryUserId,Integer page){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"登录已过时，重新登录",null,"");
//			System.out.println("离会申请列表===================>"+clubId+userId+queryUserId+page);
			if(!StringUtils.isNum(clubId+"") ||!StringUtils.isNum(userId+"") ||!StringUtils.isNum(page+"") ){
				return new JsonResult("15");
			}
			JSONObject users = clubUserService.findLeaveUser(clubId, page);
			return new JsonResult(users);
		}catch(Exception e){
			System.out.println(e);
			return new JsonResult(e);
		}
	}
	@RequestMapping("/clubApplyExcuse")//申请处理
	@ResponseBody
	public JsonResult clubApplyExcuse(Integer clubId,Long userId,Long inClubUserId,Integer opeType,Integer applyType){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"登录已过时，重新登录",null,"");
//			System.out.println("申请处理=====================>"+clubId+userId+inClubUserId+opeType+applyType);
			if(!StringUtils.isNum(clubId+"") ||!StringUtils.isNum(userId+"") ||!StringUtils.isNum(inClubUserId+"")
					||!StringUtils.isNum(opeType+"")||!StringUtils.isNum(applyType+"")){
				return new JsonResult("15");
			}
			if(applyType==1&&opeType==1){
				//只有在申请的时候查询
				//通过UserId查询该玩家已经加入的俱乐部数量-不是创建者的
				Integer num = clubUserDao.findClubNumByAddUserId(Integer.valueOf(inClubUserId+""));
//				System.out.println("num====》"+num);
				if(num>=Cnst.maxClub){//其实最多等于3
					//删除申请记录
					clubUserService.refuseJoin(clubId,Integer.valueOf(inClubUserId+""));
					return new JsonResult("10");
				}
				//同意入会
				if(clubUserDao.findAllUser(clubId).size()>=Cnst.maxMember){
//					return new JsonResult("俱乐部成员已满，无法加入");
					return new JsonResult("11");
				}
				clubUserService.passJoin(clubId,Integer.valueOf(inClubUserId+""));
				return new JsonResult(Cnst.SUCCESS);
			}
			if(applyType==1&&opeType==0){
				//拒绝入会
				clubUserService.refuseJoin(clubId,Integer.valueOf(inClubUserId+""));
				return new JsonResult(Cnst.SUCCESS);
			}
			if(applyType==2&&opeType==1){
				//同意离会
				clubUserService.passLeave(clubId,Integer.valueOf(inClubUserId+""));
				return new JsonResult(Cnst.SUCCESS);
			}
			if(applyType==2&&opeType==0){
				//拒绝离会
				clubUserService.refuseLeave(clubId,Integer.valueOf(inClubUserId+""));
				return new JsonResult(Cnst.SUCCESS);
			}
//			return new JsonResult("弄啥咧");
			return new JsonResult("12");
		}catch(Exception e){
			System.out.println(e);
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	}
	@RequestMapping("/deleteUser")//踢出俱乐部
	@ResponseBody
	public JsonResult deleteUser(Integer clubId,Long userId,Long deleteUserId){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"登录已过时，重新登录",null,"");
//			System.out.println("踢出俱乐部================>"+clubId+userId+deleteUserId);
			if(!StringUtils.isNum(clubId+"") ||!StringUtils.isNum(userId+"") ||!StringUtils.isNum(deleteUserId+"") ){
				return new JsonResult("15");
			}
			Integer user_Id = userDao.findUserId(Integer.valueOf(userId+""));
			if(user_Id==null){
				return new JsonResult(15);
			}
			//查看俱乐部是不是创建者
			RedisClub redisClub = GameUtil.getClubInfoByClubId(clubId+"");
			if(redisClub==null){
				redisClub = clubInfoDao.findClubNewByClubId(Integer.valueOf(clubId));// 根据俱乐部id查询
			}
			//俱乐部不存在
			if(redisClub==null){
				return new JsonResult(21);
			}
			//该玩家不是创建者
			if(!redisClub.getCreateId().equals(Long.valueOf(user_Id+""))){
				return new JsonResult(20);
			}
			
			clubUserService.deleteUser(clubId, Integer.valueOf(deleteUserId+""));
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
			System.out.println(e);
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	}
}
