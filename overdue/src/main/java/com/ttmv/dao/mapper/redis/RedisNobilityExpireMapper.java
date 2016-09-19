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
 *redis 爵位到期
 * @author wll
 *
 */
@Component("redisNobilityExpireMapper")
public class RedisNobilityExpireMapper {

	private final Logger log = LogManager.getLogger(RedisNobilityExpireMapper.class);
	private final String NE  = "NE";
	
	@Resource(name = "quickSentry")
	private SentryAgent quickSentry;
		
	@Resource(name = "jedisAgent")
	private RedisAgent jedisAgent;

	/**
	 * 添加RedisNobilityExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void addRedisNobilityExpire(String key,long timStemp)throws Exception{
		try {
			jedisAgent.zsetAdd(NE,key,timStemp);
			log.debug("[redis添加RedisNobilityExpire成功！]");
		} catch (Exception e) {
			log.error("[redis添加RedisNobilityExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis添加RedisNobilityExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 批量添加RedisNobilityExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void addListRedisNobilityExpire(List<SetCollectionBean> lists)throws Exception{
		try {
			jedisAgent.zsetPipAdd(lists);
			log.info("[redis添加列表RedisNobilityExpire成功！]");
		} catch (Exception e) {
			log.error("[redis添加列表RedisNobilityExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis添加列表RedisNobilityExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 批量删除RedisNobilityExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void deleteListRedisNobilityExpire(List<SetCollectionBean> lists)throws Exception{
		try {
			jedisAgent.zsetPipDelete(lists);
			log.debug("[redis删除列表RedisNobilityExpire成功！]");
		} catch (Exception e) {
			log.error("[redis删除列表RedisNobilityExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis删除列表RedisNobilityExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 查询全部NobilityExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Set<Tuple> getAllRedisNobilityExpire()throws Exception{
		try{
			Set<Tuple> sets = jedisAgent.zsetGetAll(NE);
			return sets;
		}catch(Exception e){
			log.error("查询全部RedisNobilityExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("查询全部RedisNobilityExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
		}
	}
	
	/**
	 * 修改RedisNobilityExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void updateRedisNobilityExpire(String key,long timStemp)throws Exception{
		try {
			jedisAgent.zsetAdd(NE,key,timStemp);
			log.debug("[redis修改RedisNobilityExpire成功！]");
		} catch (Exception e) {
			log.error("[redis修改RedisNobilityExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis修改RedisNobilityExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 查询NobilityExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public long  getRedisNobilityExpire(String key)throws Exception{
		long timStemp;
		try{
			timStemp = (long) jedisAgent.zsetGetValue(NE ,key);
			return timStemp;
		}catch(Exception e){
			log.error("查询RedisNobilityExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("查询UserInfo信息出错，跳过查询！错误的原因：" + e.getMessage());
		}
	}
	
	/**
	 * 删除RedisNobilityExpire
	 * @param id
	 * @throws Exception
	 */
	public void deleteRedisNobilityExpire(String key)throws Exception{
		try{
			if( key != null){
				jedisAgent.zsetDelete(NE, key);
				log.debug("[redis删除RedisNobilityExpire成功！]");
			}
		}catch(Exception e){
			log.error("[redis删除RedisNobilityExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis删除RedisNobilityExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 值范围查询RedisNobilityExpire
	 * @param id
	 * @throws Exception
	 */
	public Set<Tuple> getRangeRedisNobilityExpire(Long startTime,Long endTime)throws Exception{
		try{
			if(startTime != null && endTime != null){
				Set<Tuple> sets = jedisAgent.zsetRangeByScore(NE, startTime, endTime);
				log.debug("[redis查询RedisNobilityExpire成功！]");
				return sets;
			}
		}catch(Exception e){
			log.error("[redis查询RedisNobilityExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis查询RedisNobilityExpire失败！]" + e.getMessage());
		}
		return null;
	}
}
