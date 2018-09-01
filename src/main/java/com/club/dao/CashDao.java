package com.club.dao;

import java.util.HashMap;
import java.util.List;

import com.club.entity.Cash;

public interface CashDao {
	//申请提现
	void insertCash(HashMap<String,Object> map);
	void changeIfCashState(HashMap<String,Object> map);
	//同意提现
	void agreeCash(HashMap<String,Object> map);
	//已提现列表
	List<Cash> findHaveCash(HashMap<String, Object> map);
	//查询今日提现次数
	Integer todayCashCount(HashMap<String, Object> map);
	//最低提现额度
	Integer minCash();
	//总已提现数
	Integer haveCashCount();
	//未提现列表
	List<Cash> findNoCash(HashMap<String, Object> map);
	//未提现总数
	Integer noCashCount();
}
