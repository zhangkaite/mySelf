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
 * 豪车到期
 * @author wll
 *
 */
@Component("redisLuxuryExpireMapper")
public class RedisLuxuryExpireMapper {

	private final Logger log = LogManager.getLogger(RedisLuxuryExpireMapper.class);
	private final String LE  = "LE";
	
	@Resource(name = "quickSentry")
	private SentryAgent quickSentry;
		
	@Resource(name = "jedisAgent")
	private RedisAgent jedisAgent;

	/**
	 * 添加RedisLuxuryExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void addRedisLuxuryExpire(String key,long timStemp)throws Exception{
		try {
			jedisAgent.zsetAdd(LE,key,timStemp);
			log.debug("[redis添加RedisLuxuryExpire成功！]");
		} catch (Exception e) {
			log.error("[redis添加RedisLuxuryExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis添加RedisLuxuryExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 批量添加RedisLuxuryExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void addListRedisLuxuryExpire(List<SetCollectionBean> lists)throws Exception{
		try {
			jedisAgent.zsetPipAdd(lists);
			log.debug("[redis添加列表RedisLuxuryExpire成功！]");
		} catch (Exception e) {
			log.error("[redis添加列表RedisLuxuryExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis添加列表RedisLuxuryExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 批量删除RedisLuxuryExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void deleteListRedisLuxuryExpire(List<SetCollectionBean> lists)throws Exception{
		try {
			jedisAgent.zsetPipDelete(lists);
			log.debug("[redis删除列表RedisLuxuryExpire成功！]");
		} catch (Exception e) {
			log.error("[redis删除列表RedisLuxuryExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis删除列表RedisLuxuryExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 查询全部LuxuryExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Set<Tuple> getAllRedisLuxuryExpire()throws Exception{
		try{
			Set<Tuple> sets = jedisAgent.zsetGetAll(LE);
			return sets;
		}catch(Exception e){
			log.error("查询全部RedisLuxuryExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("查询全部RedisLuxuryExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
		}
	}
	
	/**
	 * 修改RedisLuxuryExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void updateRedisLuxuryExpire(String key,long timStemp)throws Exception{
		try {
			jedisAgent.zsetAdd(LE,key,timStemp);
			log.debug("[redis修改RedisLuxuryExpire成功！]");
		} catch (Exception e) {
			log.error("[redis修改RedisLuxuryExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis修改RedisLuxuryExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 查询LuxuryExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public long  getRedisLuxuryExpire(String key)throws Exception{
		long timStemp;
		try{
			timStemp = (long) jedisAgent.zsetGetValue(LE ,key);
			return timStemp;
		}catch(Exception e){
			log.error("查询RedisLuxuryExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("查询UserInfo信息出错，跳过查询！错误的原因：" + e.getMessage());
		}
	}
	
	/**
	 * 删除RedisLuxuryExpire
	 * @param id
	 * @throws Exception
	 */
	public void deleteRedisLuxuryExpire(String key)throws Exception{
		try{
			if( key != null){
				jedisAgent.zsetDelete(LE, key);
				log.debug("[redis删除RedisLuxuryExpire成功！]");
			}
		}catch(Exception e){
			log.error("[redis删除RedisLuxuryExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis删除RedisLuxuryExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 值范围查询RedisLuxuryExpire
	 * @param id
	 * @throws Exception
	 */
	public Set<Tuple> getRangeRedisLuxuryExpire(Long startTime,Long endTime)throws Exception{
		try{
			if(startTime != null && endTime != null){
				Set<Tuple> sets = jedisAgent.zsetRangeByScore(LE, startTime, endTime);
				log.debug("[redis查询RedisLuxuryExpire成功！]");
				return sets;
			}
		}catch(Exception e){
			log.error("[redis查询RedisLuxuryExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis查询RedisLuxuryExpire失败！]" + e.getMessage());
		}
		return null;
	}
}
