package com.club.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.club.dao.CashDao;
import com.club.dao.UserDao;
import com.club.entity.Cash;
import com.club.service.CashService;
@Service("cashService")
public class CashServiceimpl implements CashService{
	@Resource
	private CashDao cashDao;
	@Resource
	private UserDao userDao;
	//申请提现
	//提现金额 大于100 24小时前发生的订单 
	@Override
	public void applyCash(HashMap<String, Object> map,Long time1) {
		cashDao.insertCash(map);
		map.replace("time", time1);
		cashDao.changeIfCashState(map);
	}
	//同意提现
	@Override
	public void agreeCash(Integer userId, String cashCode) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dail_id", userId);
		map.put("cashCode", cashCode);
		cashDao.agreeCash(map);
	}
	//已体现列表
	@Override
	public JSONObject haveCashList(Integer userId, Integer size, Integer start) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", (start-1)*size);
		map.put("size", size);
		List<Cash> cashs = cashDao.findHaveCash(map);
		JSONObject info = new JSONObject();
		JSONArray array =new JSONArray();
		for(Cash c:cashs){
			JSONObject j = new JSONObject();
			Map<String,Object> map1 = new HashMap<String, Object>();
			map1 = userDao.findDaiLInfo(c.getDail_id());
			j.put("openId",map1.get("binding_playerId"));
			j.put("openName", map1.get("userName"));
			j.put("openHead", map1.get("head"));
			j.put("openTime", c.getTime());
			j.put("openState", c.getState());
			j.put("openCode",c.getCashCode());
			j.put("openMoney", c.getMoney());
			array.add(j);
		}
		info.put("cashs", array);
		info.put("currentPage", start);
		Integer total = cashDao.haveCashCount();
		info.put("pages", total/size+1);
		info.put("total", total);
		return info;
	}
	@Override
	public JSONObject noCashList(Integer userId, Integer size, Integer start) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", (start-1)*size);
		map.put("size", size);
		List<Cash> cashs = cashDao.findNoCash(map);
		JSONObject info = new JSONObject();
		JSONArray array =new JSONArray();
		for(Cash c:cashs){
			JSONObject j = new JSONObject();
			Map<String,Object> map1 = new HashMap<String, Object>();
			map1 = userDao.findDaiLInfo(c.getDail_id());
			j.put("openId",map1.get("binding_playerId"));
			j.put("openName", map1.get("userName"));
			j.put("openHead", map1.get("head"));
			j.put("openTime", c.getTime());
			j.put("openState", c.getState());
			j.put("openCode",c.getCashCode());
			j.put("openMoney", c.getMoney());
			array.add(j);
		}
		info.put("cashs", array);
		info.put("currentPage", start);
		Integer total = cashDao.noCashCount();
		info.put("pages", total/size+1);
		info.put("total", total);
		return info;
	}

}
