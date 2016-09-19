package com.datacenter.dams.output.redis.worker;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.center.MessageHandlerCenter;
import com.datacenter.dams.business.dao.redis.inner.input.ExpRedisQueueDao;
import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.datacenter.dams.util.JsonUtil;
import com.datacenter.worker.worker.QuartzWorkerBatch;

/**
 * 读取用户娱乐等级或是主播明星等级经验变动消息worker
 * @author wulinli
 *
 */
public class UserExpOutputWorker extends QuartzWorkerBatch<String>{

	private static Logger logger=LogManager.getLogger(UserExpOutputWorker.class);
	
	@Override
	protected List<String> getData(int sum) {
		if(sum != 0){
			try {
				List<String> listStrings = ExpRedisQueueDao.getRedisQueueMessage(RedisQueueUtil.EXP_USER_REDISQUEUE_INNER_OUTPUT,sum);
				if(listStrings != null && listStrings.size() > 0){
					logger.info("[DAMS用户经验变动UserExpOutputWorker]读取Storm计算后的用户经验的数据：" + JsonUtil.getObjectToJson(listStrings));
					return listStrings;
				}
			} catch (Exception e) {
				logger.error("[DAMS用户经验变动UserExpOutputWorker#ERROR]读取Storm计算后的用户经验的数据出错！",e);
			}
		}
		return null;
	}

	@Override
	protected void process(List<String> listStrings) throws Exception {
		if(listStrings != null && listStrings.size() > 0){
			MessageHandlerCenter.userExpOutPutCenter.handler(listStrings);
		}
	}

	@Override
	protected String toLog(String arg0) {
		return null;
	}
}
