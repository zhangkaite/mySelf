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
 * Redis 主播禁播到期
 * @author wll
 *
 */
@Component("redisLiveAnchorBanExpireMapper")
public class RedisLiveAnchorBanExpireMapper {

	private final Logger log = LogManager.getLogger(RedisLiveAnchorBanExpireMapper.class);
	private final String LABE  = "LABE";
	
	@Resource(name = "quickSentry")
	private SentryAgent quickSentry;
		
	@Resource(name = "jedisAgent")
	private RedisAgent jedisAgent;

	/**
	 * 添加RedisLiveAnchorBanExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void addRedisLiveAnchorBanExpire(String key,long timStemp)throws Exception{
		try {
			jedisAgent.zsetAdd(LABE,key,timStemp);
			log.debug("[redis添加RedisLiveAnchorBanExpire成功！]");
		} catch (Exception e) {
			log.error("[redis添加RedisLiveAnchorBanExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis添加RedisLiveAnchorBanExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 批量添加RedisLiveAnchorBanExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void addListRedisLiveAnchorBanExpire(List<SetCollectionBean> lists)throws Exception{
		try {
			jedisAgent.zsetPipAdd(lists);
			log.debug("[redis添加列表RedisLiveAnchorBanExpire成功！]");
		} catch (Exception e) {
			log.error("[redis添加列表RedisLiveAnchorBanExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis添加列表RedisLiveAnchorBanExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 批量删除RedisLiveAnchorBanExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void deleteListRedisLiveAnchorBanExpire(List<SetCollectionBean> lists)throws Exception{
		try {
			jedisAgent.zsetPipDelete(lists);
			log.debug("[redis删除列表RedisLiveAnchorBanExpire成功！]");
		} catch (Exception e) {
			log.error("[redis删除列表RedisLiveAnchorBanExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis删除列表RedisLiveAnchorBanExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 查询全部LiveAnchorBanExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Set<Tuple> getAllRedisLiveAnchorBanExpire()throws Exception{
		try{
			Set<Tuple> sets = jedisAgent.zsetGetAll(LABE);
			return sets;
		}catch(Exception e){
			log.error("查询全部RedisLiveAnchorBanExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("查询全部RedisLiveAnchorBanExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
		}
	}
	
	/**
	 * 修改RedisLiveAnchorBanExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void updateRedisLiveAnchorBanExpire(String key,long timStemp)throws Exception{
		try {
			jedisAgent.zsetAdd(LABE,key,timStemp);
			log.debug("[redis修改RedisLiveAnchorBanExpire成功！]");
		} catch (Exception e) {
			log.error("[redis修改RedisLiveAnchorBanExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis修改RedisLiveAnchorBanExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 查询LiveAnchorBanExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public long  getRedisLiveAnchorBanExpire(String key)throws Exception{
		long timStemp;
		try{
			timStemp = (long) jedisAgent.zsetGetValue(LABE ,key);
			return timStemp;
		}catch(Exception e){
			log.error("查询RedisLiveAnchorBanExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("查询UserInfo信息出错，跳过查询！错误的原因：" + e.getMessage());
		}
	}
	
	/**
	 * 删除RedisLiveAnchorBanExpire
	 * @param id
	 * @throws Exception
	 */
	public void deleteRedisLiveAnchorBanExpire(String key)throws Exception{
		try{
			if( key != null){
				jedisAgent.zsetDelete(LABE, key);
				log.debug("[redis删除RedisLiveAnchorBanExpire成功！]");
			}
		}catch(Exception e){
			log.error("[redis删除RedisLiveAnchorBanExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis删除RedisLiveAnchorBanExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 值范围查询RedisLiveAnchorBanExpire
	 * @param id
	 * @throws Exception
	 */
	public Set<Tuple> getRangeRedisLiveAnchorBanExpire(Long startTime,Long endTime)throws Exception{
		try{
			if(startTime != null && endTime != null){
				Set<Tuple> sets = jedisAgent.zsetRangeByScore(LABE, startTime, endTime);
				log.debug("[redis查询RedisLiveAnchorBanExpire成功！]");
				return sets;
			}
		}catch(Exception e){
			log.error("[redis查询RedisLiveAnchorBanExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis查询RedisLiveAnchorBanExpire失败！]" + e.getMessage());
		}
		return null;
	}
}
