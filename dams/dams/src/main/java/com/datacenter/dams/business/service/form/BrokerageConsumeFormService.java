package com.datacenter.dams.business.service.form;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.redis.inner.input.RedisQueueInputDao;
import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.datacenter.dams.business.service.FormInter;
import com.datacenter.dams.input.queue.entity.BrokerageConsumeInfo;
import com.datacenter.dams.util.JsonUtil;
import com.google.common.base.Strings;

/**
 * 佣金提现的业务
 * @author wulinli
 */
public class BrokerageConsumeFormService implements FormInter{

	private static Logger logger=LogManager.getLogger(BrokerageConsumeFormService.class);
	
	private RedisQueueInputDao redisQueueInputDao;
	
	@Override
	public void handler(Object object) throws Exception {
		if(object == null){
			return ;
		}
		BrokerageConsumeInfo info = (BrokerageConsumeInfo)object;
		String json = JsonUtil.getObjectToJson(info);
		if(!Strings.isNullOrEmpty(json)){
			redisQueueInputDao.sendToRedisQueue(RedisQueueUtil.FORM_INNER_REDISQUEUE,json);
			logger.info("[DAMS#BrokerageConsumeFormService佣金提现]数据存入DA系统队列，数据是："+json);
		}
	}

	public RedisQueueInputDao getRedisQueueInputDao() {
		return redisQueueInputDao;
	}

	public void setRedisQueueInputDao(RedisQueueInputDao redisQueueInputDao) {
		this.redisQueueInputDao = redisQueueInputDao;
	}
}
