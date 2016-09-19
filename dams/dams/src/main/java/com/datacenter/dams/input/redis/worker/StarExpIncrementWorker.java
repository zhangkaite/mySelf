package com.datacenter.dams.input.redis.worker;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.center.MessageHandlerCenter;
import com.datacenter.dams.business.dao.redis.inner.input.ExpRedisQueueDao;
import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.datacenter.dams.util.JsonUtil;
import com.datacenter.worker.worker.QuartzWorkerBatch;

/**
 * 明星经验增长worker
 * 每分钟扫描一次
 * @author wulinli
 *
 */
public class StarExpIncrementWorker extends QuartzWorkerBatch<String>{
	
	private static Logger logger=LogManager.getLogger(StarExpIncrementWorker.class);
	
	@Override
	protected List<String> getData(int sum) {
		if(sum != 0){
			try {
				List<String> listStrings = ExpRedisQueueDao.getSetMessages(RedisQueueUtil.DAMS_STAR_ONLINE_QUEUE);
				if(listStrings != null && listStrings.size() > 0){
					logger.info("[DAMS明星经验定时增加StarExpIncrementWorker]定时读取redis库中的用户数据："+JsonUtil.getObjectToJson(listStrings));
					return listStrings;
				}
			} catch (Exception e) {
				logger.error("[DAMS明星经验定时增加StarExpIncrementWorker#ERROR]定时读取redis库中的用户数据出错！",e);
			}
		}
		return null;
	}

	@Override
	protected void process(List<String> listStrings) throws Exception {
		if(listStrings != null && listStrings.size() > 0){
			MessageHandlerCenter.starExpIncrementCenter.handler(listStrings);
		}
	}

	@Override
	protected String toLog(String arg0) {
		return null;
	}

}
