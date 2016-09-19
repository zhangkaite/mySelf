package com.datacenter.dams.business.dao.redis.inner.input;

public interface RedisQueueInputDaoInter {

	/**
	 * 向RedisQueue队列添加数据
	 * @param data
	 * @throws Exception
	 */
	public void sendToRedisQueue(String data)throws Exception;
	
	/**
	 * 判断Redis队列是否已经饱和
	 * @return
	 * @throws Exception
	 */
	public boolean RedisQueueIsFull()throws Exception;
}
