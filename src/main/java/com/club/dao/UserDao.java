package com.club.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.entity.DaiL;
import com.club.entity.User;

public interface UserDao {
	//修改玩家库存
	public void updateUserMoney(@Param("id")Integer id,@Param("change")Integer change);
	//查找玩家房卡
	public Integer findMoney(Integer id);
	//查找玩家信息
	public User findUser(Integer userId);
	//查找代理token信息
	String findUserToken(Integer id);
	//查找代理user_id
	Integer findUserId(Integer id);
	//查找daili邀请码
	DaiL findDaiLById(Integer dail_id);
	//查找代理姓名，图片，房卡
	Map<String,Object> findDaiLInfo(Integer userId);
	//查询代理权限
	Integer findDaiLPower(Integer userId);
	//查找真实代理id
	Integer findTrueId(Integer userId);
	//更新充值金额
	void changePayMoney(@Param("id")Integer id,@Param("change")Integer change);
}
