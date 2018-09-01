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
	//��Ա�б�
	@Override
	public JSONObject findAllUser(int clubId,int page,Integer user_id) {
		JSONObject info = new JSONObject();
		List<Integer> list = clubUserDao.findAllUser(clubId);
		//�ҵ������������
		Integer wantIn = clubUserDao.findWantInNum(clubId);
		//�ҵ����뿪������
		Integer wantOut = clubUserDao.findWantOutNum(clubId);
		info.put("wantIn", wantIn);
		info.put("wantOut", wantOut);
		
		//û����
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
			//��redis�л�ȡ���������ͼƬ ����j��
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
	//��������б�
	@Override
	public JSONObject findJoinUser(int clubId, int page) {
		JSONObject info = new JSONObject();
		List<Integer> list = clubUserDao.findJoinUser(clubId);
		//���е�����
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
			//��redis�л�ȡ���������ͼƬ ����j��
			j.put("userImg", findUser.getUSER_IMG());
			j.put("userName", findUser.getUSER_NAME());
			array.add(j);
		}
		info.put("users", array);
		return info;
	}
	//�����뿪�б�
	@Override
	public JSONObject findLeaveUser(int clubId, int page) {
		JSONObject info = new JSONObject();
		List<Integer> list = clubUserDao.findLeaveUser(clubId);
		//���е�����
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
			//��redis�л�ȡ���������ͼƬ ����j��
			j.put("userImg", findUser.getUSER_IMG());
			j.put("userName", findUser.getUSER_NAME());
			array.add(j);
		}
		info.put("users", array);
		return info;
	}
	//ͬ�����
	@Override
	public void passJoin(Integer clubId, Integer userId) {
		clubUserDao.passJoin(clubId, userId);
		//���������Ƿ����� 
		//����redis����
	}
	//�ܾ�����
	@Override
	public void refuseJoin(Integer clubId, Integer userId) {
		clubUserDao.refuseJoin(clubId, userId);
		
	}
	//ͬ���뿪
	@Override
	public void passLeave(Integer clubId, Integer userId) {
		clubUserDao.passLeave(clubId, userId);
		//����redis����
	}
	//�ܾ��뿪
	@Override
	public void refuseLeave(Integer clubId, Integer userId) {
		clubUserDao.refuseLeave(clubId, userId);
	}
	@Override
	public void deleteUser(Integer clubId, Integer userId) {
		clubUserDao.deleteUser(clubId, userId);
	}
}
