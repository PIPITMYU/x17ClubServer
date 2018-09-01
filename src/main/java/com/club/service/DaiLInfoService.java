package com.club.service;

import com.alibaba.fastjson.JSONObject;

public interface DaiLInfoService {
	//查找dail信息
	public JSONObject findDaiLById(Integer dail_id);
	//已绑定用户
	public JSONObject findBindUser(Integer userId,Integer size,Integer start);
	//查找充值明细
	public JSONObject findAllBuyInfo(Integer userId,Integer size,Integer start);
	//查找用户代理
	public Integer findUserDai(Integer userId);
	//修改用户代理
	public void changeUserDai(Integer daiLId,Integer userId);
}
