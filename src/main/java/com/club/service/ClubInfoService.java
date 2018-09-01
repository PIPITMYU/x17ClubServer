package com.club.service;



import com.alibaba.fastjson.JSONObject;
import com.club.entity.ClubInfo;
import com.club.entity.RedisClub;


public interface ClubInfoService {
	//获取我的俱乐部
	JSONObject getMyClubs(int userId, Integer integer);
	//创建俱乐部
	void createClub(ClubInfo club,Integer userId,Integer change);
	//库存管理
	void addMoney(Integer clubId,Integer id,Integer addMoney);
	//查看库存
	JSONObject moneyManage(Integer clubId,Integer userId);
	//解散俱乐部
	void deleteClub(Integer clubId, RedisClub redisClub);
	//人员变动预警
	JSONObject haveAction(Integer userId);
	//获取别人创建的俱乐部
	JSONObject getHisClubs(Integer toUserId);
	
}
