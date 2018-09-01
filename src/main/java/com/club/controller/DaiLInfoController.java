package com.club.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.club.constant.Cnst;
import com.club.dao.UserDao;
import com.club.entity.JsonResult;
import com.club.entity.User;
import com.club.service.DaiLInfoService;
import com.club.util.GameUtil;

@Controller
public class DaiLInfoController {
	@Resource
	private DaiLInfoService daiLInfoService;
	@Resource
	private UserDao userDao;
	@RequestMapping("/managerCenter")
	@ResponseBody
	public JsonResult managerCenter(Integer userId){
		try{
			System.out.println("=====================>管理中心"+userId);
			if(userId==null)
				return new JsonResult("参数有null~");
			return new JsonResult(daiLInfoService.findDaiLById(userId));
		}catch(Exception e){
			e.printStackTrace();
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	}
	@RequestMapping("/bindUsers")
	@ResponseBody
	public JsonResult bindUsers(Integer userId,Integer page,Integer currentPage){
		try{
			System.out.println("=====================>已绑定用户"+userId+page+currentPage);
			if(page==null||userId==null||currentPage==null)
				return new JsonResult("参数有null~");
			JSONObject info = daiLInfoService.findBindUser(userId, page, currentPage);
			return new JsonResult(info);
		}catch(Exception e){
			e.printStackTrace();
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	}
	@RequestMapping("/recharge")
	@ResponseBody
	public JsonResult recharge(Integer userId,Integer page,Integer currentPage){
		try{
			System.out.println("==================>充值明细"+userId+page+currentPage);
			if(page==null||userId==null||currentPage==null)
				return new JsonResult("参数有null~");
			JSONObject info = daiLInfoService.findAllBuyInfo(userId, page, currentPage);
			return new JsonResult(info);
		}catch(Exception e){
			e.printStackTrace();
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	}
	@RequestMapping("/findDaiL")
	@ResponseBody
	public JsonResult findDaiL(Integer userId,Integer queryUserId){
		try{
			System.out.println("==========================>用户归属"+userId+queryUserId);
			if(queryUserId==null||userId==null)
//				return new JsonResult("参数有null~");
				return new JsonResult("13");
			User user = userDao.findUser(queryUserId);
			if(user==null){
//				return new JsonResult("用户不存在");
				return new JsonResult("14");
			}
			Integer daiLId = daiLInfoService.findUserDai(queryUserId);
			JSONObject info = new JSONObject();
			info.put("openId", user.getUSER_ID());
			info.put("openImg", user.getUSER_IMG());
			info.put("openName", user.getUSER_NAME());
			if(daiLId==null){
				info.put("daiLImg", "");
				info.put("daiLName", "");
				info.put("daiLId", "");
			}else{
				Map<String, Object> map = userDao.findDaiLInfo(daiLId);
				info.put("daiLImg", map.get("head"));
				info.put("daiLName", map.get("userName"));
				info.put("daiLId", userDao.findUserId(daiLId));
			}
			return new JsonResult(info);
		}catch(Exception e){
			e.printStackTrace();
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	} 
	@RequestMapping("/findChangeDaiL")
	@ResponseBody
	public JsonResult findChangeDaiL(Integer userId,Integer queryUserId){
		try{
			System.out.println("==========================>要切换的代理信息"+userId+queryUserId);
			if(queryUserId==null||userId==null)
				return new JsonResult("参数有null~");
			JSONObject info = new JSONObject();
			Integer daiL = userDao.findTrueId(queryUserId);
			if(daiL==null){
//				return new JsonResult("该代理不存在，请核实");
				return new JsonResult("15");
			}
			Map<String, Object> map = userDao.findDaiLInfo(daiL);
			info.put("daiLImg", map.get("head"));
			info.put("daiLName", map.get("userName"));
			info.put("daiLId",queryUserId);
			return new JsonResult(info);
		}catch(Exception e){
			e.printStackTrace();
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	} 
	@RequestMapping("/changeDaiL")
	@ResponseBody
	public JsonResult changeDaiL(Integer userId,Integer queryUserId,Integer chageId){
		try{
			System.out.println("===============================>用户归属调整"+userId+queryUserId+chageId);
			if(queryUserId==null||userId==null||chageId==null)
//				return new JsonResult("参数有null~");
				return new JsonResult("13");
			if(!GameUtil.checkPower(userDao.findDaiLPower(userId))){
//				return new JsonResult("权限不足");
				return new JsonResult("16");
			}
			Integer id = userDao.findTrueId	(chageId);
			if(id==null){
//				return new JsonResult("不存在该代理，请核实");
				return new JsonResult("17");
			}
			daiLInfoService.changeUserDai(id, queryUserId);
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	}
}
