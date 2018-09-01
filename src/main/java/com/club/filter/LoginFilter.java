package com.club.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.club.dao.UserDao;

public class LoginFilter implements Filter{
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		  HttpServletRequest servletRequest = (HttpServletRequest) request;
		  HttpServletResponse servletResponse = (HttpServletResponse) response;
//		  servletResponse.setContentType("application/json; charset=utf-8");
		  servletResponse.addHeader("Access-Control-Allow-Origin", "*");
		  servletResponse.setCharacterEncoding("UTF-8");
		  servletRequest.setCharacterEncoding("UTF-8");
		  servletResponse.setContentType("text/html;charset=UTF-8");
		  
//
//		  String userId = servletRequest.getParameter("userId");
//		  System.out.println("===================>token"+userId);
//		  String token_time = userDao.findUserToken(userId);//数据库设置的过期时间
//		  if(token_time==null)
//			  return;
//		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		  PrintWriter out = null;
//		  try {
//			Date date = sdf.parse(token_time);
//			if(date.getTime()<System.currentTimeMillis()){
//				 JSONObject json = new JSONObject();
//				 json.put("state", -1);
//				 json.put("info", null);
//				 json.put("others", null);
//				 json.put("message", "登陆时间过期，请重新登陆");
//				 out = servletResponse.getWriter();
//				 out.write(json.toString());
//				 System.out.println("token信息过期");
//				 return;
//			 }
			 chain.doFilter(request, response);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}finally {  
//		    if (out != null) {  
//		    	out.close();  
//		    }  
//		} 
	}
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
