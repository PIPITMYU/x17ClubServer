package com.club.service;



import com.alibaba.fastjson.JSONObject;
import com.club.entity.ClubInfo;
import com.club.entity.RedisClub;


public interface ClubInfoService {
	//��ȡ�ҵľ��ֲ�
	JSONObject getMyClubs(int userId, Integer integer);
	//�������ֲ�
	void createClub(ClubInfo club,Integer userId,Integer change);
	//������
	void addMoney(Integer clubId,Integer id,Integer addMoney);
	//�鿴���
	JSONObject moneyManage(Integer clubId,Integer userId);
	//��ɢ���ֲ�
	void deleteClub(Integer clubId, RedisClub redisClub);
	//��Ա�䶯Ԥ��
	JSONObject haveAction(Integer userId);
	//��ȡ���˴����ľ��ֲ�
	JSONObject getHisClubs(Integer toUserId);
	
}
