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
	@RequestMapping("/clubUsers")//��Ա�б�
	@ResponseBody
	public JsonResult clubUsers(Integer clubId,Long userId,Long queryUserId,Integer page){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"��¼�ѹ�ʱ�����µ�¼",null,"");
			if(!StringUtils.isNum(clubId+"") ||!StringUtils.isNum(userId+"") ||!StringUtils.isNum(page+"") ){
				return new JsonResult("15");
			}
			Integer user_Id = userDao.findUserId(Integer.valueOf(userId+""));
			System.out.println("��Ա�б�=================>"+clubId+userId+page);
			JSONObject users = clubUserService.findAllUser(clubId, page,user_Id);
			return new JsonResult(users);
		}catch(Exception e){
			System.out.println(e);
			return new JsonResult(e);
		}
		
	}
	@RequestMapping("/clubApply")//��������б�
	@ResponseBody
	public JsonResult clubApply(Integer clubId,Long userId,Long queryUserId,Integer page){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"��¼�ѹ�ʱ�����µ�¼",null,"");
//			System.out.println("��������б�=================>"+clubId+userId+queryUserId+page);
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
	@RequestMapping("/clubLeave")//��������б�
	@ResponseBody
	public JsonResult clubLeave(Integer clubId,Long userId,Long queryUserId,Integer page){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"��¼�ѹ�ʱ�����µ�¼",null,"");
//			System.out.println("��������б�===================>"+clubId+userId+queryUserId+page);
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
	@RequestMapping("/clubApplyExcuse")//���봦��
	@ResponseBody
	public JsonResult clubApplyExcuse(Integer clubId,Long userId,Long inClubUserId,Integer opeType,Integer applyType){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"��¼�ѹ�ʱ�����µ�¼",null,"");
//			System.out.println("���봦��=====================>"+clubId+userId+inClubUserId+opeType+applyType);
			if(!StringUtils.isNum(clubId+"") ||!StringUtils.isNum(userId+"") ||!StringUtils.isNum(inClubUserId+"")
					||!StringUtils.isNum(opeType+"")||!StringUtils.isNum(applyType+"")){
				return new JsonResult("15");
			}
			if(applyType==1&&opeType==1){
				//ֻ���������ʱ���ѯ
				//ͨ��UserId��ѯ������Ѿ�����ľ��ֲ�����-���Ǵ����ߵ�
				Integer num = clubUserDao.findClubNumByAddUserId(Integer.valueOf(inClubUserId+""));
//				System.out.println("num====��"+num);
				if(num>=Cnst.maxClub){//��ʵ������3
					//ɾ�������¼
					clubUserService.refuseJoin(clubId,Integer.valueOf(inClubUserId+""));
					return new JsonResult("10");
				}
				//ͬ�����
				if(clubUserDao.findAllUser(clubId).size()>=Cnst.maxMember){
//					return new JsonResult("���ֲ���Ա�������޷�����");
					return new JsonResult("11");
				}
				clubUserService.passJoin(clubId,Integer.valueOf(inClubUserId+""));
				return new JsonResult(Cnst.SUCCESS);
			}
			if(applyType==1&&opeType==0){
				//�ܾ����
				clubUserService.refuseJoin(clubId,Integer.valueOf(inClubUserId+""));
				return new JsonResult(Cnst.SUCCESS);
			}
			if(applyType==2&&opeType==1){
				//ͬ�����
				clubUserService.passLeave(clubId,Integer.valueOf(inClubUserId+""));
				return new JsonResult(Cnst.SUCCESS);
			}
			if(applyType==2&&opeType==0){
				//�ܾ����
				clubUserService.refuseLeave(clubId,Integer.valueOf(inClubUserId+""));
				return new JsonResult(Cnst.SUCCESS);
			}
//			return new JsonResult("Ūɶ��");
			return new JsonResult("12");
		}catch(Exception e){
			System.out.println(e);
//			return new JsonResult("����ʧ�ܣ����Ժ�����");
			return new JsonResult("3");
		}
	}
	@RequestMapping("/deleteUser")//�߳����ֲ�
	@ResponseBody
	public JsonResult deleteUser(Integer clubId,Long userId,Long deleteUserId){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"��¼�ѹ�ʱ�����µ�¼",null,"");
//			System.out.println("�߳����ֲ�================>"+clubId+userId+deleteUserId);
			if(!StringUtils.isNum(clubId+"") ||!StringUtils.isNum(userId+"") ||!StringUtils.isNum(deleteUserId+"") ){
				return new JsonResult("15");
			}
			Integer user_Id = userDao.findUserId(Integer.valueOf(userId+""));
			if(user_Id==null){
				return new JsonResult(15);
			}
			//�鿴���ֲ��ǲ��Ǵ�����
			RedisClub redisClub = GameUtil.getClubInfoByClubId(clubId+"");
			if(redisClub==null){
				redisClub = clubInfoDao.findClubNewByClubId(Integer.valueOf(clubId));// ���ݾ��ֲ�id��ѯ
			}
			//���ֲ�������
			if(redisClub==null){
				return new JsonResult(21);
			}
			//����Ҳ��Ǵ�����
			if(!redisClub.getCreateId().equals(Long.valueOf(user_Id+""))){
				return new JsonResult(20);
			}
			
			clubUserService.deleteUser(clubId, Integer.valueOf(deleteUserId+""));
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
			System.out.println(e);
//			return new JsonResult("����ʧ�ܣ����Ժ�����");
			return new JsonResult("3");
		}
	}
}
