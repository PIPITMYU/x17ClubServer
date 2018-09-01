package com.club.constant;

import com.club.util.ProjectInfoPropertyUtil;



public class Cnst {
	//JsonResult返回状态
	public static final int SUCCESS = 1;//成功
	public static final int ERROR = 0;//失败
	//俱乐部状态 0申请中 1已通过 2已驳回 
	public static final int APPLY = 0;//申请加入
	public static final int PASS = 1;//已通过
	public static final int REFUSE = 2;//申请退出
	public static final int FANGKAXIANZHI=200;

	//redis配置
    public static final String REDIS_HOST = ProjectInfoPropertyUtil.getProperty("redis.host", "");
    public static final String REDIS_PORT = ProjectInfoPropertyUtil.getProperty("redis.port", "");
    public static final String REDIS_PASSWORD = ProjectInfoPropertyUtil.getProperty("redis.password", "");
    //redis清理数据前缀
    //保存可以清的缓存
    public static final String REDIS_PREFIX = ProjectInfoPropertyUtil.getProperty("redis.prefix","");
    //保存不能清的缓存
    public static final String REDIS_RECORD_PREFIX = ProjectInfoPropertyUtil.getProperty("redis.record_prefix","");
    
    //俱乐部
    //清理的
    public static final String REDIS_CLUB_CLUBMAP = REDIS_PREFIX.concat("_CLUB_MAP_");//俱乐部信息--清理,防止停服的时候代理后端从房卡
    public static final String REDIS_CLUB_ROOM_LIST = REDIS_PREFIX.concat("_CLUB_MAP_LIST_");//存放俱乐部未开局房间信息--这个可以清
    //不清理的
    public static final String REDIS_CLUB_PLAY_RECORD_PREFIX = REDIS_RECORD_PREFIX.concat("_CLUB_PLAY_RECORD_");//房间战绩
    public static final String REDIS_CLUB_PLAY_RECORD_PREFIX_ROE_USER =  REDIS_RECORD_PREFIX.concat("_CLUB_PLAY_RECORD_FOR_USER_");//玩家字段
    public static final String REDIS_CLUB_TODAYSCORE_ROE_USER = REDIS_RECORD_PREFIX.concat("_CLUB_TODAYSCORE_FOR_USER_");//统计玩家今日分数
    public static final String REDIS_CLUB_TODAYJUNUM_ROE_USER = REDIS_RECORD_PREFIX.concat("_CLUB_TODAYJUNUM_FOR_USER_");//统计玩家今日局数
    public static final String REDIS_CLUB_ACTIVE_NUM = REDIS_RECORD_PREFIX.concat("_CLUB_ACTIVE_NUM_");//今天活跃人数
    public static final String REDIS_CLUB_TODAYKAI_NUM = REDIS_RECORD_PREFIX.concat("_CLUB_TODAYKAI_NUM_");//今天开局数

    
    public static final int maxClub = 3;
    public static final int maxMember = 60;
    
    public static final String REDIS_PAY_ORDERNUM = "PAY_ORDERNUM";//充值订单号
    
    public static final String REDIS_PAY_CURRENTKOU = "PAY_CURRENTKOU";//当前扣量局数
    
    public static final String REDIS_PAY_KOU = "PAY_KOU";//规定扣量
    public static final long HUODONG_TIME = 1517414400;//创建俱乐部的限免时间(活动，时间到月底)
    
    public static final long WEEK_TIME = 7*24*3600;//包星期
    public static final long MONTH_TIME = 30*24*3600;//包月
}
