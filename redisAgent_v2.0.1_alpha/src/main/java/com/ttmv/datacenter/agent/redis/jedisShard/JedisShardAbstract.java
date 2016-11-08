package com.ttmv.datacenter.agent.redis.jedisShard;

import com.ttmv.datacenter.agent.redis.RedisAgent;
import com.ttmv.datacenter.agent.redis.beans.SetCollectionBean;
import com.ttmv.datacenter.agent.redis.tool.CheckParameterUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zbs on 15/11/12.
 */
public abstract class JedisShardAbstract implements RedisAgent {

    private Logger logger = LogManager.getLogger(JedisShardAbstract.class);

    protected String host;

    protected int port;

    protected final int error_times = 1;

    protected final int  retry_times = 10;

    protected abstract void returnResource(ShardedJedis shardedJedis);

    protected abstract ShardedJedis getShardeJedis() throws Exception;

    private ShardedJedis getDataSource() throws Exception {
        ShardedJedis shardedJedis = null;
        Exception exc = null;
        for (int times = 0; times < error_times; times++) {
            try {
                shardedJedis = getShardeJedis();
                break;
            } catch (Exception e) {
                exc = new Exception("连接Redis现异常[" + host + ":" + port + "]", e);
                logger.warn("连接Redis出现异常,第" + (times + 1) + "次尝试...隔" + retry_times + "毫秒进行再次尝试", e);
                try {
                    Thread.sleep(retry_times);
                } catch (InterruptedException e1) {
                    throw new Exception(e1);
                }
                returnResource(shardedJedis);
            }
        }
        if(shardedJedis == null){
            throw new Exception(exc);
        }
        return shardedJedis;
    }

    private void checkParameter(Object ... obj) throws Exception {
        if(CheckParameterUtil.checkIsEmptyBatch(obj)){
            throw new Exception("传入参数不正确.");
        }
    }

    @Override
    public long setIfNotExist(String key, String value) throws Exception {
        checkParameter(key,value);
        ShardedJedis shardedJedis = getDataSource();
        long result = shardedJedis.setnx(key,value);
        returnResource(shardedJedis);
        return result;
    }

    /**
     * set值到库，通过key - value的方式 (如果值存在，用新值覆盖原来的值)
     */
    @Override
    public synchronized String setOverride(String key, String value) throws Exception {
        checkParameter(key,value);
        ShardedJedis shardedJedis = getDataSource();
        shardedJedis.getSet(key,value);
        String result = shardedJedis.get(key);
        returnResource(shardedJedis);
        return result;
    }

    /**
     * delete一个值，通过key的方式
     */
    @Override
    public void deleteData(String key) throws Exception {
        checkParameter(key);
        ShardedJedis shardedJedis = getDataSource();
        shardedJedis.del(key);
        returnResource(shardedJedis);
    }

    /**
     *  set值到库并设置这个值的生存时间，通过key - time的方式
     */
    @Override
    public void setDataTimeout(String key, int timeout) throws Exception {
        checkParameter(key,timeout);
        ShardedJedis shardedJedis = getDataSource();
        shardedJedis.expire(key, timeout);
        returnResource(shardedJedis);
    }
    /**
     *  get一个值，通过key
     */
    @Override
    public String getValue(String key) throws Exception {
        checkParameter(key);
        ShardedJedis shardedJedis = getDataSource();
        String result = shardedJedis.get(key);
        returnResource(shardedJedis);
        return result;
    }

    /**
     *  get一个值，通过key
     */
    @Override
    public boolean exists(String key) throws Exception {
        checkParameter(key);
        ShardedJedis shardedJedis = getDataSource();
        boolean result = shardedJedis.exists(key);
        returnResource(shardedJedis);
        return result;
    }

    /**
     * 运行一个lua脚本，keys和argv不能为null
     */
    @Override
    public Object evalShardLua(String luaScript,String mark,List<String> keys, List<String> argv) throws Exception {
        checkParameter(luaScript,mark,keys);
        ShardedJedis shardedJedis = getDataSource();
        Jedis jedis = shardedJedis.getShard(mark);
        if(argv == null){
        	argv = new ArrayList<String>();
        }
        Object result = jedis.eval(luaScript, keys, argv);
        returnResource(shardedJedis);
        return result;
    }

    @Override
    public Object evalLua(String luaScript, List<String> keys, List<String> argv) throws Exception {
        throw new Exception("分片策略，请不要调用非分片机制evalLua，请调用evalShardLua方法.");
    }

    @Override
    public String setex(String key, int seconds, String value) throws Exception {
        checkParameter(key,value);
        ShardedJedis shardedJedis = getDataSource();
        shardedJedis.setex(key, seconds, value);
        String result = shardedJedis.get(key);
        returnResource(shardedJedis);
        return result;
    }

    @Override
    public long setHashKeyField(String key, String field, String value)
            throws Exception {
        checkParameter(key,field,value);
        ShardedJedis shardedJedis = getDataSource();
        long result = shardedJedis.hset(key, field, value);
        returnResource(shardedJedis);
        return result;
    }

    @Override
    public String setMulipleHashKeyFields(String key,Map<String, String> fieldValues) throws Exception {
        checkParameter(key,fieldValues);
        ShardedJedis shardedJedis = getDataSource();
        String result = shardedJedis.hmset(key, fieldValues);
        returnResource(shardedJedis);
        return result;
    }

    @Override
    public String getHashKeyField(String key, String field) throws Exception {
        checkParameter(key,field);
        ShardedJedis shardedJedis = getDataSource();
        String result = shardedJedis.hget(key, field);
        returnResource(shardedJedis);
        return result;
    }

    @Override
    public Map<String, String> getHashKeyAllField(String key) throws Exception {
        checkParameter(key);
        ShardedJedis shardedJedis = getDataSource();
        Map<String,String> result = shardedJedis.hgetAll(key);
        returnResource(shardedJedis);
        return result;
    }


    @Override
    public Long zsetAdd(String collectionName,String key,double value) throws Exception {
        checkParameter(collectionName,key);
        ShardedJedis shardedJedis = getDataSource();
        Long result = shardedJedis.zadd(collectionName,value,key);
        returnResource(shardedJedis);
        return result;
    }


    @Override
    public void zsetDelete(String collectionName,String key) throws Exception {
        checkParameter(collectionName,key);
        ShardedJedis shardedJedis = getDataSource();
        shardedJedis.zrem(collectionName,key);
        returnResource(shardedJedis);
    }

    @Override
    public double zsetGetValue(String collectionName, String key) throws Exception {
        checkParameter(collectionName,key);
        ShardedJedis shardedJedis = getDataSource();
        double result = shardedJedis.zscore(collectionName,key);
        returnResource(shardedJedis);
        return result;
    }

    @Override
    public Set<Tuple> zsetRangeByScore(String collectionName,long min,long max) throws Exception {
        checkParameter(collectionName);
        ShardedJedis shardedJedis = getDataSource();
        Set<Tuple>  result = shardedJedis.zrangeByScoreWithScores(collectionName, min, max);
        returnResource(shardedJedis);
        return result;
    }

    @Override
    public Set<Tuple> zsetGetAll(String collectionName) throws Exception {
        checkParameter(collectionName);
        ShardedJedis shardedJedis = getDataSource();
        Set<Tuple> result = shardedJedis.zrangeWithScores(collectionName, 0, -1);
        returnResource(shardedJedis);
        return result;
    }

    @Override
    public void zsetPipAdd(List<SetCollectionBean> list) throws Exception {
       throw new Exception("分片不能用批量提交");
    }

    @Override
    public void zsetPipDelete(List<SetCollectionBean> list) throws Exception {
        throw new Exception("分片不能用批量提交");
    }
}
