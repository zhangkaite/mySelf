package com.datacenter.dams.business.dao.redis.output;

import java.util.List;

import com.google.common.base.Strings;
import com.ttmv.datacenter.agent.redis.queue.RedisQueueImpi;

public class ExpLevelOutputRedisQueueDao {

	private static RedisQueueImpi redisQueueImpi;

	/**
	 * 获取给定队列名的一条数据
	 * @param queueName
	 * @return
	 * @throws Exception
	 */
	public static List<String> getRedisQueueMessage(String queueName,int sum) throws Exception {
		List<String> datas = redisQueueImpi.getValueBatch(queueName,sum);
		if(datas != null && datas.size() > 0){
			return datas;
		}
		return null;
	}
	
	/**
	 * 向指定的队列放入一条数据
	 * @param queueName
	 * @param message
	 * @throws Exception
	 */
	public static void sendRedisQueueMessage(String queueName,String message) throws Exception {
		if(!Strings.isNullOrEmpty(queueName) && !Strings.isNullOrEmpty(message)){
			redisQueueImpi.setValue(queueName, message);
		}
	}
	
	/**
	 * 删除队列中指定的数据
	 * @param queueName
	 * @param message
	 * @throws Exception
	 */
	public static void deleteMessage(String queueName,String message)throws Exception{
		if(!Strings.isNullOrEmpty(queueName) && !Strings.isNullOrEmpty(message)){
			redisQueueImpi.delValue(queueName, message);
		}
	}
	public static RedisQueueImpi getRedisQueueImpi() {
		return redisQueueImpi;
	}

	public static void setRedisQueueImpi(RedisQueueImpi redisQueueImpi) {
		ExpLevelOutputRedisQueueDao.redisQueueImpi = redisQueueImpi;
	}
	
}
