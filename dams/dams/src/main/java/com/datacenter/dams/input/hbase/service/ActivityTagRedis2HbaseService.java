package com.datacenter.dams.input.hbase.service;

import com.datacenter.dams.input.hbase.RedisUtil;

/**
 * 
 * 获取http请求的静态类
 * @author kate
 *
 */
public class ActivityTagRedis2HbaseService {
	
	/**
	 * 查询ocms系统正在运行的http请求
	 */
	private static ActivityTagOcmsHttpRes activityTagOcmsHttpRes;
	
	
	private static RedisUtil redisUtil;
	

	public static RedisUtil getRedisUtil() {
		return redisUtil;
	}

	public static void setRedisUtil(RedisUtil redisUtil) {
		ActivityTagRedis2HbaseService.redisUtil = redisUtil;
	}

	public static ActivityTagOcmsHttpRes getActivityTagOcmsHttpRes() {
		return activityTagOcmsHttpRes;
	}

	public static void setActivityTagOcmsHttpRes(ActivityTagOcmsHttpRes activityTagOcmsHttpRes) {
		ActivityTagRedis2HbaseService.activityTagOcmsHttpRes = activityTagOcmsHttpRes;
	}

	
	
	
	
	

}
