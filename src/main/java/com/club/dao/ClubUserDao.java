package com.club.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ClubUserDao {
	//���ֲ�����
	public List<Integer> findAllUser(Integer clubId);
	//���ֲ������������
	public List<Integer> findJoinUser(Integer clubId);
	//���ֲ������˳�����
	public List<Integer> findLeaveUser(Integer clubId);
	//ͨ��������ֲ����� 0--1
	public void passJoin(@Param("clubId")Integer clubId,@Param("userId")Integer userId); 
	//�ܾ�������ֲ����� ɾ����¼
	public void refuseJoin(@Param("clubId")Integer clubId,@Param("userId")Integer userId);
	//ͨ���˳����ֲ����� ɾ����¼
	public void passLeave(@Param("clubId")Integer clubId,@Param("userId")Integer userId); 
	//�ܾ��˳����ֲ����� 2--1
	public void refuseLeave(@Param("clubId")Integer clubId,@Param("userId")Integer userId); 
	//�߳����ֲ�
	public void deleteUser(@Param("clubId")Integer clubId,@Param("userId")Integer userId);
	//ͨ�����ID��ѯ�䴴���ĵľ��ֲ�����
	public Integer findClubNumByUserId(Integer userId);
	//��ѯ����������
	public Integer findWantInNum(Integer clubId);
	//��ѯ���˳�������
	public Integer findWantOutNum(Integer clubId);
	//ͨ�����ID��ѯ�����ľ��ֲ�����
	public Integer findClubNumByAddUserId(Integer valueOf);
}
