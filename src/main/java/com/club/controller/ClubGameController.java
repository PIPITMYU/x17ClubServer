package com.club.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.club.constant.Cnst;
import com.club.dao.ClubGameDao;
import com.club.dao.ClubInfoDao;
import com.club.entity.JsonResult;
import com.club.entity.RedisClub;
import com.club.redis.StringUtils;
import com.club.service.ClubGameService;
import com.club.util.GameUtil;
@Controller
public class ClubGameController {
	
	@Resource
	private ClubInfoDao clubInfoDao;
	@Resource
	private ClubGameService clubGameService;
	@Resource
	private ClubGameDao clubGameDao;
	@RequestMapping("/clubUsersGame")//���ֲ�ս����ѯ
	@ResponseBody
	public JsonResult clubUsersGame(Integer clubId,Long userId,Long queryUserId,Long startData
			,Long endData,String order,String desc,Integer page){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"��¼�ѹ�ʱ�����µ�¼",null,"");
			//�ж��û���������Ƿ���ȷ
			if(!StringUtils.isNum(clubId+"") ||!StringUtils.isNum(userId+"") ||!StringUtils.isNum(page+"") ){
				return new JsonResult("15");
			}
			Long timesmorning = StringUtils.getTimesmorning();
//			System.out.println("ս����ѯ=================>"+clubId+userId+queryUserId+startData+endData+order+desc+page);
			JSONObject info = clubGameService.clubUsersGame(clubId, order, desc, page, startData, endData);
			Integer juNum = GameUtil.getTodayJuNum(clubId+"".concat(timesmorning+""));
			System.out.println("startData:"+startData);
			System.out.println("endData:"+endData);
			System.out.println("juNum:"+juNum);
			if(juNum==null ||juNum==0){
				juNum=0;
			}
			info.put("currNum", juNum);
//			info.put("pages",juNum%page+1);//��ҳ��
			//1:����Ļ�Ծ����--�߻���
			Long actNum = GameUtil.scard(clubId+"_".concat(timesmorning+""));
			if(actNum==null ||actNum==0l){
				actNum=0l;
			}else{
				//��Ϊ���ù���ʱ��ʱ�����������ݣ���Ҫɾ��
				actNum=actNum-1;
			}
			System.out.println(actNum+"----------------------------------actNum");
			//���վ���  �߻��� 
			info.put("activeNum", actNum);
			info.put("allHeXiao", clubGameService.allHeXiao(clubId,startData));
			return new JsonResult(info);
		}catch(Exception e){
			System.out.println(e.getMessage());
			return new JsonResult("3");
		}	
	}
	@RequestMapping("/addHeXiao")//�û�����
	@ResponseBody
	public JsonResult addHeXiao(Integer clubId,Integer userId,Integer inClubUserId,Long startData){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"��¼�ѹ�ʱ�����µ�¼",null,"");
			if(!StringUtils.isNum(clubId+"") ||!StringUtils.isNum(userId+"") ||!StringUtils.isNum(inClubUserId+"" )){
				return new JsonResult("15");
			}
			RedisClub redisClub = GameUtil.getClubInfoByClubId(clubId+"");
			if (null == redisClub) {// ���Ϊ�� �����ݿ��ѯ
				redisClub = clubInfoDao.findClubNewByClubId(Integer.valueOf(clubId));// ���ݾ��ֲ�id��ѯ
			}
		
			//���ֲ�������
			if(redisClub==null){
				return new JsonResult(21);
			}
			//����Ҳ��Ǵ�����
//			if(!redisClub.getCreateId().equals(user_Id)){
//				return new JsonResult(20);
//			}
			clubGameService.addHeXiao(clubId, inClubUserId, startData);
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResult("3");
		}
	}
}
