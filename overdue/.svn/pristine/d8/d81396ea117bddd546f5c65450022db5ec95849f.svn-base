package com.ttmv.dao.mapper.redis;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Tuple;

import com.ttmv.dao.constant.Constant;
import com.ttmv.datacenter.agent.redis.RedisAgent;
import com.ttmv.datacenter.agent.redis.beans.SetCollectionBean;
import com.ttmv.datacenter.sentry.SentryAgent;

/**
 * Redis 频道冻结到期
 * @author wll
 *
 */
@Component("redisLiveRoomBanExpireMapper")
public class RedisLiveRoomBanExpireMapper {

	private final Logger log = LogManager.getLogger(RedisLiveRoomBanExpireMapper.class);
	private final String LRBE  = "LRBE";
	
	@Resource(name = "quickSentry")
	private SentryAgent quickSentry;
		
	@Resource(name = "jedisAgent")
	private RedisAgent jedisAgent;

	/**
	 * 添加RedisLiveRoomBanExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void addRedisLiveRoomBanExpire(String key,long timStemp)throws Exception{
		try {
			jedisAgent.zsetAdd(LRBE,key,timStemp);
			log.debug("[redis添加RedisLiveRoomBanExpire成功！]");
		} catch (Exception e) {
			log.error("[redis添加RedisLiveRoomBanExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis添加RedisLiveRoomBanExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 批量添加RedisLiveRoomBanExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void addListRedisLiveRoomBanExpire(List<SetCollectionBean> lists)throws Exception{
		try {
			jedisAgent.zsetPipAdd(lists);
			log.debug("[redis添加列表RedisLiveRoomBanExpire成功！]");
		} catch (Exception e) {
			log.error("[redis添加列表RedisLiveRoomBanExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis添加列表RedisLiveRoomBanExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 批量删除RedisLiveRoomBanExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void deleteListRedisLiveRoomBanExpire(List<SetCollectionBean> lists)throws Exception{
		try {
			jedisAgent.zsetPipDelete(lists);
			log.debug("[redis删除列表RedisLiveRoomBanExpire成功！]");
		} catch (Exception e) {
			log.error("[redis删除列表RedisLiveRoomBanExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis删除列表RedisLiveRoomBanExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 查询全部LiveRoomBanExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Set<Tuple> getAllRedisLiveRoomBanExpire()throws Exception{
		try{
			Set<Tuple> sets = jedisAgent.zsetGetAll(LRBE);
			return sets;
		}catch(Exception e){
			log.error("查询全部RedisLiveRoomBanExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("查询全部RedisLiveRoomBanExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
		}
	}
	
	/**
	 * 修改RedisLiveRoomBanExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void updateRedisLiveRoomBanExpire(String key,long timStemp)throws Exception{
		try {
			jedisAgent.zsetAdd(LRBE,key,timStemp);
			log.debug("[redis修改RedisLiveRoomBanExpire成功！]");
		} catch (Exception e) {
			log.error("[redis修改RedisLiveRoomBanExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis修改RedisLiveRoomBanExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 查询LiveRoomBanExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public long  getRedisLiveRoomBanExpire(String key)throws Exception{
		long timStemp;
		try{
			timStemp = (long) jedisAgent.zsetGetValue(LRBE ,key);
			return timStemp;
		}catch(Exception e){
			log.error("查询RedisLiveRoomBanExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("查询UserInfo信息出错，跳过查询！错误的原因：" + e.getMessage());
		}
	}
	
	/**
	 * 删除RedisLiveRoomBanExpire
	 * @param id
	 * @throws Exception
	 */
	public void deleteRedisLiveRoomBanExpire(String key)throws Exception{
		try{
			if( key != null){
				jedisAgent.zsetDelete(LRBE, key);
				log.debug("[redis删除RedisLiveRoomBanExpire成功！]");
			}
		}catch(Exception e){
			log.error("[redis删除RedisLiveRoomBanExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis删除RedisLiveRoomBanExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 值范围查询RedisLiveRoomBanExpire
	 * @param id
	 * @throws Exception
	 */
	public Set<Tuple> getRangeRedisLiveRoomBanExpire(Long startTime,Long endTime)throws Exception{
		try{
			if(startTime != null && endTime != null){
				Set<Tuple> sets = jedisAgent.zsetRangeByScore(LRBE, startTime, endTime);
				log.debug("[redis查询RedisLiveRoomBanExpire成功！]");
				return sets;
			}
		}catch(Exception e){
			log.error("[redis查询RedisLiveRoomBanExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis查询RedisLiveRoomBanExpire失败！]" + e.getMessage());
		}
		return null;
	}
}
