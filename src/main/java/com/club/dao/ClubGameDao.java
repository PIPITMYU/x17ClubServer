package com.club.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ClubGameDao {
	//今日活跃人数
	List<Integer> todayPerson(HashMap<String, Object> map);
	//今日局数
	Integer todayGames(HashMap<String, Object> map);
	//个人比赛总局数
	Integer allNum(HashMap<String, Object> map);
	//个人比赛总分
//	Integer allScore(HashMap<String, Object> map);
	//查看核销状态
	Integer findHeXiao(HashMap<String, Object> map);
	//用户核销
	void addHeXiao(HashMap<String, Object> map);
	//今日核销数
	Integer allHeXiao(HashMap<String, Object> map);
}
