package com.ttmv.datacenter.usercenter.dao.implement.mapper.parameter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.agent.redis.RedisAgent;
import com.ttmv.datacenter.usercenter.dao.implement.util.JsonUtil;
import com.ttmv.datacenter.usercenter.domain.data.Parameter;

public class RedisParameter {

	private final Logger log = LogManager.getLogger(RedisParameter.class);
	
	private RedisAgent redisAgent;

	/**
	 * 添加Parameter
	 * @param obj
	 * @throws Exception 
	 */
	public void addRedisParameter(String key,String parameterJson) throws Exception{
		redisAgent.setIfNotExist(key,parameterJson);
	}
	
	/**
	 * 通过key查询redis数据
	 * @param key
	 * @return
	 */
	public Parameter getRedisParameter(String key){
		try{
			String jsonData = redisAgent.getValue(key);
			Parameter parameter = (Parameter)JsonUtil.getObjectFromJson(jsonData,Parameter.class);
			if(parameter != null ){
				return parameter;
			}
		}catch(Exception e){
			log.error("查询UserInfo信息出错，跳过查询！",e);
		}
		return null;
	}
	
	/**
	 * 通过key删除数据
	 * @param userid
	 * @throws Exception 
	 */
	public void deleteRedisParameter(String key) throws Exception{
		if(key != null){
			redisAgent.deleteData(key);			
		}
	}
	
	public void setRedisAgent(RedisAgent redisAgent) {
		this.redisAgent = redisAgent;
	}
}
