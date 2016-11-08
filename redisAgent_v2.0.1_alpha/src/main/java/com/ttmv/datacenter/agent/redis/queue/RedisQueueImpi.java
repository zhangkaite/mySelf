package com.ttmv.datacenter.agent.redis.queue;

import com.ttmv.datacenter.agent.redis.RedisPoolConfig;
import com.ttmv.datacenter.agent.redis.RedisQueue;
import com.ttmv.datacenter.agent.redis.tool.CheckParameterUtil;
import com.ttmv.datacenter.agent.redis.tool.ModuleInitUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by zbs on 15/11/18.
 */
public class RedisQueueImpi implements RedisQueue {

    private Logger logger = LogManager.getLogger(RedisQueueImpi.class);
    private JedisPool pool = null;
    private String host;
    private int port;

    public RedisQueueImpi(String host, int port, RedisPoolConfig poolConfig, int timeout) {
        if (CheckParameterUtil.checkIsEmpty(host) || port == 0 || poolConfig == null || timeout==0) {
            ModuleInitUtil.failInitBySet("RedisQueueImpi", new String[]{"host", "port", "poolConfig", "timeout"});
        }
        this.host = host;
        this.port = port;
        JedisPoolConfig config = poolConfig.getRedisPoolConfig();
        pool = new JedisPool(config, host, port, timeout);
    }

    /**
     * 返还数据库连接池链接
     * @param jedis
     */
    protected void returnResource(Jedis jedis) {
        if (jedis != null) {
            pool.returnResource(jedis);
        }
    }

    @Override
    public void setValue(String key,String value) throws Exception {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.rpush(key, value);
        }catch (Exception e){
            throw new Exception(e);
        }finally {
            returnResource(jedis);
        }
    }

    @Override
    public long size(String key) throws Exception {
       Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.llen(key);
        }catch (Exception e){
            throw new Exception(e);
        }finally {
            returnResource(jedis);
        }
    }

    @Override
    public String getValue(String key) throws Exception {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lpop(key);
        }catch (Exception e){
            throw new Exception(e);
        }finally {
            returnResource(jedis);
        }
    }

    @Override
    public List<String> searchtValueByRange(String key,int start,int stop) throws  Exception {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lrange(key,start,stop);
        }catch (Exception e){
            throw new Exception(e);
        }finally {
            returnResource(jedis);
        }
    }

    @Override
    public void delValue(String key,String value) throws  Exception {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.lrem(key,1,value);
        }catch (Exception e){
            throw new Exception(e);
        }finally {
            returnResource(jedis);
        }
    }

    @Override
    public List<String> getValueBatch(String key,int number) throws  Exception {
        List<String> keys = new ArrayList<String>();
        keys.add(key);
        List<String> argv = new ArrayList<String>();
        argv.add(String.valueOf(number));
        String luaScript =  " local list= {} " +
                " local num = redis.call('llen',KEYS[1])" +
                " if num == 0 then return nil end"+
                " for i=1,ARGV[1],1 do " +
                "    local value = redis.call('lpop',KEYS[1]) " +
                "    if value then" +
                "        list[i] = value " +
                "    else " +
                "        break" +
                "    end" +
                " end " +
                " if #list<1 then " +
                "    return nil " +
                " end " +
                " return list";
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Object result = jedis.eval(luaScript, keys, argv);
            return (List<String>)result;
        }catch (Exception e){
            throw new Exception(e);
        }finally {
            returnResource(jedis);
        }

    }

	@Override
	public void setSetValue(String key, String value) throws Exception {
		Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.sadd(key,value);
        }catch (Exception e){
            throw new Exception(e);
        }finally {
            returnResource(jedis);
        }
	}

	@Override
	public Set<String> getSetValues(String key) throws Exception {
		Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Set<String> setStrings = jedis.smembers(key);
            return setStrings;
        }catch (Exception e){
            throw new Exception(e);
        }finally {
            returnResource(jedis);
        }
	}

	@Override
	public void deleteSetValue(String key, String value) throws Exception {
		Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.srem(key, value);
        }catch (Exception e){
            throw new Exception(e);
        }finally {
            returnResource(jedis);
        }
	}
}
