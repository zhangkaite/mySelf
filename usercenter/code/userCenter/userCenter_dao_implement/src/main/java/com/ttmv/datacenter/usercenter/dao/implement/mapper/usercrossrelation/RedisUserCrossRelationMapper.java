package com.ttmv.datacenter.usercenter.dao.implement.mapper.usercrossrelation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.agent.redis.RedisAgent;
import com.ttmv.datacenter.sentry.SentryAgent;
import com.ttmv.datacenter.usercenter.dao.implement.constant.Constant;
import com.ttmv.datacenter.usercenter.dao.implement.util.BeanCopyProperties;
import com.ttmv.datacenter.usercenter.dao.implement.util.JsonUtil;
import com.ttmv.datacenter.usercenter.domain.data.UserCrossRelation;

public class RedisUserCrossRelationMapper {

	private final Logger log = LogManager.getLogger(RedisUserCrossRelationMapper.class);
	private static final String CROSS = "CROSS_";
	private SentryAgent quickSentry;
	private RedisAgent jedisAgent;
	private final int REDIS_KEY_TIMEOUT = 84600;	

	/**
	 * 添加UserCrossRelation
	 * @param key
	 * @param jsonData
	 * @throws Exception
	 */
	public void addRedisUserCrossRelation(String id,String jsonData,String reqID)throws Exception{
		try {
			/* 向redis缓存中添加全部数据jsonData ,设置key的时效性 24小时*/
			jedisAgent.setIfNotExist(CROSS + id, jsonData);
			log.debug("[" + reqID + "]@@" + "[redis添加UserCrossRelation成功！]");
		} catch (Exception e) {
			log.error("[" + reqID + "]@@" + "[redis添加UserCrossRelation成功！]",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_REDIS_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception("[" + reqID + "]@@" + "[redis添加UserCrossRelation成功！]",e);
		}
	}
	
	/**
	 * 通过id查询redis中的UserCrossRelation的数据
	 * @param id
	 * @throws Exception
	 */
	public UserCrossRelation  getRedisUserCrossRelation(String id)throws Exception{
		String jsonData = null;
		try{
			jsonData = jedisAgent.getValue(CROSS + id);
		}catch(Exception e){
			log.error("查询UserCrossRelation信息出错，跳过查询！错误的原因：",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_REDIS_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
		}
		try{
			if(jsonData != null ){
				UserCrossRelation crossRelation = (UserCrossRelation)JsonUtil.getObjectFromJson(jsonData,UserCrossRelation.class);
				return crossRelation;
			}
		}catch(Exception e){
			log.error("查询UserCrossRelation信息,JSON转换对象出错！错误的原因：",e);
			throw new Exception("查询UserCrossRelation信息,JSON转换对象出错！错误的原因：",e);
		}		
		return null;
	}
	
	/**
	 * 通过key删除UserCrossRelation
	 * @param id
	 * @throws Exception 
	 */
	public void deleteRedisUserCrossRelation(String id,String reqID) throws Exception{
		try{
			if(id != null){
				if(jedisAgent.exists(CROSS + id)){				
					jedisAgent.deleteData(CROSS + id);
					log.debug("[" + reqID + "]@@" + "[redis删除UserCrossRelation成功！]");
				}
			}
		}catch(Exception e){
			log.error("查询UserCrossRelation信息,JSON转换对象出错！错误的原因：",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_REDIS_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception("查询UserCrossRelation信息,JSON转换对象出错！错误的原因：",e);
		}
	}
	
	/**
	 * 修改UserCrossRelation
	 * 
	 * @param crossRelation
	 * @throws Exception
	 */
	public void updateRedisUserCrossRelation(UserCrossRelation crossRelation)throws Exception{		
			UserCrossRelation temp = getRedisUserCrossRelation(crossRelation.getId().toString());
			if(temp == null){
				log.debug("[" + crossRelation.getReqId() + "]@@" + "[修改Redis的UserCrossRelation对象，查询redis的数据是Null！id是："+crossRelation.getId().toString()+"]");
				throw new Exception("[" + crossRelation.getReqId() + "]@@" + "[修改Redis的UserCrossRelation对象，查询redis的数据是Null！id是："+crossRelation.getId().toString()+"]");
			}
			log.debug("[" + crossRelation.getReqId() + "]@@" + "[修改Redis的UserCrossRelation对象，查询redis成功！]");
			BeanCopyProperties.copyProperties(crossRelation,temp, false,null);
			log.debug("[" + crossRelation.getReqId() + "]@@" + "[Group的新值覆盖旧值成功！]");
			/*添加最新的crossRelation*/
			String jsonData = JsonUtil.getObjectToJson(temp);
			log.debug("[" + crossRelation.getReqId() + "]@@" + "[UserCrossRelation的新对象转换成Json成功！]");
		try{
			jedisAgent.setOverride(CROSS + temp.getId(), jsonData);
			log.debug("[" + crossRelation.getReqId() + "]@@" + "[修改redis的UserCrossRelation成功！]");
		}catch(Exception e){
			log.error("[" + crossRelation.getReqId() + "]@@" + "[修改redis的UserCrossRelation失败！]",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_REDIS_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception("[" + crossRelation.getReqId() + "]@@" + "[修改redis的UserCrossRelation失败！]",e);
		}
	}

	public RedisAgent getJedisAgent() {
		return jedisAgent;
	}

	public void setJedisAgent(RedisAgent jedisAgent) {
		this.jedisAgent = jedisAgent;
	}

	public SentryAgent getQuickSentry() {
		return quickSentry;
	}

	public void setQuickSentry(SentryAgent quickSentry) {
		this.quickSentry = quickSentry;
	}
}
