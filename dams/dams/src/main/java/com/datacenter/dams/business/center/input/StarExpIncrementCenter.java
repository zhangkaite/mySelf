package com.datacenter.dams.business.center.input;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.redis.inner.input.ExpRedisQueueDao;
import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.datacenter.dams.util.ConsumeSpendConstant;
import com.datacenter.dams.util.DateUtil;
import com.datacenter.dams.util.JsonUtil;

/**
 * 明星增长经验 处理中心
 * @author wulinli
 *
 */
public class StarExpIncrementCenter {
	
	private static Logger logger=LogManager.getLogger(StarExpIncrementCenter.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void handler(Object object)throws Exception{
		if(object == null){
			return ;
		}
		List<String> listStrings = (List<String>)object;
		Map map = new HashMap();
		map.put("star", listStrings);
		map.put("user", null);
		map.put("step", ConsumeSpendConstant.STAR_STEP);
		map.put("time", DateUtil.getUnixDate(new Date()));
		String jsonRedis = JsonUtil.getObjectToJson(map);
		ExpRedisQueueDao.sendRedisQueueMessage(RedisQueueUtil.ONLINE_REDISQUEUE_INNER_INPUT, jsonRedis);
		logger.info("[DAMS明星经验定时增加StarExpIncrementCenter]中心处理用户数据发送到storm计算队列。" +jsonRedis);
	}
}
