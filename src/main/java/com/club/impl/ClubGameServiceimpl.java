package com.club.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.club.constant.Cnst;
import com.club.dao.ClubGameDao;
import com.club.dao.UserDao;
import com.club.entity.User;
import com.club.redis.StringUtils;
import com.club.service.ClubGameService;
import com.club.util.GameUtil;

@Service("clubGameService")
public class ClubGameServiceimpl implements ClubGameService{
	@Resource
	private ClubGameDao clubGameDao;
	@Resource
	private UserDao userDao;
	//今日活跃人数
	@Override
	public List<Integer> todayPerson(Integer clubId) {
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("clubId", clubId);
		map.put("morning", GameUtil.getTimesmorning());
		map.put("night", GameUtil.getTimesNight());
		List<Integer> users = clubGameDao.todayPerson(map);	
		return users;
	}
	//今日局数
	@Override
	public Integer todayGames(Integer clubId) {
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("clubId", clubId);
		map.put("morning", GameUtil.getTimesmorning());
		map.put("night", GameUtil.getTimesNight());
		Integer games = clubGameDao.todayGames(map);	
		return games;
	}
	@Override
	public JSONObject clubUsersGame(Integer clubId, String order, String desc,
			Integer page,Long morning,Long night) {
		JSONObject info = new JSONObject();
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("clubId", clubId);
		map.put("morning", morning);
		map.put("night", night);
		map.put("userId", 0);
		List<Integer> activeUsers = clubGameDao.todayPerson(map);
		//页数通过前面去获取
		Integer games = clubGameDao.todayGames(map);
		Integer pages=0;
		if(games%page==0){
			pages=games/page;
		}else{
			pages=games/page+1;
		}
		info.put("pages",pages);//总页数
		JSONArray users = new JSONArray();

		for(int i=0;i<activeUsers.size();i++){
			JSONObject j = new JSONObject();
			Integer userId = activeUsers.get(i);
			j.put("userId", userId);
			User findUser = userDao.findUser(userId);
			j.put("userName",findUser.getUSER_NAME());
			j.put("userImg", findUser.getUSER_IMG());
			map.replace("userId", userId);
			//通过缓存获取玩家局数
			j.put("juNum",clubGameDao.allNum(map));
			//redis调取个人分数
			String key2 = Cnst.REDIS_CLUB_TODAYSCORE_ROE_USER+clubId+"_"+userId+"_"+morning;
			if(GameUtil.exists(key2)){
	  			Integer score = GameUtil.getObject(key2,Integer.class);
	  			j.put("score", score);
	  		}else{
	  			j.put("score", 0);
	  		}
			Integer status = clubGameDao.findHeXiao(map);
			j.put("heXiaoStatus",status==null?0:1);
			users.add(j);
		}
		info.put("users", users);
		return info;
	}
	@Override
	public void addHeXiao(Integer clubId, Integer userId, Long morning) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("clubId", clubId);
		map.put("userId", userId);
		map.put("morning", morning);
		clubGameDao.addHeXiao(map);
	}
	@Override
	public Integer allHeXiao(Integer clubId, Long morning) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("clubId", clubId);
		map.put("morning", morning);
		Integer all = clubGameDao.allHeXiao(map);
		if(all==null)
			return 0;
		return all;
	}
	
}
