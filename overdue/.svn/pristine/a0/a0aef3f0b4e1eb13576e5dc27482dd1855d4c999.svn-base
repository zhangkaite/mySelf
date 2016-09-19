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
 * Redis Vip到期
 * @author wll
 *
 */
@Component("redisVipExpireMapper")
public class RedisVipExpireMapper {

	private final Logger log = LogManager.getLogger(RedisVipExpireMapper.class);
	private final String VE  = "VE";
	
	@Resource(name = "quickSentry")
	private SentryAgent quickSentry;
		
	@Resource(name = "jedisAgent")
	private RedisAgent jedisAgent;

	/**
	 * 添加RedisVipExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void addRedisVipExpire(String key,long timStemp)throws Exception{
		try {
			jedisAgent.zsetAdd(VE,key,timStemp);
			log.debug("[redis添加RedisVipExpire成功！]");
		} catch (Exception e) {
			log.error("[redis添加RedisVipExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis添加RedisVipExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 批量添加RedisVipExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void addListRedisVipExpire(List<SetCollectionBean> lists)throws Exception{
		try {
			jedisAgent.zsetPipAdd(lists);
			log.debug("[redis添加列表RedisVipExpire成功！]");
		} catch (Exception e) {
			log.error("[redis添加列表RedisVipExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis添加列表RedisVipExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 批量删除RedisVipExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void deleteListRedisVipExpire(List<SetCollectionBean> lists)throws Exception{
		try {
			jedisAgent.zsetPipDelete(lists);
			log.debug("[redis删除列表RedisVipExpire成功！]");
		} catch (Exception e) {
			log.error("[redis删除列表RedisVipExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis删除列表RedisVipExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 查询全部VipExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Set<Tuple> getAllRedisVipExpire()throws Exception{
		try{
			Set<Tuple> sets = jedisAgent.zsetGetAll(VE);
			return sets;
		}catch(Exception e){
			log.error("查询全部RedisVipExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("查询全部RedisVipExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
		}
	}
	
	/**
	 * 修改RedisVipExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void updateRedisVipExpire(String key,long timStemp)throws Exception{
		try {
			jedisAgent.zsetAdd(VE,key,timStemp);
			log.debug("[redis修改RedisVipExpire成功！]");
		} catch (Exception e) {
			log.error("[redis修改RedisVipExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis修改RedisVipExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 查询VipExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public long  getRedisVipExpire(String key)throws Exception{
		long timStemp;
		try{
			timStemp = (long) jedisAgent.zsetGetValue(VE ,key);
			return timStemp;
		}catch(Exception e){
			log.error("查询RedisVipExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("查询UserInfo信息出错，跳过查询！错误的原因：" + e.getMessage());
		}
	}
	
	/**
	 * 删除RedisVipExpire
	 * @param id
	 * @throws Exception
	 */
	public void deleteRedisVipExpire(String key)throws Exception{
		try{
			if( key != null){
				jedisAgent.zsetDelete(VE, key);
				log.debug("[redis删除RedisVipExpire成功！]");
			}
		}catch(Exception e){
			log.error("[redis删除RedisVipExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis删除RedisVipExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 值范围查询RedisVipExpire
	 * @param id
	 * @throws Exception
	 */
	public Set<Tuple> getRangeRedisVipExpire(Long startTime,Long endTime)throws Exception{
		try{
			if(startTime != null && endTime != null){
				Set<Tuple> sets = jedisAgent.zsetRangeByScore(VE, startTime, endTime);
				log.debug("[redis查询RedisVipExpire成功！]");
				return sets;
			}
		}catch(Exception e){
			log.error("[redis查询RedisVipExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis查询RedisVipExpire失败！]" + e.getMessage());
		}
		return null;
	}
}
