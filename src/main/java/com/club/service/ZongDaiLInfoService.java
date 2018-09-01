package com.club.service;

import com.alibaba.fastjson.JSONObject;

public interface ZongDaiLInfoService {
	//充值管理
	public JSONObject findAllBuy(Integer userId,Integer size,Integer start);
	//扣量切换
	public void changeKouState(String orderNum);
	//修改扣量
	public void changeKou(Integer kou);
	//白名单列表
	public JSONObject whiteList(Integer size,Integer start);
	//查询白名单根据id
	public Integer findWhiteById(Integer userId);
	//修改白名单
	public void chageWhite(Integer userId,Integer white);
	//查看扣量
	public Integer findKou();
}
