package com.datacenter.dams.business.center.output;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.redis.output.ExpLevelOutputRedisQueueDao;
import com.datacenter.dams.business.dao.util.RedisQueueUtil;

/**
 * 用户娱乐等级或是主播明星等级升级数据，推送外部系统中心。
 * @author wulinli
 *
 */
public class UserLevelupOutputCenter {

	private static Logger logger=LogManager.getLogger(UserLevelupOutputCenter.class);
	
	public void handler(Object object)throws Exception{
		if(object == null){
			return ;
		}
		String message = object.toString();
		ExpLevelOutputRedisQueueDao.sendRedisQueueMessage(RedisQueueUtil.LEVELUP_USER_REDISQUEUE_OUTPUT, message);
		logger.info("[DAMS用户等级变更UserLevelupOutputCenter]用户等级变更推送到外部系统成功："+message);
	}
}
