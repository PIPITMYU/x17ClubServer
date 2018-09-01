package com.club.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.club.constant.Cnst;
import com.club.dao.ClubUserDao;
import com.club.dao.UserDao;
import com.club.entity.User;
import com.club.service.ClubUserService;

@Service("clubUserService")
public class ClubUserServiceimpl implements ClubUserService{
	@Resource
	private ClubUserDao clubUserDao;
	@Resource
	private UserDao userDao;
	//成员列表
	@Override
	public JSONObject findAllUser(int clubId,int page,Integer user_id) {
		JSONObject info = new JSONObject();
		List<Integer> list = clubUserDao.findAllUser(clubId);
		//找到想进来的人数
		Integer wantIn = clubUserDao.findWantInNum(clubId);
		//找到想离开的认输
		Integer wantOut = clubUserDao.findWantOutNum(clubId);
		info.put("wantIn", wantIn);
		info.put("wantOut", wantOut);
		
		//没有人
		JSONArray array = new JSONArray();

		int pages=0;
//		if(list==null||list.size()==0){
//			info.put("pages", 0);
//			info.put("users", array);
//			info.put("totalNum", Cnst.maxMember);
//			info.put("currNum",0);
//			return info;
//		}
		if(list.size()%page==0){
			pages=list.size()/page;
		}else{
			pages=list.size()/page+1;
		}
		info.put("pages", pages);
//		JSONArray array = new JSONArray();
		for(int i=0;i<list.size();i++){
			JSONObject j = new JSONObject();
			int userId = list.get(i);
//			if(userId==user_id)
//				continue;
			j.put("userId", userId);
			//从redis中获取玩家姓名，图片 放入j中
			User findUser = userDao.findUser(userId);
			j.put("userImg", findUser.getUSER_IMG());
			j.put("userName", findUser.getUSER_NAME());
			array.add(j);
		}
		info.put("users", array);
		info.put("totalNum", Cnst.maxMember);
		info.put("currNum",list.size());
		return info;
	}
	//申请加入列表
	@Override
	public JSONObject findJoinUser(int clubId, int page) {
		JSONObject info = new JSONObject();
		List<Integer> list = clubUserDao.findJoinUser(clubId);
		//所有的人数
		List<Integer> allList = clubUserDao.findAllUser(clubId);
		info.put("num", allList.size());

//		if(list==null||list.size()==0)
//			return null;
		int pages=0;
		if(list.size()%page==0){
			pages=list.size()/page;
		}else{
			pages=list.size()/page+1;
		}
		info.put("pages", pages);
//		info.put("pages", list.size()%page+1);
		JSONArray array = new JSONArray();
		for(int i=0;i<list.size();i++){
			JSONObject j = new JSONObject();
			int userId = list.get(i);
			j.put("userId", userId);
			User findUser = userDao.findUser(userId);
			//从redis中获取玩家姓名，图片 放入j中
			j.put("userImg", findUser.getUSER_IMG());
			j.put("userName", findUser.getUSER_NAME());
			array.add(j);
		}
		info.put("users", array);
		return info;
	}
	//申请离开列表
	@Override
	public JSONObject findLeaveUser(int clubId, int page) {
		JSONObject info = new JSONObject();
		List<Integer> list = clubUserDao.findLeaveUser(clubId);
		//所有的人数
		List<Integer> allList = clubUserDao.findAllUser(clubId);
		info.put("num", allList.size());
		int pages=0;
		if(list.size()%page==0){
			pages=list.size()/page;
		}else{
			pages=list.size()/page+1;
		}
		info.put("pages", pages);
//		info.put("pages", list.size()%page+1);
		JSONArray array = new JSONArray();
		for(int i=0;i<list.size();i++){
			JSONObject j = new JSONObject();
			int userId = list.get(i);
			j.put("userId", userId);
			User findUser = userDao.findUser(userId);
			//从redis中获取玩家姓名，图片 放入j中
			j.put("userImg", findUser.getUSER_IMG());
			j.put("userName", findUser.getUSER_NAME());
			array.add(j);
		}
		info.put("users", array);
		return info;
	}
	//同意加入
	@Override
	public void passJoin(Integer clubId, Integer userId) {
		clubUserDao.passJoin(clubId, userId);
		//检验人数是否已满 
		//更新redis数据
	}
	//拒绝加入
	@Override
	public void refuseJoin(Integer clubId, Integer userId) {
		clubUserDao.refuseJoin(clubId, userId);
		
	}
	//同意离开
	@Override
	public void passLeave(Integer clubId, Integer userId) {
		clubUserDao.passLeave(clubId, userId);
		//更新redis数据
	}
	//拒绝离开
	@Override
	public void refuseLeave(Integer clubId, Integer userId) {
		clubUserDao.refuseLeave(clubId, userId);
	}
	@Override
	public void deleteUser(Integer clubId, Integer userId) {
		clubUserDao.deleteUser(clubId, userId);
	}
}
