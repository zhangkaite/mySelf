package com.datacenter.dams.business.dao.redis.output;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.ttmv.datacenter.agent.redis.queue.RedisQueueImpi;

/**
 * 向外系统队列 推送数据
 * @author wll
 */
public class SwingTagOutputDao {

	private static Logger logger=LogManager.getLogger(SwingTagOutputDao.class);
	private static RedisQueueImpi redisQueueImpi;
	
	public void sendMessage(String message)throws Exception{
		logger.info("[DAMS#ActivitySwingTagOutput]吊牌活动信息数据>>>推送到外部系统系统队列,数据是:" + message);
		redisQueueImpi.setValue(RedisQueueUtil.ACTIVITY_OUTER_REIDSQUEUE_SWINGTAG,message);
	}
	
	public static RedisQueueImpi getRedisQueueImpi() {
		return redisQueueImpi;
	}
	public static void setRedisQueueImpi(RedisQueueImpi redisQueueImpi) {
		SwingTagOutputDao.redisQueueImpi = redisQueueImpi;
	}
}
