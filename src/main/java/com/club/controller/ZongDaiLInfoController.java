package com.club.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.club.constant.Cnst;
import com.club.dao.UserDao;
import com.club.entity.JsonResult;
import com.club.entity.User;
import com.club.service.ZongDaiLInfoService;
import com.club.util.GameUtil;

@Controller
public class ZongDaiLInfoController {
	@Resource
	private ZongDaiLInfoService zongDaiLInfoService;
	@Resource
	private UserDao userDao;
	@RequestMapping("/Allrecharge")
	@ResponseBody
	public JsonResult allrecharge(Integer userId,Integer queryUserId,Integer page,Integer currentPage){
		try{
			System.out.println("==========================>总代理充值管理"+userId+page+currentPage);
			if(page==null||userId==null||currentPage==null)
//				return new JsonResult("参数有null~");
				return new JsonResult("13");
			if(!GameUtil.checkPower(userDao.findDaiLPower(userId))){
//				return new JsonResult("权限不足");
				return new JsonResult("16");
			}
			return new JsonResult(zongDaiLInfoService.findAllBuy(userId, page, currentPage));
		}catch(Exception e){
			e.printStackTrace();
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	}
	@RequestMapping("/changeKou")
	@ResponseBody
	public JsonResult changeKou(Integer userId,Integer kouState){
		try{
			System.out.println("============================>扣量管理"+userId+kouState);
			if(kouState==null||userId==null)
//				return new JsonResult("参数有null~");
				return new JsonResult("13");
			if(!GameUtil.checkPower(userDao.findDaiLPower(userId))){
//				return new JsonResult("权限不足");
				return new JsonResult("16");
			}
			zongDaiLInfoService.changeKou(kouState);
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	}
	@RequestMapping("/changeKouState")
	@ResponseBody
	public JsonResult changeKouState(Integer userId,Integer queryUserId,Integer DailId,String orderNum){
		try{
			System.out.println("============================>扣量分成切换"+userId+queryUserId+DailId+orderNum);
			if(queryUserId==null||userId==null||DailId==null||orderNum==null)
//				return new JsonResult("参数有null~");
				return new JsonResult("13");
			if(!GameUtil.checkPower(userDao.findDaiLPower(userId))){
				return new JsonResult("16");
			}
			zongDaiLInfoService.changeKouState(orderNum);
			return new JsonResult(Cnst.SUCCESS); 
		}catch(Exception e){
			e.printStackTrace();
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	}
	@RequestMapping("/whiteList")
	@ResponseBody
	public JsonResult whiteList(Integer userId,Integer page,Integer currentPage){
		try{
			System.out.println("===============================>白名单列表"+userId+page+currentPage);
			if(page==null||userId==null||currentPage==null)
//				return new JsonResult("参数有null~");
				return new JsonResult("13");
			if(!GameUtil.checkPower(userDao.findDaiLPower(userId))){
//				return new JsonResult("权限不足");
				return new JsonResult("16");
			}
			return new JsonResult(zongDaiLInfoService.whiteList(page, currentPage));
		}catch(Exception e){
			e.printStackTrace();
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	}
	@RequestMapping("/findOneWhite")
	@ResponseBody
	public JsonResult findOneWhite(Integer userId,Integer queryUserId){
		try{
			System.out.println("==========================>查询个人白名单"+userId+queryUserId);
			if(queryUserId==null||userId==null)
//				return new JsonResult("参数有null~");
				return new JsonResult("13");
			if(!GameUtil.checkPower(userDao.findDaiLPower(userId))){
//				return new JsonResult("权限不足");
				return new JsonResult("16");
			}
			User user = userDao.findUser(queryUserId);
			if(user==null){
//				return new JsonResult("用户不存在");
				return new JsonResult("14");
			}
			Integer white = zongDaiLInfoService.findWhiteById(queryUserId);
			if(white==null){
//				return new JsonResult("该玩家还未绑定代理");
				return new JsonResult("19");
			}
			JSONObject info  = new JSONObject();
			JSONArray array = new JSONArray();
			JSONObject j = new JSONObject();
			j.put("openId", queryUserId);
			j.put("openName", user.getUSER_NAME());
			j.put("openImg", user.getUSER_IMG());
			j.put("whiteState",white);
			array.add(j);
			info.put("users", array);
			return new JsonResult(info);
		}catch(Exception e){
			e.printStackTrace();
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	}
	@RequestMapping("/changeWhiteList")
	@ResponseBody
	public JsonResult changeWhiteList(Integer userId,Integer queryUserId,Integer action){
		try{
			System.out.println("=========================>修改白名单"+userId+queryUserId+action);
			if(queryUserId==null||userId==null||action==null)
//				return new JsonResult("参数有null~");
				return new JsonResult("3");
			if(!GameUtil.checkPower(userDao.findDaiLPower(userId))){
//				return new JsonResult("权限不足");
				return new JsonResult("16");
			}
			User user = userDao.findUser(queryUserId);
			if(user==null){
//				return new JsonResult("用户不存在");
				return new JsonResult("14");
			}
			Integer white = zongDaiLInfoService.findWhiteById(queryUserId);
			if(white==null){
//				return new JsonResult("该玩家还未绑定代理");
				return new JsonResult("19");
			}
			zongDaiLInfoService.chageWhite(queryUserId, action);
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	}
}
