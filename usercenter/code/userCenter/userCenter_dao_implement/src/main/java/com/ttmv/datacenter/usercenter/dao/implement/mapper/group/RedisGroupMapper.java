package com.ttmv.datacenter.usercenter.dao.implement.mapper.group;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.agent.redis.RedisAgent;
import com.ttmv.datacenter.sentry.SentryAgent;
import com.ttmv.datacenter.usercenter.dao.implement.constant.Constant;
import com.ttmv.datacenter.usercenter.dao.implement.util.JsonUtil;
import com.ttmv.datacenter.usercenter.domain.data.Group;
import com.ttmv.datacenter.usercenter.domain.data.UserCrossRelation;

public class RedisGroupMapper {
	
	private final Logger log = LogManager.getLogger(RedisGroupMapper.class);
	
	/*key的前缀*/
	private static final String GROUP = "GROUP_";
	private RedisAgent jedisAgent;
	private final int REDIS_KEY_TIMEOUT = 86400;	
	private SentryAgent quickSentry;
	
	public RedisAgent getJedisAgent() {
		return jedisAgent;
	}

	public void setJedisAgent(RedisAgent jedisAgent) {
		this.jedisAgent = jedisAgent;
	}

	/**
	 * 添加好友分组
	 * @param key
	 * @param groupJson
	 * @throws Excetpion
	 */
	public void addRedisGroup(String id,String groupJson,String reqID)throws Exception{
		try{
			/* 向redis缓存中添加全部数据userInfoJson ,设置key的时效性 24小时 */
			jedisAgent.setIfNotExist(GROUP+id,groupJson);
			log.debug("[" + reqID + "]@@" + "[redis添加Group成功！]");
		}catch(Exception e){
			log.error("[" + reqID + "]@@" + "[redis添加Group失败！]",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_REDIS_ERROR_MSG+ e.getMessage(), Constant.UC_ERROR);
			throw new Exception("[" + reqID + "]@@" + "[redis添加Group失败！]",e);
		}
	}
	
	/**
	 * 修改好友分组
	 * @param key
	 * @param groupJson
	 * @throws Excetpion
	 */
	public void updateRedisGroup(String id,String groupJson,String reqID)throws Exception{
		try{
			jedisAgent.setOverride(GROUP+id, groupJson);
			log.debug("[" + reqID + "]@@" + "[redis修改Group成功！]");
		}catch(Exception e){
			log.error("[" + reqID + "]@@" + "[redis修改Group失败！]",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_REDIS_ERROR_MSG+ e.getMessage(), Constant.UC_ERROR);
			throw new Exception("[" + reqID + "]@@" + "[redis修改Group失败！]",e);
		}
	}
	
	/**
	 *  通过key查询Group
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Group getRedisGroup(String id)throws Exception{
		String jsonData = null;
		try{
			jsonData = jedisAgent.getValue(GROUP + id);
		}catch(Exception e){
			log.error("redis查询Group失败！失败的原因：",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_REDIS_ERROR_MSG+ e.getMessage(), Constant.UC_ERROR);
		}	
		try{
			if(jsonData != null ){
				Group group = (Group)JsonUtil.getObjectFromJson(jsonData,Group.class);
				log.debug("redis查询Group成功！");
				return group;
			}
		}catch(Exception e){
			log.error("查询Group信息,JSON转换对象出错！错误的原因：",e);
			throw new Exception("查询Group信息,JSON转换对象出错！错误的原因：",e);
		}	
		return null;
	} 
	
	/**
	 * 通过key删除Group
	 * @param id
	 * @throws Exception
	 */
	public void deleteRedisGroup(String id,String reqID) throws Exception{
		try{
			if(id != null){
				jedisAgent.deleteData(GROUP + id);
				log.debug("[" + reqID + "]@@" + "[redis删除Group成功！]");
			}
		}catch(Exception e){
			log.error("[" + reqID + "]@@" + "[redis删除Group失败！]",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_REDIS_ERROR_MSG+ e.getMessage(), Constant.UC_ERROR);
			throw new Exception("[" + reqID + "]@@" + "[redis删除Group失败！]",e);
		}
	}

	public SentryAgent getQuickSentry() {
		return quickSentry;
	}

	public void setQuickSentry(SentryAgent quickSentry) {
		this.quickSentry = quickSentry;
	}
}
