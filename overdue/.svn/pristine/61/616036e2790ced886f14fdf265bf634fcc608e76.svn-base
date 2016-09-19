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
@Component("redisRoomForbiddenExpireMapper")
public class RedisRoomForbiddenExpireMapper {

	private final Logger log = LogManager.getLogger(RedisRoomForbiddenExpireMapper.class);
	private final String RFE  = "RFE";
	
	@Resource(name = "quickSentry")
	private SentryAgent quickSentry;
		
	@Resource(name = "jedisAgent")
	private RedisAgent jedisAgent;

	/**
	 * 添加RedisRoomForbiddenExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void addRedisRoomForbiddenExpire(String key,long timStemp)throws Exception{
		try {
			jedisAgent.zsetAdd(RFE,key,timStemp);
			log.debug("[redis添加RedisRoomForbiddenExpire成功！]");
		} catch (Exception e) {
			log.debug("[redis添加RedisRoomForbiddenExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis添加RedisRoomForbiddenExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 批量添加RedisRoomForbiddenExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void addListRedisRoomForbiddenExpire(List<SetCollectionBean> lists)throws Exception{
		try {
			jedisAgent.zsetPipAdd(lists);
			log.debug("[redis添加列表RedisRoomForbiddenExpire成功！]");
		} catch (Exception e) {
			log.error("[redis添加列表RedisRoomForbiddenExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis添加列表RedisRoomForbiddenExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 批量删除RedisRoomForbiddenExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void deleteListRedisRoomForbiddenExpire(List<SetCollectionBean> lists)throws Exception{
		try {
			jedisAgent.zsetPipDelete(lists);
			log.debug("[redis删除列表RedisRoomForbiddenExpire成功！]");
		} catch (Exception e) {
			log.error("[redis删除列表RedisRoomForbiddenExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis删除列表RedisRoomForbiddenExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 查询全部ForbiddenExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Set<Tuple> getAllRedisRoomForbiddenExpire()throws Exception{
		try{
			Set<Tuple> sets = jedisAgent.zsetGetAll(RFE);
			return sets;
		}catch(Exception e){
			log.error("查询全部RedisForbiddenExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("查询全部RedisForbiddenExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
		}
	}
	
	/**
	 * 修改RedisRoomForbiddenExpire
	 * @param key
	 * @param json
	 * @throws Exception
	 */
	public void updateRedisRoomForbiddenExpire(String key,long timStemp)throws Exception{
		try {
			jedisAgent.zsetAdd(RFE,key,timStemp);
			log.debug("[redis修改RedisRoomForbiddenExpire成功！]");
		} catch (Exception e) {
			log.error("[redis修改RedisRoomForbiddenExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis修改RedisRoomForbiddenExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 查询RoomForbiddenExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public long  getRedisRoomForbiddenExpire(String key)throws Exception{
		long timStemp;
		try{
			timStemp = (long) jedisAgent.zsetGetValue(RFE ,key);
			return timStemp;
		}catch(Exception e){
			log.error("查询RedisRoomForbiddenExpire信息出错，跳过查询！错误的原因：" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("查询UserInfo信息出错，跳过查询！错误的原因：" + e.getMessage());
		}
	}
	
	/**
	 * 删除RedisRoomForbiddenExpire
	 * @param id
	 * @throws Exception
	 */
	public void deleteRedisRoomForbiddenExpire(String key)throws Exception{
		try{
			if( key != null){
				jedisAgent.zsetDelete(RFE, key);
				log.debug("[redis删除RedisRoomForbiddenExpire成功！]");
			}
		}catch(Exception e){
			log.error("[redis删除RedisRoomForbiddenExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis删除RedisRoomForbiddenExpire失败！]" + e.getMessage());
		}
	}
	
	/**
	 * 值范围查询RedisRoomForbiddenExpire
	 * @param id
	 * @throws Exception
	 */
	public Set<Tuple> getRangeRedisRoomForbiddenExpire(Long startTime,Long endTime)throws Exception{
		try{
			if(startTime != null && endTime != null){
				Set<Tuple> sets = jedisAgent.zsetRangeByScore(RFE, startTime, endTime);
				log.debug("[redis查询RedisRoomForbiddenExpire成功！]");
				return sets;
			}
		}catch(Exception e){
			log.error("[redis查询RedisRoomForbiddenExpire失败！]" + e.getMessage());
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_REDIS_ERROR_MSG+ e.getMessage(), Constant.OD_ERROR);
			throw new Exception("[redis查询RedisRoomForbiddenExpire失败！]" + e.getMessage());
		}
		return null;
	}
}
