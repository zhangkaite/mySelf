package com.datacenter.dams.business.service.form;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.redis.inner.input.RedisQueueInputDao;
import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.datacenter.dams.business.service.FormInter;
import com.datacenter.dams.input.queue.entity.TquanRechargeInfo;
import com.datacenter.dams.util.JsonUtil;
import com.google.common.base.Strings;

/**
 * 报表tq充值的业务
 * @author wulinli
 */
public class TquanRechargeFormService implements FormInter{

	private static Logger logger=LogManager.getLogger(TquanRechargeFormService.class);
	
	private RedisQueueInputDao redisQueueInputDao;
	
	@Override
	public void handler(Object object) throws Exception {
		if(object == null){
			return ;
		}
		TquanRechargeInfo info = (TquanRechargeInfo)object;
		String json = JsonUtil.getObjectToJson(info);
		if(!Strings.isNullOrEmpty(json)){
			redisQueueInputDao.sendToRedisQueue(RedisQueueUtil.FORM_INNER_REDISQUEUE,json);
			logger.info("[DAMS#TquanRechargeFormService报表TQ充值]数据存入DA系统队列，数据是："+json);
		}
	}

	public RedisQueueInputDao getRedisQueueInputDao() {
		return redisQueueInputDao;
	}

	public void setRedisQueueInputDao(RedisQueueInputDao redisQueueInputDao) {
		this.redisQueueInputDao = redisQueueInputDao;
	}
	
}
