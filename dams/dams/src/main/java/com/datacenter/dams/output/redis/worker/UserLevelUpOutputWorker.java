package com.datacenter.dams.output.redis.worker;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.center.MessageHandlerCenter;
import com.datacenter.dams.business.dao.redis.inner.input.ExpRedisQueueDao;
import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.datacenter.dams.util.JsonUtil;
import com.datacenter.worker.worker.QuartzWorker;
import com.google.common.base.Strings;

/**
 * 用户娱乐等级或是主播明星等级升级
 * @author wulinli
 *
 */
public class UserLevelUpOutputWorker extends QuartzWorker<String>{

	private static Logger logger=LogManager.getLogger(UserLevelUpOutputWorker.class);
	
	@Override
	protected List<String> getData(int sum) {
		if(sum != 0){
			try {
				List<String> listStrings = ExpRedisQueueDao.getRedisQueueMessage(RedisQueueUtil.LEVELUP_USER_REDISQUEUE_INNER_OUTPUT, sum);
				if(listStrings != null && listStrings.size() > 0){
					logger.info("[DAMS用户等级变更UserLevelUpOutputWorker]读取Storm计算后的用户等级变更数据："+JsonUtil.getObjectToJson(listStrings));
					return listStrings;
				}
			} catch (Exception e) {
				logger.error("[DAMS用户等级变更UserLevelUpOutputWorker#ERROR]读取Storm计算后的用户等级变更数据出错！",e);
			}
		}
		return null;
	}

	@Override
	protected void process(String message) throws Exception {
		if(!Strings.isNullOrEmpty(message)){
			MessageHandlerCenter.userLevelupOutputCenter.handler(message);
		}
	}

	@Override
	protected String toLog(String object) {
		return null;
	}
}
