package com.club.impl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.club.dao.DaiLUserDao;
import com.club.dao.UserDao;
import com.club.entity.DaiL;
import com.club.entity.DaiLUser;
import com.club.entity.Recharge;
import com.club.entity.User;
import com.club.service.DaiLInfoService;
import com.club.util.GameUtil;
@Service("daiLInfoSercive")
public class DaiLInfoServiceimpl implements DaiLInfoService{
	@Resource
	private UserDao userDao;
	@Resource
	private DaiLUserDao daiLUserDao;
	@Override
	//查找代理邀请码
	public JSONObject findDaiLById(Integer userId) {
		DaiL daiL = userDao.findDaiLById(userId);
		Map<String,Object> daiLInfo = userDao.findDaiLInfo(userId);
		JSONObject info = new JSONObject();
		info.put("userName", daiLInfo.get("userName"));
		info.put("userImg", daiLInfo.get("head"));
		info.put("money", daiLInfo.get("money"));
		info.put("inviteCode", daiL.getInviteCode());
		return info;
	}
	//已绑定用户信息
	@Override
	public JSONObject findBindUser(Integer userId, Integer size, Integer start) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dail_id", userId);
		map.put("start", (start-1)*size);
		map.put("size", size);
		//绑定用户总人数
		Integer total = daiLUserDao.findAllUserCount(userId);
		Integer pages = total/size+1;
		List<DaiLUser> users = daiLUserDao.findDaiLUserByPages(map);
		JSONObject info = new JSONObject();
		JSONArray array = new JSONArray();
		for(int i=0;i<users.size();i++){
			Integer thisId = users.get(i).getUser_id();
			User user = userDao.findUser(thisId);
			JSONObject js = new JSONObject();
			js.put("openId", thisId);
			js.put("openName", user.getUSER_NAME());
			js.put("openImg", user.getUSER_IMG());
			js.put("openCard", user.getMONEY());
			Integer allBuy = daiLUserDao.findUserAllBuy(thisId);
			js.put("openBuy",allBuy==null?0:allBuy);//查找用户消费总额
			js.put("openTIme", users.get(i).getTime());
			array.add(js);
		}
		info.put("users", array);
		info.put("pages", pages);
		info.put("total", total);
		System.out.println(info.toJSONString());
  		return info;
	}
	//查询充值明细
	@Override
	public JSONObject findAllBuyInfo(Integer userId, Integer size, Integer start) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dail_id", userId);
		map.put("start", (start-1)*size);
		map.put("size", size);
		//保留两位小数
		DecimalFormat df = new DecimalFormat("#.00");
		//总页数
		Integer pages = daiLUserDao.findAllBuyCount(userId)/size+1;
		List<Recharge> users = daiLUserDao.findRechargeByPages(map);
		DaiL dail = userDao.findDaiLById(userId);
		double scale = dail.getScale()*0.1;//分成
		Long morning = GameUtil.getTimesmorning();
		map.put("morning", morning);
		map.put("night", morning+86400000);
		Integer todayAllCard = daiLUserDao.todayAllCard(map);//今日总卡数
		map.replace("morning", morning-86400000);
		map.replace("night", morning);
		Integer yesAllCard = daiLUserDao.todayAllCard(map);//昨日总卡数	
		//代理总充值(去扣量，去提现)
		map.put("time", System.currentTimeMillis()-86400000);
		Integer allMoney = daiLUserDao.daiLAllBuy(map);
		if(allMoney==null)
			allMoney = 0;
		Double daiLAll = allMoney*scale;
		JSONObject info = new JSONObject();
		info.put("today", todayAllCard==null?0:todayAllCard);
		info.put("yesterday", yesAllCard==null?0:yesAllCard);
		//可提现金额
		info.put("canCash", daiLAll==null?0:df.format(daiLAll));
		JSONArray array = new JSONArray();
		for(int i=0;i<users.size();i++){
			JSONObject js = new JSONObject();
			Recharge r = users.get(i); 
			js.put("openId", r.getUser_id());
			js.put("openBuy", r.getMoney());
			js.put("openCard", r.getCard());
			js.put("openTime",r.getTime());
			js.put("openGet", df.format(r.getMoney()*scale));
			array.add(js);
		}
		info.put("users", array);
		info.put("pages", pages);
		return info;
	}
	@Override
	public Integer findUserDai(Integer userId) {
		return daiLUserDao.findDaiLId(userId);
	}
	@Override
	public void changeUserDai(Integer daiLId, Integer userId) {
			Integer YuandaiLId = daiLUserDao.findDaiLId(userId);
			if(YuandaiLId==null){
				//未绑定代理
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("dail_id", daiLId);
				map.put("user_id", userId);
				map.put("time", System.currentTimeMillis());
				map.put("white",0);
				daiLUserDao.insertDaiLUser(map);
			}else{
				daiLUserDao.changeDaiLId(daiLId, userId);
			}
		 
	}

}
