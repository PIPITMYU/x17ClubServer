package com.club.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.club.dao.ClubInfoDao;
import com.club.dao.ClubUserDao;
import com.club.dao.UserDao;
import com.club.entity.ClubInfo;
import com.club.entity.JsonResult;
import com.club.entity.RedisClub;
import com.club.service.ClubInfoService;
import com.club.util.GameUtil;
@Service("clubInfoService")
public class ClubInfoServiceimpl implements ClubInfoService{
	@Resource
	private ClubInfoDao clubInfoDao;
	@Resource
	private UserDao userDao;
	@Resource
	private ClubUserDao clubUserDao;
	@Override
	//id为代理的userId, id2为生成的代理id
	public JSONObject getMyClubs(int userId,Integer id) {
		List<ClubInfo> clubs = clubInfoDao.findClubByCreateId(userId);
		//获取房卡数
		int myMoney = userDao.findMoney(id);
		//还能创建的个数
//		int canCreateNum = Integer.valueOf(getRedisInfo.getStringByKey(Cnst.REDIS_PREFIX_CREATEMAX))-clubs.size();
		int canCreateNum = 3 - clubs.size();
		JSONObject club =  new JSONObject();
		JSONArray array = new JSONArray();
		for(int i=0;i<clubs.size();i++){
			JSONObject o = new JSONObject();
			int lastMoney = clubs.get(i).getROOM_CARD_NUM();
			int yuJing = clubs.get(i).getROOM_CARD_NOTICE();
			int clubId = clubs.get(i).getCLUB_ID();
			int dnum = clubs.get(i).getROOM_CARD_QUOTA();
			o.put("clubId", clubId);
			o.put("xiane", dnum);
			o.put("lastMoney", lastMoney);
			o.put("clubName", clubs.get(i).getCLUB_NAME());
			o.put("yuJing",yuJing);
			o.put("haveAction",clubInfoDao.haveAction(clubId));
			//现在加上俱乐部房主
			o.put("allPerson", clubUserDao.findAllUser(clubId).size());
			o.put("FE", clubs.get(i).getFREE_END());
			o.put("FS", clubs.get(i).getFREE_START());
			array.add(o);
		}
		club.put("myMoney", myMoney);
		club.put("canCreateNum", canCreateNum);
		club.put("clubs", array);
		return club;
	}
	@Override
	public void createClub(ClubInfo club,Integer userId,Integer change) {
		//更新数据库club信息
		clubInfoDao.createClub(club);
		//把房主放到club_user里
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("clubId", club.getCLUB_ID());
		map.put("userId",club.getCREATE_ID());
		map.put("createTime", club.getCREATE_TIME());
		clubInfoDao.addCreater(map);
		//更新数据库user房卡
		userDao.updateUserMoney(userId, change);
		//更新redis缓存
	}
	
	@Override
	public void addMoney(Integer clubId, Integer id, Integer addMoney) {
		//俱乐部增加房卡
		clubInfoDao.addMoney(clubId, addMoney);
		//更新user房卡
		userDao.updateUserMoney(id, addMoney);
		//更新club Redis
		RedisClub club = GameUtil.getClubInfoByClubId(clubId+"");
		if(club==null){
			club = clubInfoDao.findClubNewByClubId(Integer.valueOf(clubId));// 根据俱乐部id查询
		}
		club.setRoomCardNum(club.getRoomCardNum()+addMoney);
		GameUtil.setClubInfoByClubId(clubId+"", club);
	}
	
	@Override
	public JSONObject moneyManage(Integer clubId,Integer userId) {
		//获取缓存俱乐部
		RedisClub redisClub = GameUtil.getClubInfoByClubId(clubId+"");
		if(redisClub==null){
			redisClub = clubInfoDao.findClubNewByClubId(Integer.valueOf(clubId));// 根据俱乐部id查询
		}
		if(redisClub==null){
			return null;
		}
		GameUtil.setClubInfoByClubId(clubId+"", redisClub);
		//查询user房卡
		Integer user_Id = userDao.findUserId(Integer.valueOf(userId+""));
		if(user_Id==null){
			return null;
		}
		int userMoney = userDao.findMoney(userId);
		JSONObject info = new JSONObject();
		info.put("myMoney", userMoney);
		info.put("lastMoney", redisClub.getRoomCardNum());
		info.put("clubName", redisClub.getClubName());
		return info;
	}
	@Override
	public void deleteClub(Integer clubId,RedisClub redisClub) {
		Long userId = redisClub.getCreateId();
		Integer money = redisClub.getRoomCardNum();
		//修改mysql数据
		clubInfoDao.deleteClubInfo(clubId);
		clubInfoDao.deleteClubUser(clubId);
		userDao.updateUserMoney(Integer.valueOf(userId+""), -money);
	}
	@Override
	public JSONObject haveAction(Integer userId) {
		List<ClubInfo> clubs = clubInfoDao.findClubByCreateId(userId);
		JSONObject info = new JSONObject();
		Integer actions = 0;
		for(int i=0;i<clubs.size();i++){
			actions = actions + clubInfoDao.haveAction(clubs.get(i).getCLUB_ID());
		}
		info.put("haveAction",actions);
		return info;
	}
	@Override
	public JSONObject getHisClubs(Integer toUserId) {
		List<ClubInfo> clubs = clubInfoDao.findClubByCreateId(toUserId);
		JSONObject club =  new JSONObject();
		JSONArray array = new JSONArray();
		for(int i=0;i<clubs.size();i++){
			JSONObject o = new JSONObject();
			int lastMoney = clubs.get(i).getROOM_CARD_NUM();
			int yuJing = clubs.get(i).getROOM_CARD_NOTICE();
			int clubId = clubs.get(i).getCLUB_ID();
			int dnum = clubs.get(i).getROOM_CARD_QUOTA();
			o.put("clubId", clubId);
			o.put("xiane", dnum);
			o.put("lastMoney", lastMoney);
			o.put("clubName", clubs.get(i).getCLUB_NAME());
			o.put("yuJing",yuJing);
			o.put("FS",clubs.get(i).getFREE_START());
			o.put("FE",clubs.get(i).getFREE_END());
			o.put("haveAction",clubInfoDao.haveAction(clubId));
			o.put("allPerson", clubUserDao.findAllUser(clubId).size()-1);
			array.add(o);
		}
		club.put("clubs", array);
		return club;
	}
}
