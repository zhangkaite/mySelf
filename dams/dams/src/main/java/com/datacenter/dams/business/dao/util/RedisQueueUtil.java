
package com.datacenter.dams.business.dao.util;

public interface RedisQueueUtil {
	
	/**
	 * 排行榜
	 */
	/* TB刷礼物，放入的redis队列的名称，storm使用，用于计算*/
	String CONSUME_SPEND = "da_spending_que";
	/* 消费放入redis队列数据数量的饱和数 */
	Integer CONSUM_REDISQUEUE_FULL = 10000;
	/* 消费数据放入hadoop离线计算队列 */
	String SPEND_OFFLINE ="dams_spending_hdfs_que"; 	
	/*storm计算后,数据存放的队列*/
	String RICHER_RANKLIST_REDISQUEUE_RESULT = "dams_user_rankspending_que";
	String FAMOUSER_RANKLIST_REDISQUEUE_RESULT = "dams_star_rankspending_que";
	
	/**
	 *  用户或是主播等级 
	 */
	/* IM的redis队列 */
	String IM_FLOWER_REDISQUEUE = "SDCS_flower_Queue";
	String IM_TAKEHEART_REDISQUEUE = "SDCS_heart_Queue";
	String IM_STARONLINE_REDISQUEUE = "SDCS_starOnline_Queue";
	String IM_USERONLINE_REDISQUEUE = "SDCS_userOnline_Queue";
	
	/* dams系统内部队列 */
	String DAMS_USER_ONLINE_QUEUE = "dams_user_online";
	String DAMS_STAR_ONLINE_QUEUE = "dams_star_online";
	String USER_RESET_QUEUE = "DAMS_UC_ResetStarEXP_Queue";
	
	/* da系统队列 */
	String FLOWER_REDISQUEUE_INNER_INPUT ="da_consume_flower_que";
	String TAKEHEART_REDISQUEUE_INNER_INPUT ="da_consume_heart_que";
	String ONLINE_REDISQUEUE_INNER_INPUT ="da_consume_online_que";
	String MONTH_DOWNLEVEL_REDISQUEUE_INNER_INPUT ="da_consume_month_online_que";
	String CONSUME_REDISQUEUE_INNER_INPUT = "da_consume_spending_que";
	
	/* 用户或主播经验值队列,storm计算后,推送给DAMS */
	String EXP_USER_REDISQUEUE_INNER_OUTPUT = "dams_exp_que";
	/* 用户或主播升级通知队列,storm计算后,推送给DAMS */
	String LEVELUP_USER_REDISQUEUE_INNER_OUTPUT = "dams_levelup_que";
	/* 对外推送 , 推送到用户中心 Activmemq队列 */
	String EXP_USER_REDISQUEUE_OUTPUT = "UC_LevelExp_Queue";
	/* 对外推送 , 推送到外部系统 redis队列 */
	String LEVELUP_USER_REDISQUEUE_OUTPUT = "PS_upLevel_Queue";
	
	/**
	 * 活动
	 */
	/* 活动数据队列,storm计算使用 */
	String  ACTIVITY_INNER_REDISQUEUE_INPUT = "da_activity_que";
	/* storm计算后的结果,存放的队列 */
	String  ACTIVITY_INNER_REDISQUEUE_USER_RANK_OUTPUT = "dams_user_rankactivity_que";
	String  ACTIVITY_INNER_REDISQUEUE_STAR_RANK_OUTPUT = "dams_star_rankactivity_que";
	String  ACTIVITY_INNER_REDISQUEUE_SWINGTAG_OUTPUT = "dams_swingtag_que";
	/* IM推送队列*/
	String ACTIVITY_OUTER_REIDSQUEUE_SWINGTAG = "PS_activity_Queue";
	
	/**
	 * 报表
	 */
	String FORM_INNER_REDISQUEUE = "da_paycenter_alldata_que";

}
