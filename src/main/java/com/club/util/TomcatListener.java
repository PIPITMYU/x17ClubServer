package com.club.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.club.redis.MyRedis;




public class TomcatListener implements ServletContextListener{
	
	

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		System.out.println("tomcat关闭..........");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		 //初始化redis
		MyRedis.initRedis();
		System.out.println("tomcate启动..............");
	}

}