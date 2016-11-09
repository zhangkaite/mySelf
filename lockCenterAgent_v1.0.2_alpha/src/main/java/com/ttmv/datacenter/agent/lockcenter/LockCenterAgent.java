package com.ttmv.datacenter.agent.lockcenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.agent.lockcenter.redis.LockRedisAgent;
import com.ttmv.datacenter.agent.redis.RedisAgent;
import com.ttmv.datacenter.utils.check.CheckParameterUtil;
import com.ttmv.datacenter.utils.check.ModuleInitUtil;

public class LockCenterAgent implements Locker {

	private static Logger logger = LogManager.getLogger(LockCenterAgent.class);

	private LockRedisAgent redis;

	public LockCenterAgent(RedisAgent redis, String hbaseHost, String hbasePort) {
		logger.info("--- Loading lockCenterAgent! ---");
		if (redis == null || CheckParameterUtil.checkIsEmpty(hbasePort)
				|| CheckParameterUtil.checkIsEmpty(hbaseHost)) {
			ModuleInitUtil.failInitBySet("LockCenterAgent", new String[] {
					"redis","hbaseHost", "hbasePort" });
			return;
		}
		try {
			this.redis = new LockRedisAgent(redis);
		} catch (Exception e) {
			logger.error("LockCenterAgent出现异常",e);
			ModuleInitUtil.failInitByEnv("LockCenterAgent","Connection redis failed.", e);
		}
	}

	/**
	 * 永久锁
	 * **/
	public boolean lockUniqueFE(String key) {
		logger.info("加永久锁 --->>> " + key);
		return redis.lockUniqueFE(key);
	}

	/**
	 * 删除永久锁
	 * **/
	public boolean unlockUniqueFE(String key) {
		logger.info("删除永久锁 --->>> " + key);
		if (redis.unlockUniqueFE(key)) {

			return true;
		}
		return false;
	}

	/**
	 * 临时锁
	 **/
	public boolean lockUnique(String key) {
		logger.info("加临时锁 --->>> " + key);
		return redis.lockUnique(key);
	}

	/**
	 * 临时锁解锁
	 **/
	public void unlockUnique(String key) {
		logger.info("删除临时锁--->>> " + key);
		redis.unlockUnique(key);
	}

	/**
	 * 永久锁--次数
	 * */
	public boolean lockUntilFE(String key, int untilNum) {
		logger.info("永久锁增加次数--->>> " + key);
		return redis.lockUntilFE(key, untilNum);
	}

	/**
	 * 解锁永久锁--次数
	 **/
	public boolean releaseOneFE(String key) {
		logger.info("永久锁减少次数--->>> " + key);
		return redis.releaseOneFE(key);
	}

}
