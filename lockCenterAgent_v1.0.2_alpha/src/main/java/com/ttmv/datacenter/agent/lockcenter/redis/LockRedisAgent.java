package com.ttmv.datacenter.agent.lockcenter.redis;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.ttmv.datacenter.agent.lockcenter.redis.lua.Script;
import com.ttmv.datacenter.agent.redis.RedisAgent;
import com.ttmv.datacenter.utils.check.ModuleInitUtil;

/**
 * 
 * @author Scarlett.zhou
 * @date 2015年2月9日
 */
public class LockRedisAgent {

	private static Logger logger = LogManager.getLogger(LockRedisAgent.class);

	private RedisAgent jedis;
	private List<String> keys;
	private List<String> argv;

	public LockRedisAgent(RedisAgent redis) {
		logger.info("--- Loading RedisAgent! ---");
		if (redis == null) {
			ModuleInitUtil.failInitBySet("LockCenterAgent", new String[] {"redis", "redis" });
			return;
		}
		try {
			this.jedis = redis;
		} catch (Exception e) {
			ModuleInitUtil.failInitByEnv("RedisAgent","Connection redis failed.", e);
		}
	}

	/**
	 * 永久锁
	 * **/
	public boolean lockUniqueFE(String key) {
		keys = new ArrayList<String>();
		keys.add(key);
		try {
			Object o = jedis.evalLua(Script.LOCK_UNTIL_FE, keys, argv); // 成功返回1，失败返回0
			Long str = null;
			if (o instanceof Long) {
				str = (Long) o;
			} else {
				logger.error("增加永久锁Lua脚本返回类型不是Long型. [返回值]:" + o);
				throw new Exception("增加永久锁Lua脚本返回类型不是Long型. [返回值]:" + o);
			}
			if(str==0){
				logger.error("lua脚本[LOCK_UNTIL_FE]执行失败！！！@@ [KEY]--" + key +"已经存在，或者key写入失败！！！");
				return false;
			}else if (str==1){
				logger.info("[key]:"+ key +"加锁成功！！！");
				return true;
			}else{
				logger.warn("返回值异常，lua返回值为:"+str);
				return false;
			}
		} catch (Exception e) {
			logger.error("增加永久锁异常.", e);
			return false;
		}
	}

	/**
	 * 删除永久锁
	 * **/
	public boolean unlockUniqueFE(String key) {
		keys = new ArrayList<String>();
		keys.add(key);
		try {
			Object o = jedis.evalLua(Script.UN_LOCK_UNTIL_FE, keys, argv); // 成功返回1，失败返回0
			Long str = null;
			if (o instanceof Long) {
				str = (Long) o;
			} else {
				throw new Exception("删除永久锁Lua脚本返回类型不是Long型. [返回值]:" + o);
			}
			return str != null && str == 1 ? true : false;
		} catch (Exception e) {
			logger.error("删除永久锁异常.", e);
			return false;
		}
	}

	/**
	 * 临时锁
	 **/
	public boolean lockUnique(String key) {
		keys = new ArrayList<String>();
		keys.add(key);
		argv = new ArrayList<String>();
		argv.add(String.valueOf(50)); // 默认50秒钟
		try {
			Object o = jedis.evalLua(Script.LOCK_UNTIL, keys, argv); // 成功返回1，失败返回0
			Long str = null;
			if (o instanceof Long) {
				str = (Long) o;
			} else {
				throw new Exception("增加临时锁Lua脚本返回类型不是Long型. [返回值]:" + o);
			}
			return str != null && str == 1 ? true : false;
		} catch (Exception e) {
			logger.error("增加临时锁异常.", e);
			return false;
		}
	}

	/**
	 * 临时锁解锁
	 **/
	public void unlockUnique(String key) {
		keys = new ArrayList<String>();
		keys.add(key);
		try {
			jedis.evalLua(Script.UN_LOCK_UNTIL, keys, argv); // 成功返回1，失败返回
		} catch (Exception e) {
			logger.error("临时锁解锁异常.",e);
		}
	}

	/**
	 * 永久锁--次数
	 * */
	public boolean lockUntilFE(String key, int untilNum) {
		keys = new ArrayList<String>();
		keys.add(key);
		argv = new ArrayList<String>();
		argv.add(String.valueOf(untilNum));
		try {
			Object o = jedis.evalLua(Script.LOCK_UNTIL_FE_COUNT, keys, argv); // 成功返回1，失败返回0
			Long str = null;
			if (o instanceof Long) {
				str = (Long) o;
			} else {
				throw new Exception("增加永久锁--次数Lua脚本返回类型不是Long型. [返回值]:" + o);
			}
			return str != null && str == 1 ? true : false;
		} catch (Exception e) {
			logger.error("增加永久锁--次数异常.",e);
			return false;
		}
	}

	/**
	 * 解锁永久锁--次数
	 **/
	public boolean releaseOneFE(String key) {
		keys = new ArrayList<String>();
		keys.add(key);
		try {
			Object o = jedis.evalLua(Script.RELEASE_ONE_FE, keys, argv); // 成功返回1，失败返回0
			Long str = null;
			if (o instanceof Long) {
				str = (Long) o;
			} else {
				throw new Exception("解锁永久锁--次数Lua脚本返回类型不是Long型. [返回值]:" + o);
			}
			return str != null && str == 1 ? true : false;
		} catch (Exception e) {
			logger.error("解锁永久锁--次数异常.",e);
			return false;
		}
	}
}
