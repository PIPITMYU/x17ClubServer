package com.club.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ClubUserDao {
	//俱乐部人数
	public List<Integer> findAllUser(Integer clubId);
	//俱乐部申请加入名单
	public List<Integer> findJoinUser(Integer clubId);
	//俱乐部申请退出名单
	public List<Integer> findLeaveUser(Integer clubId);
	//通过加入俱乐部申请 0--1
	public void passJoin(@Param("clubId")Integer clubId,@Param("userId")Integer userId); 
	//拒绝加入俱乐部申请 删除记录
	public void refuseJoin(@Param("clubId")Integer clubId,@Param("userId")Integer userId);
	//通过退出俱乐部申请 删除记录
	public void passLeave(@Param("clubId")Integer clubId,@Param("userId")Integer userId); 
	//拒绝退出俱乐部申请 2--1
	public void refuseLeave(@Param("clubId")Integer clubId,@Param("userId")Integer userId); 
	//踢出俱乐部
	public void deleteUser(@Param("clubId")Integer clubId,@Param("userId")Integer userId);
	//通过玩家ID查询其创建的的俱乐部数量
	public Integer findClubNumByUserId(Integer userId);
	//查询想加入的人数
	public Integer findWantInNum(Integer clubId);
	//查询想退出的人数
	public Integer findWantOutNum(Integer clubId);
	//通过玩家ID查询其加入的俱乐部数量
	public Integer findClubNumByAddUserId(Integer valueOf);
}
