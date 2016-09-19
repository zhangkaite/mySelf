package com.ttmv.datacenter.usercenter.dao.implement.mapper.terminalforbid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.agent.redis.RedisAgent;
//import com.ttmv.datacenter.agent.redis.jedis.JedisPoolAgent;
import com.ttmv.datacenter.sentry.SentryAgent;
import com.ttmv.datacenter.usercenter.dao.implement.constant.Constant;

public class RedisTerminalForbidMapper {

	private final Logger log = LogManager.getLogger(RedisTerminalForbidMapper.class);

	private RedisAgent jedisAgent;
	private SentryAgent quickSentry;
	
	/**
	 * 禁用终端添加到redis中
	 * 
	 * @param redis
	 */
	public void addRedisTerminalForbidKey(String key,String reqID)throws Exception{
		try{
			/* 添加redis */
			jedisAgent.setIfNotExist(key, "*");
			log.debug("[" + reqID+ "]@@" + "[redis添加key成功！]");
		}catch(Exception e){
			log.error("[" + reqID+ "]@@" + "[redis添加key失败！]",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_REDIS_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception("[" + reqID+ "]@@" + "[redis添加key失败！]",e);
		}
	}

	/**
	 * 删除禁用终端记录 根据key
	 * @param key
	 * @throws Exception
	 */
	public void deleteRedisTerminalForbidKey(String key,String reqID)throws Exception{
		try{
			if(isExistKey(key)){				
				jedisAgent.deleteData(key);
				log.debug("[" + reqID+ "]@@" + "[redis删除key成功！]");
			}
		}catch(Exception e){
			log.error("[" + reqID+ "]@@" + "[redis删除key失败！]",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_REDIS_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception("[" + reqID+ "]@@" + "[redis删除key失败！]",e);
		}
	}
	
	/**
	 * 根据key 查询终端的
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public boolean isExistKey(String key)throws Exception{
		try{
			boolean flag = jedisAgent.exists(key);
			return flag;
		}catch(Exception e){
			log.error("判断TerminalForbid是否存在出错,错误的原因是：",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_REDIS_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception("判断TerminalForbid是否存在出错,错误的原因是：",e);
		}
	}

	public void setJedisAgent(RedisAgent jedisAgent) {
		this.jedisAgent = jedisAgent;
	}

	public RedisAgent getJedisAgent() {
		return jedisAgent;
	}

	public SentryAgent getQuickSentry() {
		return quickSentry;
	}

	public void setQuickSentry(SentryAgent quickSentry) {
		this.quickSentry = quickSentry;
	}
}
