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
 * 靓号到期
 * @author wll
 *
 */
@Component("redisGoodNumberExpireMapper")
public class RedisGoodNumberExpireMapper {

	private final Logger log = LogManager.getLogger(RedisGoodNumberExpireMapper.class);
	private final String GNE  = "GNE";
	
	@Resource(name = "quickSentry")
	private SentryAgent quickSentry;
		
	@Resource(name = "jedisAgent")
	private RedisAgent jedisAgent;

	/**
	 * 添加RedisGoodNumberExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void addRedisGoodNumberExpire(String key,long timStemp)throws Exception{
		try {
			jedisAgent.zsetAdd(GNE,key,timStemp);
			log.debug("[redis添加RedisGoodNumberExpire成功！]");
		} catch (Exception e) {
			log.error("[redis添加RedisGoodNumberExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis添加RedisGoodNumberExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 批量添加RedisGoodNumberExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void addListRedisGoodNumberExpire(List<SetCollectionBean> lists)throws Exception{
		try {
			jedisAgent.zsetPipAdd(lists);
			log.debug("[redis添加列表RedisGoodNumberExpire成功！]");
		} catch (Exception e) {
			log.error("[redis添加列表RedisGoodNumberExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis添加列表RedisGoodNumberExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 批量删除RedisGoodNumberExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void deleteListRedisGoodNumberExpire(List<SetCollectionBean> lists)throws Exception{
		try {
			jedisAgent.zsetPipDelete(lists);
			log.debug("[redis删除列表RedisGoodNumberExpire成功！]");
		} catch (Exception e) {
			log.error("[redis删除列表RedisGoodNumberExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis删除列表RedisGoodNumberExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 查询全部GoodNumberExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Set<Tuple> getAllRedisGoodNumberExpire()throws Exception{
		try{
			Set<Tuple> sets = jedisAgent.zsetGetAll(GNE);
			return sets;
		}catch(Exception e){
			log.error("查询全部RedisGoodNumberExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("查询全部RedisGoodNumberExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
		}
	}
	
	/**
	 * 修改RedisGoodNumberExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void updateRedisGoodNumberExpire(String key,long timStemp)throws Exception{
		try {
			jedisAgent.zsetAdd(GNE,key,timStemp);
			log.debug("[redis修改RedisGoodNumberExpire成功！]");
		} catch (Exception e) {
			log.error("[redis修改RedisGoodNumberExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis修改RedisGoodNumberExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 查询GoodNumberExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public long  getRedisGoodNumberExpire(String key)throws Exception{
		long timStemp;
		try{
			timStemp = (long) jedisAgent.zsetGetValue(GNE ,key);
			return timStemp;
		}catch(Exception e){
			log.error("查询RedisGoodNumberExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("查询UserInfo信息出错，跳过查询！错误的原因：" + e.getMessage());
		}
	}
	
	/**
	 * 删除RedisGoodNumberExpire
	 * @param id
	 * @throws Exception
	 */
	public void deleteRedisGoodNumberExpire(String key)throws Exception{
		try{
			if( key != null){
				jedisAgent.zsetDelete(GNE, key);
				log.debug("[redis删除RedisGoodNumberExpire成功！]");
			}
		}catch(Exception e){
			log.error("[redis删除RedisGoodNumberExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis删除RedisGoodNumberExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 值范围查询RedisGoodNumberExpire
	 * @param id
	 * @throws Exception
	 */
	public Set<Tuple> getRangeRedisGoodNumberExpire(Long startTime,Long endTime)throws Exception{
		try{
			if(startTime != null && endTime != null){
				Set<Tuple> sets = jedisAgent.zsetRangeByScore(GNE, startTime, endTime);
				log.debug("[redis查询RedisGoodNumberExpire成功！]");
				return sets;
			}
		}catch(Exception e){
			log.error("[redis查询RedisGoodNumberExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis查询RedisGoodNumberExpire失败！]" + e.getMessage());
		}
		return null;
	}
}
