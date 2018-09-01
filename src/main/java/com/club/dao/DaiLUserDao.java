package com.club.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.entity.DaiLUser;
import com.club.entity.Recharge;

public interface DaiLUserDao {
	//分页查询代理用户信息
	List<DaiLUser> findDaiLUserByPages(HashMap<String, Object> map);
	//代理总绑定人数
	Integer findAllUserCount(Integer dail_id);
	//查找用户总消费(不显示扣量)
	Integer findUserAllBuy(Integer userId);
	//分页查询直充明细
	List<Recharge> findRechargeByPages(HashMap<String,Object> map);
	//充值明细总数(不含扣量)
	Integer findAllBuyCount(Integer dail_id);
	//查找分成
	Integer findScaleById(Integer userId);
	//今日直充卡数
	Integer todayAllCard(HashMap<String, Object> map);
	//查找代理总充值(可提现)
	Integer daiLAllBuy(HashMap<String, Object> map);
	//查找用户代理
	Integer findDaiLId(Integer userId);
	//修改用户代理
	void changeDaiLId(@Param("dail_id")Integer daiLId,@Param("user_id")Integer userId);
	//用户绑定代理
	void insertDaiLUser(HashMap<String, Object> map);
}
