package com.ttmv.datacenter.agent.redis.redisCloud;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ttmv.datacenter.agent.redis.beans.SetCollectionBean;
import com.ttmv.datacenter.agent.redis.tool.CheckParameterUtil;
import com.ttmv.datacenter.agent.redis.tool.ModuleInitUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.*;

import com.ttmv.datacenter.agent.redis.RedisAgent;
import com.ttmv.datacenter.agent.redis.RedisPoolConfig;

/**
 * redis 连接池+主从自动切换
 * 
 * @author Scarlett.zhou
 * @date 2015年4月24日
 */

public class JedisPoolCloudAgent implements RedisAgent {

	private Logger logger = LogManager.getLogger(JedisPoolCloudAgent.class);

	private JedisSentinelPool pool = null;
	private final int error_times = 1;

	public JedisPoolCloudAgent(String masterName, Set<String> sentinels,
			RedisPoolConfig poolConfig) {
		if (CheckParameterUtil.checkIsEmpty(masterName) || sentinels == null
				|| sentinels.size() < 1 || poolConfig == null) {
			ModuleInitUtil.failInitBySet("JedisPoolCloudAgent", new String[] {
					"masterName", "sentinels", "config" });
		}
		JedisPoolConfig config = poolConfig.getRedisPoolConfig();
		// 连接redis sentinel 监听端口
		pool = new JedisSentinelPool(masterName, sentinels, config);
	}

	/**
	 * 返还到连接池
	 * 
	 * @param pool
	 * @param redis
	 */
	public static void returnResource(JedisSentinelPool pool, Jedis redis) {
		if (redis != null) {
			pool.returnResource(redis);
		}
	}

	@Override
	public long setIfNotExist(String key, String value) throws Exception {
		logger.info("当前连接的库:" + getMaster());
		if (CheckParameterUtil.checkIsEmpty(key)
				|| CheckParameterUtil.checkIsEmpty(value)) {
			throw new Exception("传入参数不正确. [key]=" + key + " [value]=" + value);
		}
		Jedis jedis = null;
		Exception exc = null;
		long result = 0;
		for (int times = 0; times < error_times; times++) {
			try {
				jedis = pool.getResource();
				result = jedis.setnx(key, value);
			} catch (Exception e) {
				exc = new Exception("连接Redis进行setIfNotExist操作出现异常", e);
				logger.warn("连接Redis进行setIfNotExist操作出现异常，第" + (times + 1)
						+ "次尝试...");
				pool.returnBrokenResource(jedis);
				continue;
			} finally {
				returnResource(pool, jedis);
			}
			return result;
		}
		throw new Exception(exc);
	}

	/**
	 * set值到库，通过key - value的方式 (如果值存在，用新值覆盖原来的值)
	 */
	@Override
	public synchronized String setOverride(String key, String value)
			throws Exception {
		logger.info("当前连接的库:" + getMaster());
		if (CheckParameterUtil.checkIsEmpty(key)
				|| CheckParameterUtil.checkIsEmpty(value)) {
			throw new Exception("传入参数不正确. [key]=" + key + " [value]=" + value);
		}
		// 如果jedis连接失败，重新连接一次
		Exception exc = null;
		String result = null;
		Jedis jedis = null;
		for (int times = 0; times < error_times; times++) {
			try {
				jedis = pool.getResource();
				jedis.getSet(key, value);
				result = jedis.get(key);
			} catch (Exception e) {
				exc = new Exception("连接Redis进行setOverride操作出现异常", e);
				logger.warn("连接Redis进行setOverride操作出现异常，第" + (times + 1)
						+ "次尝试...");
				pool.returnBrokenResource(jedis);
				continue;
			} finally {
				returnResource(pool, jedis);
			}
			return result;
		}
		throw new Exception(exc);
	}

	/**
	 * delete一个值，通过key的方式
	 */
	@Override
	public void deleteData(String key) throws Exception {
		logger.info("当前连接的库:" + getMaster());
		if (CheckParameterUtil.checkIsEmpty(key)) {
			throw new Exception("传入参数不正确.");
		}
		// 如果jedis连接失败，重新连接一次
		Exception exc = null;
		Jedis jedis = null;
		for (int times = 0; times < error_times; times++) {
			try {
				jedis = pool.getResource();
				jedis.del(key);
			} catch (Exception e) {
				exc = new Exception("连接Redis进行deleteData操作出现异常", e);
				logger.warn("连接Redis进行deleteData操作出现异常，第" + (times + 1)
						+ "次尝试...");
				pool.returnBrokenResource(jedis);
				continue;
			} finally {
				returnResource(pool, jedis);
			}
			return;
		}
		throw new Exception(exc);
	}

	/**
	 * set值到库并设置这个值的生存时间，通过key - time的方式
	 */
	@Override
	public void setDataTimeout(String key, int timeout) throws Exception {
		logger.info("当前连接的库:" + getMaster());
		if (CheckParameterUtil.checkIsEmpty(key) || timeout < 1) {
			throw new Exception("传入参数不正确.");
		}
		// 如果jedis连接失败，重新连接一次
		Exception exc = null;
		Jedis jedis = null;
		for (int times = 0; times < error_times; times++) {
			try {
				jedis = pool.getResource();
				jedis.expire(key, timeout);
			} catch (Exception e) {
				exc = new Exception("连接Redis进行setDataTimeout操作出现异常", e);
				logger.warn("连接Redis进行setDataTimeout操作出现异常，第" + (times + 1)
						+ "次尝试...");
				pool.returnBrokenResource(jedis);
				continue;
			} finally {
				returnResource(pool, jedis);
			}
			return;
		}
		throw new Exception(exc);
	}

	/**
	 * get一个值，通过key
	 */
	@Override
	public String getValue(String key) throws Exception {
		logger.info("当前连接的库:" + getMaster());
		if (CheckParameterUtil.checkIsEmpty(key)) {
			throw new Exception("传入参数不正确.");
		}
		// 如果jedis连接失败，重新连接一次
		Exception exc = null;
		String result = null;
		Jedis jedis = null;
		for (int times = 0; times < error_times; times++) {
			try {
				jedis = pool.getResource();
				result = jedis.get(key);
			} catch (Exception e) {
				exc = new Exception("连接Redis进行getValue操作出现异常", e);
				logger.warn("连接Redis进行getValue操作出现异常，第" + (times + 1)
						+ "次尝试...");
				pool.returnBrokenResource(jedis);
				continue;
			} finally {
				returnResource(pool, jedis);
			}
			return result;
		}
		throw new Exception(exc);
	}

	/**
	 * get一个值，通过key
	 */
	@Override
	public boolean exists(String key) throws Exception {
		logger.info("当前连接的库:" + getMaster());
		if (CheckParameterUtil.checkIsEmpty(key)) {
			throw new Exception("传入参数不正确.");
		}
		// 如果jedis连接失败，重新连接一次
		Exception exc = null;
		boolean result = false;
		Jedis jedis = null;
		for (int times = 0; times < error_times; times++) {
			try {
				jedis = pool.getResource();
				result = jedis.exists(key);
			} catch (Exception e) {
				exc = new Exception("连接Redis进行exists操作出现异常", e);
				logger.warn("连接Redis进行exists操作出现异常，第" + (times + 1) + "次尝试...");
				pool.returnBrokenResource(jedis);
				continue;
			} finally {
				returnResource(pool, jedis);
			}
			return result;
		}
		throw new Exception(exc);
	}

	/**
	 * 运行一个lua脚本，keys和argv不能为null
	 */
	@Override
	public Object evalLua(String luaScript, List<String> keys, List<String> argv)
			throws Exception {
		logger.info("当前连接的库:" + getMaster());
		if (CheckParameterUtil.checkIsEmpty(luaScript) || keys == null) {
			throw new Exception("传入参数不正确.");
		}
		// 设定一个默认的为空的list，因为value不能传空值，但是为了程序中好理解，允许给空，在这里给个空对象
		if (argv == null) {
			argv = new ArrayList<String>();
		}
		// 如果jedis连接失败，重新连接一次
		Exception exc = null;
		Object result = null;
		Jedis jedis = null;
		for (int times = 0; times < error_times; times++) {
			try {
				jedis = pool.getResource();
				if (argv == null) {
					argv = new ArrayList<String>();
				}
				result = jedis.eval(luaScript, keys, argv);
			} catch (Exception e) {
				exc = new Exception("连接Redis进行evalLua操作出现异常", e);
				logger.warn("连接Redis进行evalLua操作出现异常，第" + (times + 1) + "次尝试...");
				pool.returnBrokenResource(jedis);
				continue;
			} finally {
				returnResource(pool, jedis);
			}
			return result;
		}
		throw new Exception(exc);
	}

	@Override
	public Object evalShardLua(String luaScript, String mark,
			List<String> keys, List<String> argv) throws Exception {
		logger.warn("非分片策略，建议不要调用分片机制，已跳转到evalLua方法执行.");
		return evalLua(luaScript, keys, argv);
	}

	@Override
	public Long zsetAdd(String collectionName, String key, double value)
			throws Exception {
		return null;
	}

	@Override
	public void zsetDelete(String collectionName, String key) throws Exception {

	}

	@Override
	public double zsetGetValue(String collectionName, String key)
			throws Exception {
		return 0;
	}

	@Override
	public Set<Tuple> zsetRangeByScore(String collectionName, long min, long max)
			throws Exception {
		return null;
	}

	@Override
	public Set<Tuple> zsetGetAll(String collectionName) throws Exception {
		return null;
	}

	@Override
	public void zsetPipAdd(List<SetCollectionBean> list) {

	}

	@Override
	public void zsetPipDelete(List<SetCollectionBean> list) {

	}

	/**
	 * 返回当前的主机
	 */
	public String getMaster() {
		HostAndPort hostAndPort = pool.getCurrentHostMaster();
		if (hostAndPort != null) {
			return hostAndPort.getHost() + ":" + hostAndPort.getPort();
		}
		return null;
	}

	@Override
	public String setex(String key, int seconds, String value) throws Exception {
		logger.info("当前连接的库:" + getMaster());
		if (CheckParameterUtil.checkIsEmpty(key)
				|| CheckParameterUtil.checkIsEmpty(value)) {
			throw new Exception("传入参数不正确. [key]=" + key + " [value]=" + value);
		}
		// 如果jedis连接失败，重新连接一次
		Exception exc = null;
		String result = null;
		Jedis jedis = null;
		for (int times = 0; times < error_times; times++) {
			try {
				jedis = pool.getResource();
				jedis.setex(key, seconds, value);
				result = jedis.get(key);
			} catch (Exception e) {
				exc = new Exception("连接Redis进行setex操作出现异常", e);
				logger.warn("连接Redis进行setex操作出现异常，第" + (times + 1) + "次尝试...");
				pool.returnBrokenResource(jedis);
				continue;
			} finally {
				returnResource(pool, jedis);
			}
			return result;
		}
		throw new Exception(exc);
	}

	@Override
	public long setHashKeyField(String key, String field, String value)
			throws Exception {
		logger.info("当前连接的库:" + getMaster());
		if (CheckParameterUtil.checkIsEmpty(key)
				|| CheckParameterUtil.checkIsEmpty(field)
				|| CheckParameterUtil.checkIsEmpty(value)) {
			throw new Exception("传入参数不正确. [key]=" + key + " [field]=" + field
					+ "[value]=" + value);
		}
		Jedis jedis = null;
		Exception exc = null;
		long result = 0;
		for (int times = 0; times < error_times; times++) {
			try {
				jedis = pool.getResource();
				result = jedis.hset(key, field, value);
			} catch (Exception e) {
				exc = new Exception("连接Redis进行setHashKeyField操作出现异常", e);
				logger.warn("连接Redis进行setHashKeyField操作出现异常，第" + (times + 1)
						+ "次尝试...");
				pool.returnBrokenResource(jedis);
				continue;
			} finally {
				returnResource(pool, jedis);
			}
			return result;
		}
		throw new Exception(exc);
	}

	@Override
	public String setMulipleHashKeyFields(String key,
			Map<String, String> fieldValues) throws Exception {
		logger.info("当前连接的库:" + getMaster());
		if (CheckParameterUtil.checkIsEmpty(key) || fieldValues == null
				|| fieldValues.size() == 0) {
			throw new Exception("传入参数不正确. [key]=" + key + " [fieldValues]="
					+ fieldValues);
		}
		Jedis jedis = null;
		Exception exc = null;
		String result = null;
		for (int times = 0; times < error_times; times++) {
			try {
				jedis = pool.getResource();
				result = jedis.hmset(key, fieldValues);
			} catch (Exception e) {
				exc = new Exception("连接Redis进行setMulipleHashKeyFields操作出现异常", e);
				logger.warn("连接Redis进行setMulipleHashKeyFields操作出现异常，第"
						+ (times + 1) + "次尝试...");
				pool.returnBrokenResource(jedis);
				continue;
			} finally {
				returnResource(pool, jedis);
			}
			return result;
		}
		throw new Exception(exc);
	}

	@Override
	public String getHashKeyField(String key, String field) throws Exception {
		logger.info("当前连接的库:" + getMaster());
		if (CheckParameterUtil.checkIsEmpty(key)
				|| CheckParameterUtil.checkIsEmpty(field)) {
			throw new Exception("传入参数不正确. [key]=" + key + " [field]=" + field);
		}
		// 如果jedis连接失败，重新连接一次
		Exception exc = null;
		String result = null;
		Jedis jedis = null;
		for (int times = 0; times < error_times; times++) {
			try {
				jedis = pool.getResource();
				result = jedis.hget(key, field);
			} catch (Exception e) {
				exc = new Exception("连接Redis进行getHashKeyField操作出现异常", e);
				logger.warn("连接Redis进行getHashKeyField操作出现异常，第" + (times + 1)
						+ "次尝试...");
				pool.returnBrokenResource(jedis);
				continue;
			} finally {
				returnResource(pool, jedis);
			}
			return result;
		}
		throw new Exception(exc);
	}

	@Override
	public Map<String, String> getHashKeyAllField(String key) throws Exception {
		logger.info("当前连接的库:" + getMaster());
		if (CheckParameterUtil.checkIsEmpty(key)) {
			throw new Exception("传入参数不正确. [key]=" + key);
		}
		// 如果jedis连接失败，重新连接一次
		Exception exc = null;
		Map<String, String> result = null;
		Jedis jedis = null;
		for (int times = 0; times < error_times; times++) {
			try {
				jedis = pool.getResource();
				result = jedis.hgetAll(key);
			} catch (Exception e) {
				exc = new Exception("连接Redis进行getHashKeyAllField操作出现异常", e);
				logger.warn("连接Redis进行getHashKeyAllField操作出现异常，第" + (times + 1)
						+ "次尝试...");
				pool.returnBrokenResource(jedis);
				continue;
			} finally {
				returnResource(pool, jedis);
			}
			return result;
		}
		throw new Exception(exc);
	}
}
