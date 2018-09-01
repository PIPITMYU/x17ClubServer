package com.club.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.club.constant.Cnst;
import com.club.dao.ClubInfoDao;
import com.club.dao.ClubUserDao;
import com.club.dao.UserDao;
import com.club.entity.ClubInfo;
import com.club.entity.JsonResult;
import com.club.entity.RedisClub;
import com.club.entity.User;
import com.club.redis.StringUtils;
import com.club.service.ClubInfoService;
import com.club.util.GameUtil;


@Controller
public class ClubInfoController {
	@Resource
	private ClubInfoDao clubInfoDao;
	@Resource
	private ClubInfoService clubInfoService;
	@Resource
	private ClubUserDao clubUserDao;
	@Resource
	private UserDao userDao;
	@RequestMapping("/getMyClubs")//��ȡ���ֲ�
	@ResponseBody
	public JsonResult getMyClubs(Long userId,HttpServletRequest request){
		try{
			if(!StringUtils.isNum(userId.toString()) ){
				return new JsonResult("15");
			}
			//ͨ�������Id��ȡ��userId
			Integer user_Id = userDao.findUserId(Integer.valueOf(userId+""));
			System.out.println("==================>"+user_Id);
			//��������  �����Ǿ���,���ܲ���������ӿ�
			if(user_Id==null){
				return new JsonResult(15);
			}
			JSONObject json = clubInfoService.getMyClubs(user_Id,Integer.valueOf(userId+""));
			return new JsonResult(json);
		}
		catch(Exception e){
			System.out.println(e);
//			return new JsonResult("����ʧ�ܣ����Ժ�����");
			return new JsonResult("3");
		}
	}
	@RequestMapping("/createClub")//�������ֲ�
	@ResponseBody
	public JsonResult createClub(Long userId,String clubName,Integer lastMoney,Integer maxUseMoney,Integer moneyWarn){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"��¼�ѹ�ʱ�����µ�¼",null,"");
			if(clubName.contains(".")||clubName.contains("/")||clubName.contains("//"))
				return new JsonResult("4");
//				return new JsonResult("���зǷ��ַ�");
			if(clubInfoDao.findClubByClubName(clubName)!=null){
//				return new JsonResult("�þ��ֲ��Ѵ��ڣ��������ư�");
				return new JsonResult("5");
			}
			//��ǰ�˾Ϳ����ж�
			Integer user_Id = userDao.findUserId(Integer.valueOf(userId+""));
			if(user_Id==null){
				return new JsonResult(15);
			}
//			System.out.println("�������ֲ�=======>"+userId+clubName+lastMoney+maxUseMoney+moneyWarn);
//			int canCreateNum = Integer.valueOf(getRedisInfo.getStringByKey(Cnst.REDIS_PREFIX_CREATEMAX));
			int canCreateNum = Cnst.maxClub;
			//���ֲ��ܴ���  ָ�䴴��
			Integer num =clubUserDao.findClubNumByUserId(Integer.valueOf(user_Id+""));
//			if(clubs.size()>=canCreateNum)
			if(num >=canCreateNum)
//				return new JsonResult("�޷�����������ֲ�");
				return new JsonResult("6");
			//����userId��ȡ���淿����
			int money = userDao.findMoney(Integer.valueOf(userId+""));
			User findUser = userDao.findUser(user_Id);

			if(money<Cnst.FANGKAXIANZHI){
				//�������㣬���ֵ
				return new JsonResult("25");
			}
			if(money<lastMoney)
//				return new JsonResult("�������㣬�޷��������ֲ�");
				return new JsonResult("7");
			ClubInfo club = new ClubInfo();
			RedisClub redisClub = new RedisClub();
			
			//����clubId�Ƿ����
			synchronized (ClubInfoController.class) {
				Integer clubId=null;
				while(true){
					clubId = GameUtil.createSixCode();
					if(clubInfoDao.findClubByClubId(clubId)==null){
						club.setCLUB_ID(GameUtil.createSixCode());
						break;
					}	
				}
				long currentTimeMillis = System.currentTimeMillis();
				club.setCLUB_NAME(clubName);
				club.setCREATE_ID(Integer.valueOf(user_Id+""));
				club.setCREATE_TIME(currentTimeMillis);
				club.setPERSON_QUOTA(Cnst.maxMember);
	//			club.setPERSON_QUOTA(Integer.valueOf(getRedisInfo.getStringByKey(Cnst.REDIS_PREFIX_CLUBMENBERMAX)));//��������redis����
				club.setROOM_CARD_NOTICE(moneyWarn);
				club.setROOM_CARD_NUM(lastMoney);
				club.setROOM_CARD_QUOTA(maxUseMoney);
				club.setCLUB_USERNAME(findUser.getUSER_NAME());
				//�¼�������⿪ʼʱ����������ʱ��  ���ֻ����2018 1���У�ʱ���µ�1517414400
				club.setFREE_START(currentTimeMillis);
				if(currentTimeMillis< Cnst.HUODONG_TIME*1000){//�������ʱ��
					club.setFREE_END(Cnst.HUODONG_TIME*1000);
				}else{
					club.setFREE_END(currentTimeMillis);
				}
				//�÷�����---1�������ݿ��CLUB_INFO����Ӿ��ֲ���Ϣ 2�������ݿ��CLUB_USER����������Ϣ����stateΪ5
				clubInfoService.createClub(club, Integer.valueOf(userId+""), lastMoney);
				//��ӻ�������
				redisClub.setClubId(clubId);
				redisClub.setClubName(clubName);
				redisClub.setCreateId(Long.valueOf(user_Id+""));
				redisClub.setCreateTime(currentTimeMillis);
				redisClub.setPersonQuota(Cnst.maxMember);
				//			club.setPERSON_QUOTA(Integer.valueOf(getRedisInfo.getStringByKey(Cnst.REDIS_PREFIX_CLUBMENBERMAX)));//��������redis����
				redisClub.setRoomCardNotice(moneyWarn);
				redisClub.setRoomCardNum(lastMoney);
				redisClub.setRoomCardQuota(maxUseMoney);
				redisClub.setFreeStart(club.getFREE_START());
				redisClub.setFreeEnd(club.getFREE_END());
				redisClub.setClubUserName(club.getCLUB_USERNAME());

				//��ӵ�����
				GameUtil.setClubInfoByClubId(clubId.toString(), redisClub);
			}
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
			System.out.println(e);
//			return new JsonResult("����ʧ�ܣ����Ժ�����");
			return new JsonResult("3");
		}
	}
	@RequestMapping("/searchClubs")//������ҵ�idȡ��ѯ���ֲ���Ϣ
	@ResponseBody
	public JsonResult freeClub(String userId,String toUserId){
		try{
			//�ж��û���������Ƿ���ȷ
			if(!StringUtils.isNum(userId) ||!StringUtils.isNum(toUserId)){
				return new JsonResult("15");
			}
//			if(!StringUtils.isNum(toUserId)){
//				return new JsonResult("15");
//			}
			//userId��ʶ��ѯ���û�id;   toUserId��ʶ����ѯ���ֲ���������id
			//��ѯ����Ȩ��  userId��ʶ��ѯ���û�id
			Integer daiLPower = userDao.findDaiLPower(Integer.valueOf(userId));
			if(daiLPower==null){
				daiLPower=0;
			}
			if(daiLPower<6){
				//Ȩ�޲���
				return new JsonResult("16");
			}
			//��ѯ���˴����ľ��ֲ���Ϣ
			JSONObject j=clubInfoService.getHisClubs(Integer.valueOf(toUserId));
//			if(j==null){
//				return new JsonResult("");
//			}
			return new JsonResult(j);
		}catch(Exception e){
			System.out.println(e);
			return new JsonResult("3");
		}
	}
	
	@RequestMapping("/freeClub")//���ֲ���ֵ����ʱ��
	@ResponseBody
	public JsonResult freeClub(String userId,String clubId,Integer state){
		try{
//			//�ж��û���������Ƿ���ȷ
			if(!StringUtils.isNum(userId) || !StringUtils.isNum(clubId)){
				return new JsonResult("15");
			}
//			if(!StringUtils.isNum(clubId)){
//				return new JsonResult("15");
//			}
			if(!state.equals(1) && !state.equals(2))
				return new JsonResult("4");
			//��ѯ����Ȩ��
			Integer daiLPower = userDao.findDaiLPower(Integer.valueOf(userId));
			if(daiLPower<6){
				//Ȩ�޲���
				return new JsonResult("15");
			}
			//��ȡ���ֲ���Ϣ
			RedisClub redisClub = GameUtil.getClubInfoByClubId(clubId);
			if (null == redisClub) {// ���Ϊ�� �����ݿ��ѯ
				redisClub = clubInfoDao.findClubNewByClubId(Integer.valueOf(clubId));// ���ݾ��ֲ�id��ѯ
			}
			//��������⿪ʼʱ��
			Long freeStart = System.currentTimeMillis();
			Long freeEnd = redisClub.getFreeEnd();
			if(freeStart<freeEnd){//˵�����ж��������ʱ��
				if(state.equals(1)){//����
					freeEnd=freeEnd+Cnst.WEEK_TIME*1000;
				}else if(state.equals(2)){//����
					freeEnd=freeEnd+Cnst.MONTH_TIME*1000;
				}
			}else{//˵��û��������ʱ��
				if(state.equals(1)){//����
					freeEnd=freeStart+Cnst.WEEK_TIME*1000;
				}else if(state.equals(2)){//����
					freeEnd=freeStart+Cnst.MONTH_TIME*1000;
				}
			}
			//�������ݿ�
			clubInfoDao.updateFreeTimeByClubId(Integer.valueOf(clubId),freeStart,freeEnd);
			//����redis����
			redisClub.setFreeStart(freeStart);
			redisClub.setFreeEnd(freeEnd);
			//����redis
			GameUtil.setClubInfoByClubId(clubId, redisClub);
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
			System.out.println(e);
			return new JsonResult("3");
		}
	}
	
	
	@RequestMapping("/closeClub")//���ֲ�����ʱ��
	@ResponseBody
	public JsonResult closeClub(String userId,String clubId){
		try{
//			//�ж��û���������Ƿ���ȷ
			if(!StringUtils.isNum(userId) || !StringUtils.isNum(clubId)){
				return new JsonResult("15");
			}
			//��ѯ����Ȩ��
			Integer daiLPower = userDao.findDaiLPower(Integer.valueOf(userId));
			if(daiLPower<6){
				//Ȩ�޲���
				return new JsonResult("15");
			}
			long currentTimeMillis = System.currentTimeMillis();
			clubInfoDao.closeFreeTimeByClubId(Integer.valueOf(clubId),currentTimeMillis);
			// ͨ��clubId��redis�л�ȡ���ֲ���Ϣ
			RedisClub redisClub = GameUtil.getClubInfoByClubId(clubId);
			if (null == redisClub) {// ���Ϊ�� �����ݿ��ѯ
				redisClub = clubInfoDao.findClubNewByClubId(Integer.valueOf(clubId));// ���ݾ��ֲ�id��ѯ
			}else{
				//���¾��ֲ�����ѽ���ʱ��
//				redisClub.setFREE_END(currentTimeMillis);
				redisClub.setFreeEnd(currentTimeMillis);
			}
			//����redis����
			GameUtil.setClubInfoByClubId(clubId, redisClub);
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
			System.out.println(e);
			return new JsonResult("3");
		}
	}
	
	
	
	
	
	
	//�޸ľ��ֲ����
	@RequestMapping("/lastMoneyUpdate")
	@ResponseBody
	public JsonResult lastMoneyUpdate(Integer clubId,Long userId,Integer addMoney){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"��¼�ѹ�ʱ�����µ�¼",null,"");
//			System.out.println("�޸ľ��ֲ����============>"+clubId+userId+addMoney);
			//���ҷ���
			if(!StringUtils.isNum(clubId+"") || !StringUtils.isNum(addMoney+"")|| !StringUtils.isNum(userId+"")){
				return new JsonResult("13");
			}
			int money = userDao.findMoney(Integer.valueOf(userId+""));
			if(money<addMoney){
//				return new JsonResult("����������");
				return new JsonResult("9");
			}
			clubInfoService.addMoney(clubId,Integer.valueOf(userId+""), addMoney);
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
			System.out.println(e);
//			return new JsonResult("����ʧ�ܣ����Ժ�����");
			return new JsonResult("3");
		}
	}
	//��������
	@RequestMapping("/moneyManage")
	@ResponseBody
	public JsonResult moneyManage(Integer clubId,Long userId){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"��¼�ѹ�ʱ�����µ�¼",null,"");
//			System.out.println("��������=================>"+clubId+userId);
			if(!StringUtils.isNum(clubId+"") ||  !StringUtils.isNum(userId+"")){
				return new JsonResult("13");
			}
			JSONObject info = clubInfoService.moneyManage(clubId,Integer.valueOf(userId+""));
			//���ֲ�������
			if(info==null){
				return new JsonResult(21);
			}
			return new JsonResult(info);
		}catch(Exception e){
//			return new JsonResult("����ʧ�ܣ����Ժ�����");
			return new JsonResult("3");
		}
	}
	//��ɢ���ֲ�
	@RequestMapping("/deleteClub")
	@ResponseBody
	public JsonResult deleteClub(Integer clubId,Long userId){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"��¼�ѹ�ʱ�����µ�¼",null,"");
			//���ܻ�Ҫһ��userid����
			if(!StringUtils.isNum(clubId+"") ||  !StringUtils.isNum(userId+"")){
				return new JsonResult("13");
			}
			//��ȡ��ҵ�userId
			Long user_Id = 	Long.valueOf(userDao.findUserId(Integer.valueOf(userId+"")));
			if(user_Id==null){
				return new JsonResult(15);
			}
			//�鿴���ֲ��ǲ��Ǵ�����
			RedisClub redisClub = GameUtil.getClubInfoByClubId(clubId+"");
			Boolean needDelte=true;
			if(redisClub==null){
				needDelte=false;
				redisClub = clubInfoDao.findClubNewByClubId(Integer.valueOf(clubId));// ���ݾ��ֲ�id��ѯ
			}
			//���ֲ�������
			if(redisClub==null){
				return new JsonResult(21);
			}
			//����Ҳ��Ǵ�����
			if(!redisClub.getCreateId().equals(user_Id)){
				return new JsonResult(20);
			}
			//ɾ�����ֲ�
			clubInfoService.deleteClub(clubId,redisClub);
			//ɾ������
			if(needDelte){
				GameUtil.deleteClubInfoByClubId(clubId);
			}
			
//			System.out.println("��ɢ���ֲ�===================>"+clubId);
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
//			return new JsonResult("����ʧ�ܣ����Ժ�����");
			return new JsonResult("3");
		}
	}
	
	//���ֲ���Ա�����˳���ʾ
	@RequestMapping("/haveAction")
	@ResponseBody
	public JsonResult haveAction(Long userId){
		try{
//			System.out.println("���ֲ���Ա�䶯Ԥ��===================>"+userId);
			Integer user_Id = userDao.findUserId(Integer.valueOf(userId+""));
			JSONObject info = clubInfoService.haveAction(user_Id);
			return new JsonResult(info);
		}catch(Exception e){
			return new JsonResult();
		}
	}
	
	



	
	
}
