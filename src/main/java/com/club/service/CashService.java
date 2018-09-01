package com.club.service;

import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;

public interface CashService {
	//申请提现
	void applyCash(HashMap<String, Object> map,Long time);
	//同意提现
	void agreeCash(Integer userId,String cashCode);
	//提现列表
	JSONObject haveCashList(Integer userId,Integer size,Integer start);
	//未提现列表
	JSONObject noCashList(Integer userId, Integer size, Integer start);
}
