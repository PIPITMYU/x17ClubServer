package com.club.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.entity.Recharge;

public interface ZongDaiLUserDao {
	//充值总条数 含扣量
	Integer findAllBuyCountKou();
	//充值详情 含扣量 分页
	List<Recharge> findRechargeByPagesKou(HashMap<String, Object> map);
	//扣量数
	Integer findKouCount();
	//扣量金额
	Integer findKouAll();
	//总订单数
	Integer findOrderCount();
	//总发生金额
	Integer findOrderNum();
	//修改分成---扣量
	void changeKouState(String orderNum);
	//修改扣量
	void changeKou(@Param("kou")Integer kou);
	//白名单列表
	List<Integer> whiteList(HashMap<String, Object> map);
	//根据userid查找白名单
	Integer findWhiteById(Integer userId);
	//修改白名单
	void changeWhite(@Param("userId")Integer userId,@Param("white")Integer white);
	//白名单总数
	Integer allWhiteCount();
	//查看扣量
	Integer findKou();
	
	//插入订单详情
	void saveRecharge(HashMap<String, Object> map);
	//今日订单数
	Integer todayAllCount(HashMap<String, Object> map);
	//今日扣量数
	Integer todayAllKouCount(HashMap<String, Object> map);
	//今日订单总额
	Integer todayAll(HashMap<String, Object> map);
	//今日扣量总额
	Integer todayAllKou(HashMap<String, Object> map);
}
