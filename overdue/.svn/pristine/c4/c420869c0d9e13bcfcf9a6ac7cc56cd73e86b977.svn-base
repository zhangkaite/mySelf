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
 * 用户账户冻结到期
 * @author wll
 *
 */
@Component("redisUserForbiddenExpireMapper")
public class RedisUserForbiddenExpireMapper {

	private final Logger log = LogManager.getLogger(RedisUserForbiddenExpireMapper.class);
	private final String UFE  = "UFE";
	
	@Resource(name = "quickSentry")
	private SentryAgent quickSentry;
		
	@Resource(name = "jedisAgent")
	private RedisAgent jedisAgent;

	/**
	 * 添加RedisUserForbiddenExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void addRedisUserForbiddenExpire(String key,long timStemp)throws Exception{
		try {
			jedisAgent.zsetAdd(UFE,key,timStemp);
			log.debug("[redis添加RedisUserForbiddenExpire成功！]");
		} catch (Exception e) {
			log.debug("[redis添加RedisUserForbiddenExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis添加RedisUserForbiddenExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 批量添加RedisUserForbiddenExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void addListRedisUserForbiddenExpire(List<SetCollectionBean> lists)throws Exception{
		try {
			jedisAgent.zsetPipAdd(lists);
			log.debug("[redis添加列表RedisUserForbiddenExpire成功！]");
		} catch (Exception e) {
			log.debug("[redis添加列表RedisUserForbiddenExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis添加列表RedisUserForbiddenExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 批量删除RedisUserForbiddenExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void deleteListRedisUserForbiddenExpire(List<SetCollectionBean> lists)throws Exception{
		try {
			jedisAgent.zsetPipDelete(lists);
			log.debug("[redis删除列表RedisUserForbiddenExpire成功！]");
		} catch (Exception e) {
			log.error("[redis删除列表RedisUserForbiddenExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis删除列表RedisUserForbiddenExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 查询全部UserForbiddenExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Set<Tuple> getAllRedisUserForbiddenExpire()throws Exception{
		try{
			Set<Tuple> sets = jedisAgent.zsetGetAll(UFE);
			return sets;
		}catch(Exception e){
			log.error("查询全部RedisUserForbiddenExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("查询全部RedisUserForbiddenExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
		}
	}
	
	/**
	 * 修改RedisUserForbiddenExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void updateRedisUserForbiddenExpire(String key,long timStemp)throws Exception{
		try {
			jedisAgent.zsetAdd(UFE,key,timStemp);
			log.debug("[redis修改RedisUserForbiddenExpire成功！]");
		} catch (Exception e) {
			log.error("[redis修改RedisUserForbiddenExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis修改RedisUserForbiddenExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 查询UserForbiddenExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public long  getRedisUserForbiddenExpire(String key)throws Exception{
		long timStemp;
		try{
			timStemp = (long) jedisAgent.zsetGetValue(UFE ,key);
			return timStemp;
		}catch(Exception e){
			log.error("查询RedisUserForbiddenExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("查询UserInfo信息出错，跳过查询！错误的原因：" + e.getMessage());
		}
	}
	
	/**
	 * 删除RedisUserForbiddenExpire
	 * @param id
	 * @throws Exception
	 */
	public void deleteRedisUserForbiddenExpire(String key)throws Exception{
		try{
			if( key != null){
				jedisAgent.zsetDelete(UFE, key);
				log.debug("[redis删除RedisUserForbiddenExpire成功！]");
			}
		}catch(Exception e){
			log.error("[redis删除RedisUserForbiddenExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis删除RedisUserForbiddenExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 值范围查询RedisUserForbiddenExpire
	 * @param id
	 * @throws Exception
	 */
	public Set<Tuple> getRangeRedisUserForbiddenExpire(Long startTime,Long endTime)throws Exception{
		try{
			if(startTime != null && endTime != null){
				Set<Tuple> sets = jedisAgent.zsetRangeByScore(UFE, startTime, endTime);
				log.debug("[redis查询RedisUserForbiddenExpire成功！]");
				return sets;
			}
		}catch(Exception e){
			log.error("[redis查询RedisUserForbiddenExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis查询RedisUserForbiddenExpire失败！]" + e.getMessage());
		}
		return null;
	}
}
