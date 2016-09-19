package com.ttmv.datacenter.usercenter.dao.implement.mapper.userinfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.agent.redis.RedisAgent;
import com.ttmv.datacenter.sentry.SentryAgent;
import com.ttmv.datacenter.usercenter.dao.implement.constant.Constant;
import com.ttmv.datacenter.usercenter.dao.implement.util.JsonUtil;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;

public class RedisUserInfoMapper {

	private final Logger log = LogManager.getLogger(RedisUserInfoMapper.class);
	private SentryAgent quickSentry;
	
	private final String UI  = "UI_";
	private RedisAgent jedisAgent;
	private final int REDIS_KEY_TIMEOUT = 84600;	

	/**
	 * 注册用户：数据添加Redis
	 * 
	 * @param userInfo
	 * @throws Exception
	 */
	public void addUserInfoInRedis(String key,String userInfoJson,String reqID) throws Exception {
		try {
			/* 向redis缓存中添加全部数据userInfoJson ,设置key的时效性 24小时 */
			jedisAgent.setIfNotExist(UI + key,userInfoJson);
			log.debug("[" + reqID + "]@@" + "[redis添加UserInfo成功！]");
		} catch (Exception e) {
			log.error("[" + reqID + "]@@" + "[redis添加UserInfo失败！]" ,e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_REDIS_ERROR_MSG+ e.getMessage(), Constant.UC_ERROR);
			throw new Exception("[" + reqID + "]@@" + "[redis添加UserInfo失败！]",e);
		}
	}
	
	/**
	 * 注册用户：数据添加Redis
	 * 
	 * @param userInfo
	 * @throws Exception
	 */
	public void updateUserInfoInRedis(String key,String userInfoJson,String reqID) throws Exception {
		try {
			/* 向redis缓存中添加全部数据userInfoJson */
			jedisAgent.setOverride(UI + key, userInfoJson);
			log.debug("[" + reqID + "]@@" + "[redis修改UserInfo成功！]");
		} catch (Exception e) {
			log.error("[" + reqID + "]@@" + "[redis修改UserInfo失败！]",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_REDIS_ERROR_MSG+ e.getMessage(), Constant.UC_ERROR);
			throw new Exception("[" + reqID + "]@@" + "[redis修改UserInfo失败！]",e);
		}
	}
	
	/**
	 * 通过id查询redis中的userInfo的数据
	 * @param userId
	 * @throws Exception
	 */
	public UserInfo  getUserInfoInRedis(String id)throws Exception{
		String jsonData = null;
		try{
			jsonData = jedisAgent.getValue(UI + id);
		}catch(Exception e){
			log.error("查询UserInfo信息出错，跳过查询！错误的原因：",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_REDIS_ERROR_MSG+ e.getCause().getMessage(), Constant.UC_ERROR);
		}
		try{
			if(jsonData != null ){
				UserInfo userInfo = (UserInfo)JsonUtil.getObjectFromJson(jsonData,UserInfo.class);
				return userInfo;
			}
		}catch(Exception e){
			log.error("查询UserInfo信息,JSON转换对象出错！错误的原因：",e);
			throw new Exception("查询UserInfo信息,JSON转换对象出错！错误的原因：",e);
		}
		return null;
	}

	/**
	 * 通过userId删除数据
	 * @param userid
	 * @throws Exception 
	 */
	public void deleteUserInfoRedis(String userid,String reqID) throws Exception{
		try{
			if(userid != null){
				if(jedisAgent.exists(UI  + userid)){				
					jedisAgent.deleteData(UI  + userid);
					log.debug("[" + reqID + "]@@" + "[redis删除UserInfo成功！]");
				}
			}
		}catch(Exception e){
			log.error("[" + reqID + "]@@" + "[redis删除UserInfo失败！]",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_REDIS_ERROR_MSG+ e.getMessage(), Constant.UC_ERROR);
			throw new Exception("[" + reqID + "]@@" + "[redis删除UserInfo失败！]",e);
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
