package com.club.constant;

import com.club.util.ProjectInfoPropertyUtil;



public class Cnst {
	//JsonResult����״̬
	public static final int SUCCESS = 1;//�ɹ�
	public static final int ERROR = 0;//ʧ��
	//���ֲ�״̬ 0������ 1��ͨ�� 2�Ѳ��� 
	public static final int APPLY = 0;//�������
	public static final int PASS = 1;//��ͨ��
	public static final int REFUSE = 2;//�����˳�
	public static final int FANGKAXIANZHI=200;

	//redis����
    public static final String REDIS_HOST = ProjectInfoPropertyUtil.getProperty("redis.host", "");
    public static final String REDIS_PORT = ProjectInfoPropertyUtil.getProperty("redis.port", "");
    public static final String REDIS_PASSWORD = ProjectInfoPropertyUtil.getProperty("redis.password", "");
    //redis��������ǰ׺
    //���������Ļ���
    public static final String REDIS_PREFIX = ProjectInfoPropertyUtil.getProperty("redis.prefix","");
    //���治����Ļ���
    public static final String REDIS_RECORD_PREFIX = ProjectInfoPropertyUtil.getProperty("redis.record_prefix","");
    
    //���ֲ�
    //�����
    public static final String REDIS_CLUB_CLUBMAP = REDIS_PREFIX.concat("_CLUB_MAP_");//���ֲ���Ϣ--����,��ֹͣ����ʱ������˴ӷ���
    public static final String REDIS_CLUB_ROOM_LIST = REDIS_PREFIX.concat("_CLUB_MAP_LIST_");//��ž��ֲ�δ���ַ�����Ϣ--���������
    //�������
    public static final String REDIS_CLUB_PLAY_RECORD_PREFIX = REDIS_RECORD_PREFIX.concat("_CLUB_PLAY_RECORD_");//����ս��
    public static final String REDIS_CLUB_PLAY_RECORD_PREFIX_ROE_USER =  REDIS_RECORD_PREFIX.concat("_CLUB_PLAY_RECORD_FOR_USER_");//����ֶ�
    public static final String REDIS_CLUB_TODAYSCORE_ROE_USER = REDIS_RECORD_PREFIX.concat("_CLUB_TODAYSCORE_FOR_USER_");//ͳ����ҽ��շ���
    public static final String REDIS_CLUB_TODAYJUNUM_ROE_USER = REDIS_RECORD_PREFIX.concat("_CLUB_TODAYJUNUM_FOR_USER_");//ͳ����ҽ��վ���
    public static final String REDIS_CLUB_ACTIVE_NUM = REDIS_RECORD_PREFIX.concat("_CLUB_ACTIVE_NUM_");//�����Ծ����
    public static final String REDIS_CLUB_TODAYKAI_NUM = REDIS_RECORD_PREFIX.concat("_CLUB_TODAYKAI_NUM_");//���쿪����

    
    public static final int maxClub = 3;
    public static final int maxMember = 60;
    
    public static final String REDIS_PAY_ORDERNUM = "PAY_ORDERNUM";//��ֵ������
    
    public static final String REDIS_PAY_CURRENTKOU = "PAY_CURRENTKOU";//��ǰ��������
    
    public static final String REDIS_PAY_KOU = "PAY_KOU";//�涨����
    public static final long HUODONG_TIME = 1517414400;//�������ֲ�������ʱ��(���ʱ�䵽�µ�)
    
    public static final long WEEK_TIME = 7*24*3600;//������
    public static final long MONTH_TIME = 30*24*3600;//����
}
