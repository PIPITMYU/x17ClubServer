package com.club.dao;

import java.util.HashMap;
import java.util.List;


import org.apache.ibatis.annotations.Param;

import com.club.entity.ClubInfo;
import com.club.entity.RedisClub;

public interface ClubInfoDao {
	//查询我的俱乐部
	public List<ClubInfo> findClubByCreateId(Integer userId);
	//根据名字查询俱乐部
	Integer findClubByClubName(String name);
	//创建俱乐部
	public void createClub(ClubInfo club);
	//把创建者加入俱乐部中
	public void addCreater(HashMap<String, Object> map);
	//房卡管理
	public void addMoney(@Param("clubId")Integer clubId,@Param("change")Integer change);
	//查询俱乐部信息
	ClubInfo findClubByClubId(Integer clubId);
	//解散俱乐部
	public void deleteClubInfo(Integer clubId);
	public void deleteClubUser(Integer clubId);
	//查询是否有申请操作
	Integer haveAction(Integer clubId);
	//根据俱乐部的id去更新俱乐部的限免结束时间（关闭俱乐部限免时间）
	public void updateFreeTimeByClubId(@Param("clubId")Integer clubId, @Param("freeStart")Long freeStart,
			@Param("freeEnd")Long freeEnd);
	//根据俱乐部的id去关闭俱乐部的限免结束时间（关闭俱乐部限免时间）
	public void closeFreeTimeByClubId(@Param("clubId")Integer clubId, @Param("freeEnd")long freeEnd);
	//查询俱乐部信息  --这个存储方式为缓存
	public RedisClub findClubNewByClubId(Integer clubId);
}
