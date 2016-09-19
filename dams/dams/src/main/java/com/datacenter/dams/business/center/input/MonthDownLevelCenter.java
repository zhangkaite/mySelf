package com.datacenter.dams.business.center.input;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.redis.inner.input.ExpRedisQueueDao;
import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.datacenter.dams.util.JsonUtil;
import com.google.common.base.Strings;

/**
 * 月度降级，命令发起中心
 * @author wulinli
 *
 */
public class MonthDownLevelCenter {

	private static Logger logger=LogManager.getLogger(MonthDownLevelCenter.class);
	
	public void handler(Object object)throws Exception{
		if(object == null){
			return ;
		}
		String message = object.toString();
		if(!Strings.isNullOrEmpty(message)){
			Map<String, String> map = new HashMap<String,String>();
			map.put("userId", message);
			String json = JsonUtil.getObjectToJson(map);
			ExpRedisQueueDao.sendRedisQueueMessage(RedisQueueUtil.MONTH_DOWNLEVEL_REDISQUEUE_INNER_INPUT, json);
			logger.info("[DAMS降级MonthDownLevelCenter]降级用户信息发送到Storm，数据是："+json);
		}
	}
}
