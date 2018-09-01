package com.club.redis;




import com.club.constant.Cnst;




import com.club.util.GameUtil;
import com.club.util.UseJDBC;

import redis.clients.jedis.Jedis;
public class MyRedis {
  	private static RedisClient client;
  	
  	public static void initRedis(){
  		client = new RedisClient(null);
//  	initCurrentProjectRedis();
//  	GameUtil.initPayList();
		System.out.println("redis 初始化完成");
  	}
  	
  	public synchronized static RedisClient getRedisClient(){
  		return client;
  	}
  	

	private static void initCurrentProjectRedis(){
    	Jedis jedis = null;
    	int kou = new UseJDBC().findKou();
    	try { 	
    		jedis = MyRedis.getRedisClient().getJedis();
    		jedis.set(Cnst.REDIS_PAY_CURRENTKOU,String.valueOf(0));
    		jedis.set(Cnst.REDIS_PAY_KOU, String.valueOf(kou));
		} catch (Exception e) {
			e.printStackTrace();
			MyRedis.getRedisClient().returnBrokenJedis(jedis);
		}finally{
			if (jedis!=null) {
				MyRedis.getRedisClient().returnJedis(jedis);
			}
		}
    }

}
