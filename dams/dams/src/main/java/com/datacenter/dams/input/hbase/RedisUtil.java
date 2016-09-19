package com.datacenter.dams.input.hbase;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Tuple;

/***
 * 
 * jedis通过连接池查询 zset集合数据
 * 
 * @author kate
 *
 */
public class RedisUtil {
	private JedisPool jedisPool = null;

	private final static Logger logger = LogManager.getLogger(RedisUtil.class);

	/**
	 * 实例化redispool
	 * 
	 * @param redisHost
	 * @param redisPort
	 * @param maxTotal
	 * @param maxIdle
	 * @param maxWaitMillis
	 * @param timeout
	 */
	public RedisUtil(String redisHost, int redisPort, int maxIdle, int maxWaitMillis, int timeout) throws Exception {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxIdle(maxIdle);
			config.setMaxWaitMillis(maxWaitMillis);
			jedisPool = new JedisPool(config, redisHost, redisPort, timeout);
		} catch (Exception e) {
			logger.error("吊牌活动redis连接池初始化失败，失败的原因:", e);
		}

	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	public synchronized Jedis getJedis() throws Exception {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				return resource;
			} else {
				throw new Exception("初始化吊牌活动连接池失败，没有获取到连接池");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	@SuppressWarnings("deprecation")
	public void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

	/***
	 * 获取zset集合里面的所有数据
	 * 排序默认是从小到大
	 */
	public Set<Tuple> getZset(String key, long  min, long  max) throws Exception {
		Set<Tuple> resultSet = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			resultSet = jedis.zrangeWithScores(key, min, max);
		} catch (Exception e) {
			throw e;
		} finally {
			returnResource(jedis);
		}
		return resultSet;
	}

}
