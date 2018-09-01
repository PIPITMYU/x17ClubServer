package com.club.controller;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.club.constant.Cnst;
import com.club.dao.DaiLUserDao;
import com.club.dao.UserDao;
import com.club.dao.ZongDaiLUserDao;
import com.club.entity.JsonResult;
import com.club.util.GameUtil;

@Controller
public class PayContorller {
	@Resource
	private ZongDaiLUserDao zongDaiLUserDao;
	@Resource
	private DaiLUserDao daiLUserDao;
	@Resource
	private UserDao userDao;
	//支付接口
	@RequestMapping("/payService")
	@ResponseBody
	public synchronized String payService(){
		String orderNum = "sss";
		
		//判断订单有效性
		JSONArray orderList = GameUtil.getPayList();
		if(!orderList.contains(orderNum)){
			//订单无效
			return "success";
		}
		//获取userId 订单号规则为 userID+时间戳
		Integer userId = Integer.parseInt(orderNum.substring(0, 6));
		Integer white = zongDaiLUserDao.findWhiteById(userId);
		HashMap<String,Object> map = new HashMap<String,Object>();
		Integer daiLId = daiLUserDao.findDaiLId(userId);
		map.put("dail_id", daiLId);
		map.put("user_id", userId);
		map.put("money", 1);//
		map.put("card", 1);//
		map.put("orderNum", orderNum);
		map.put("time", 1);//
		map.put("ifcash", 0);
		//判断用户是否为白名单
		if(white==1){
			map.put("ifkou", 0);
		}else{
			Integer currentKou = GameUtil.getKouInfo(Cnst.REDIS_PAY_CURRENTKOU);
			Integer kou = GameUtil.getKouInfo(Cnst.REDIS_PAY_KOU);
			if(currentKou>=kou){
				//开始扣量
				map.put("ifkou", 1);
			}else{
				//不扣量
				map.put("ifkou", 0);
			}
			currentKou+=1;
			if(currentKou==10){
				//归0
				currentKou = 0;
			}
			GameUtil.setKouInfo(Cnst.REDIS_PAY_CURRENTKOU, 0);
		}
		//存储订单
		zongDaiLUserDao.saveRecharge(map);
		//更新user账户
		userDao.changePayMoney(userId, 1);
		//更新redis user账户 走大接口暂时不用更新
		
		//处理订单 更新redis订单列表
		orderList.remove(orderNum);
		GameUtil.setPayList(orderList);
		return "success";
	}
	@RequestMapping("/clickPay")
	@ResponseBody
	public JsonResult clickPay(Integer userId){
		try{
			System.out.println("点击支付按钮==================>"+userId);
			Integer daiL = daiLUserDao.findDaiLId(userId);
			JSONObject info = new JSONObject();
			if(daiL==null){
				info.put("reqState", 0);
				return new JsonResult(info);
			}
			info.put("reqState", 1);
			return new JsonResult(info);
		}catch(Exception e){
			e.printStackTrace();
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	}
	@RequestMapping("/bindDaiL")
	@ResponseBody
	public JsonResult bindDaiL(Integer userId,Integer inviteCode){
		try{
			System.out.println("用户绑定代理============>"+userId+inviteCode);
			Integer id = userDao.findTrueId	(inviteCode);
			if(id==null){
//				return new JsonResult("不存在该代理，请核实");
				return new JsonResult("17");
			}
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("dail_id", id);
			map.put("user_id", userId);
			map.put("time", System.currentTimeMillis());
			map.put("white",0);
			daiLUserDao.insertDaiLUser(map);
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	}
	@RequestMapping("/getPayInfo")
	@ResponseBody
	public JsonResult getPayInfo(Integer userId){
		try{
			System.out.println("获取支付页面info====================>"+userId);
			JSONObject info = new JSONObject();
			JSONArray array = new JSONArray();
			JSONObject j = new JSONObject();
			j.put("card", 4);
			j.put("money", 4);
			array.add(j);
			JSONObject j1 = new JSONObject();
			j1.put("card", 8);
			j1.put("money", 8);
			array.add(j1);
			JSONObject j2 = new JSONObject();
			j2.put("card", 12);
			j2.put("money", 12);
			array.add(j2);
			JSONObject j3 = new JSONObject();
			j3.put("card", 24);
			j3.put("money", 24);
			array.add(j3);
			return new JsonResult(info);
		}catch(Exception e){
			e.printStackTrace();
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	}
	@RequestMapping("/payMoney")
	@ResponseBody
	public JsonResult payMoney(Integer userId,Integer card,Integer money){
		try{
			System.out.println("我要支付======================>"+userId+money);
			String orderNum = String.valueOf(userId).concat(String.valueOf(System.currentTimeMillis()));
			JSONArray orderList = GameUtil.getPayList();
			if(orderList.contains(orderNum)){
				//重复订单号 
//				return new JsonResult("支付出现问题，请联系客服");
				return new JsonResult("18");
			}
			orderList.add(orderNum);
			//更新订单号
			GameUtil.setPayList(orderList);
			JSONObject info = new JSONObject();
			info.put("reqHtml", "www.baidu.com");
			return new JsonResult(info);
		}catch(Exception e){
			e.printStackTrace();
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	}
	@RequestMapping("/JiashujuPay")
	@ResponseBody
	public JsonResult JiashujuPay(Integer userId,Integer card,Integer money){
		try{
			System.out.println("支付假数据======================>"+userId+money+card);
			String orderNum = String.valueOf(userId).concat(String.valueOf(System.currentTimeMillis()));
			//判断订单有效性
			JSONArray orderList = GameUtil.getPayList();
			if(orderList.contains(orderNum)){
				//重复订单号 
//				return new JsonResult("支付出现问题，请联系客服");
				return new JsonResult("18");
			}
			//获取userId 订单号规则为 userID+时间戳
			Integer white = zongDaiLUserDao.findWhiteById(userId);
			HashMap<String,Object> map = new HashMap<String,Object>();
			Integer daiLId = daiLUserDao.findDaiLId(userId);
			map.put("dail_id", daiLId);
			map.put("user_id", userId);
			map.put("money", money);//
			map.put("card", card);//
			map.put("orderNum", orderNum);
			map.put("time", System.currentTimeMillis());//
			map.put("ifcash", 0);
			//判断用户是否为白名单
			if(white==1){
				map.put("ifkou", 0);
			}else{
				Integer currentKou = GameUtil.getKouInfo(Cnst.REDIS_PAY_CURRENTKOU);
				Integer kou = GameUtil.getKouInfo(Cnst.REDIS_PAY_KOU);
				if(currentKou>=kou){
					//开始扣量
					map.put("ifkou", 1);
				}else{
					//不扣量
					map.put("ifkou", 0);
				}
				currentKou+=1;
				if(currentKou==10){
					//归0
					currentKou = 0;
				}
				GameUtil.setKouInfo(Cnst.REDIS_PAY_CURRENTKOU, currentKou);
			}
			//存储订单
			zongDaiLUserDao.saveRecharge(map);
			//更新user账户
			userDao.changePayMoney(userId, card);
			//更新redis user账户 走大接口暂时不用更新
			
			//处理订单 更新redis订单列表
			orderList.remove(orderNum);
			GameUtil.setPayList(orderList);
			JSONObject info = new JSONObject();
			info.put("reqState", 1);
			return new JsonResult(info);
		}catch(Exception e){
			e.printStackTrace();
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	}
}
