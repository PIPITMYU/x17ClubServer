package com.club.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.dao.UtilDao;

@Controller
public class UtilController {
	@Resource
	private UtilDao utilDao;
	@RequestMapping("/deleteAllClubInfo")
	@ResponseBody
	public void deleteClub(HttpServletRequest request){
		//删除俱乐部信息
//		System.out.println(getIpAddress(request));
		utilDao.deleteCheckStatus();
		utilDao.deleteInfo();
		utilDao.deleteRecord();
		utilDao.deleteRoom();
		utilDao.deleteUse();
		utilDao.deleteUser();
	}
	@RequestMapping("/deleteAllZCInfo")
	@ResponseBody
	public void deleteAllZCInfo(){
		//删除直充信息
		utilDao.deleteDaiLUser();
		utilDao.deleteRecharge();
		utilDao.deleteCash();
	}
	@RequestMapping("/deleteAllGameInfo")
	@ResponseBody
	public void deleteAllGameInfo(){
		//删除游戏测试信息
		utilDao.deleteGamePlayRecord();
		utilDao.deleteGameRoom();
		utilDao.deleteGameUse();
		utilDao.deleteGameUser();
	}

	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

}
