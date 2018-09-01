package com.club.service;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;

public interface ClubUserService {
	//��ѯ���ֲ�����
	JSONObject findAllUser(int clubId,int page,Integer user_id);
	//��ѯ�����������
	JSONObject findJoinUser(int clubId,int page);
	//��ѯ�����˳�����
	JSONObject findLeaveUser(int clubId,int page);
	//ͨ��������ֲ����� 
	public void passJoin(Integer clubId,Integer userId); 
	//�ܾ�������ֲ����� 
	public void refuseJoin(Integer clubId,Integer userId);
	//ͨ���˳����ֲ����� 
	public void passLeave(Integer clubId,Integer userId); 
	//�ܾ��˳����ֲ����� 
	public void refuseLeave(Integer clubId,Integer userId);
	//�߳����ֲ�
	public void deleteUser(Integer clubId,Integer userId);
}
