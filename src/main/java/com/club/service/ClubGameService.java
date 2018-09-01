package com.club.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public interface ClubGameService {
	//今日活跃人数
	List<Integer> todayPerson(Integer clubId);
	//今日局数
	Integer todayGames(Integer clubId);
	//战绩列表
	JSONObject clubUsersGame(Integer clubId,String order,String desc,Integer page,Long morning,Long night);
	//用户核销
	void addHeXiao(Integer clubId,Integer userId,Long morning);
	//用户核销数
	Integer allHeXiao(Integer clubId,Long morning);
}
