package com.club.dao;

public interface UtilDao {
	//删除club信息
	void deleteCheckStatus();
	void deleteRecord();
	void deleteRoom();
	void deleteInfo();
	void deleteUser();
	void deleteUse();
	//删除直充信息
	void deleteDaiLUser();
	void deleteRecharge();
	void deleteCash();
	//删除游戏测试信息
	void deleteGamePlayRecord();
	void deleteGameRoom();
	void deleteGameUser();
	void deleteGameUse();
}
