package com.datacenter.dams.business.dao.redis.inner.input;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.base.Strings;
import com.ttmv.datacenter.agent.redis.queue.RedisQueueImpi;

public class ExpRedisQueueDao {

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
	
	/**
	 * 发送消息到set集合中
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public static void sendSetMessage(String queueName,String message)throws Exception{
		if(!Strings.isNullOrEmpty(queueName) && !Strings.isNullOrEmpty(message)){
			redisQueueImpi.setSetValue(queueName, message);
		}
	}
	
	/**
	 * 获取指定Set的全部数据
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static List<String> getSetMessages(String queueName)throws Exception{
		if(!Strings.isNullOrEmpty(queueName)){
			Set<String> sets = redisQueueImpi.getSetValues(queueName);
			if(sets != null && sets.size() > 0){
				List<String> listStrings = new ArrayList<String>();
				listStrings.addAll(sets);
				return listStrings;
			}
		}
		return null;
	}
	
	/**
	 * 删除Set集合中的一条消息
	 * @param queueName
	 * @param value
	 */
	public static void removeSetMessage(String queueName,String message)throws Exception{
		if(!Strings.isNullOrEmpty(queueName) && !Strings.isNullOrEmpty(message)){
			redisQueueImpi.deleteSetValue(queueName, message);
		}
	}
	
	public static RedisQueueImpi getRedisQueueImpi() {
		return redisQueueImpi;
	}

	public static void setRedisQueueImpi(RedisQueueImpi redisQueueImpi) {
		ExpRedisQueueDao.redisQueueImpi = redisQueueImpi;
	}
	
}
