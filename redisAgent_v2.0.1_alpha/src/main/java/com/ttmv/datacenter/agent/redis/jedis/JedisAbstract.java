package com.ttmv.datacenter.agent.redis.jedis;

import com.ttmv.datacenter.agent.redis.RedisAgent;
import com.ttmv.datacenter.agent.redis.beans.SetCollectionBean;
import com.ttmv.datacenter.agent.redis.tool.CheckParameterUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zbs on 15/11/11.
 */
public abstract class JedisAbstract implements RedisAgent {

    private Logger logger = LogManager.getLogger(JedisAbstract.class);

    protected String host;

    protected int port;

    protected final int error_times = 1;

    protected final int  retry_times = 10;

    protected abstract void returnResource(Jedis redis);

    protected abstract Jedis getJedis() throws Exception;

    private Jedis getDataSource() throws Exception {
        Jedis jedis = null;
        Exception exc = null;
        for (int times = 0; times < error_times; times++) {
            try {
                jedis = getJedis();
                break;
            } catch (Exception e) {
                exc = new Exception("连接Redis现异常[" + host + ":" + port + "]", e);
                logger.warn("连接Redis出现异常,第" + (times + 1) + "次尝试...隔" + retry_times + "毫秒进行再次尝试", e);
                try {
                    Thread.sleep(retry_times);
                } catch (InterruptedException e1) {
                    throw new Exception(e1);
                }
                returnResource(jedis);
            }
        }
        if(jedis == null){
            throw new Exception(exc);
        }
        return jedis;
    }

    private void checkParameter(Object ... obj) throws Exception {
        if(CheckParameterUtil.checkIsEmptyBatch(obj)){
            throw new Exception("传入参数不正确.");
        }
    }


    @Override
    public long setIfNotExist(String key, String value) throws Exception {
        checkParameter(key,value);
        Jedis jedis = getDataSource();
        long result = jedis.setnx(key, value);
        returnResource(jedis);
        return result;
    }

    @Override
    public String setOverride(String key, String value) throws Exception {
        checkParameter(key,value);
        Jedis jedis = getDataSource();
        jedis.getSet(key, value);
        String result = jedis.get(key);
        returnResource(jedis);
        return result;
    }

    @Override
    public String setex(String key, int seconds, String value) throws Exception {
        checkParameter(key,value);
        Jedis jedis = getDataSource();
        jedis.setex(key, seconds,value);
        String result = jedis.get(key);
        returnResource(jedis);
        return result;
    }

    @Override
    public void deleteData(String key) throws Exception {
        checkParameter(key);
        Jedis jedis = getDataSource();
        jedis.del(key);
        returnResource(jedis);
    }

    @Override
    public void setDataTimeout(String key, int timeout) throws Exception {
        checkParameter(key,timeout);
        Jedis jedis = getDataSource();
        jedis.expire(key, timeout);
        returnResource(jedis);
    }

    @Override
    public String getValue(String key) throws Exception {
        checkParameter(key);
        Jedis jedis = getDataSource();
        String result = jedis.get(key);
        returnResource(jedis);
        return result;
    }

    @Override
    public boolean exists(String key) throws Exception {
        checkParameter(key);
        Jedis jedis = getDataSource();
        boolean result = jedis.exists(key);
        returnResource(jedis);
        return result;
    }

    @Override
    public long setHashKeyField(String key, String field, String value) throws Exception {
        checkParameter(key,field,value);
        Jedis jedis = getDataSource();
        long result = jedis.hset(key, field, value);
        returnResource(jedis);
        return result;
    }

    @Override
    public String setMulipleHashKeyFields(String key, Map<String, String> fieldValues) throws Exception {
        checkParameter(key,fieldValues);
        Jedis jedis = getDataSource();
        String result = jedis.hmset(key, fieldValues);
        returnResource(jedis);
        return result;
    }

    @Override
    public String getHashKeyField(String key, String field) throws Exception {
        checkParameter(key,field);
        Jedis jedis = getDataSource();
        String result = jedis.hget(key, field);
        returnResource(jedis);
        return result;
    }

    @Override
    public Map<String, String> getHashKeyAllField(String key) throws Exception {
        checkParameter(key);
        Jedis jedis = getDataSource();
        Map<String, String> result = jedis.hgetAll(key);
        returnResource(jedis);
        return result;
    }

    @Override
    public Object evalLua(String luaScript, List<String> keys, List<String> argv) throws Exception {
        checkParameter(luaScript,keys);
        Jedis jedis = getDataSource();
        if(argv == null){
        	argv = new ArrayList<String>();
        }
        Object result = jedis.eval(luaScript, keys, argv);
        returnResource(jedis);
        return result;
    }

    @Override
    public Long zsetAdd(String collectionName,String key,double value) throws Exception {
        checkParameter(collectionName,key);
        Jedis jedis = getDataSource();
        Long result = jedis.zadd(collectionName,value,key);
        returnResource(jedis);
        return result;
    }


    @Override
    public void zsetDelete(String collectionName,String key) throws Exception {
        checkParameter(collectionName,key);
        Jedis jedis = getDataSource();
        jedis.zrem(collectionName,key);
        returnResource(jedis);
    }

    @Override
    public double zsetGetValue(String collectionName, String key) throws Exception {
        checkParameter(collectionName,key);
        Jedis jedis = getDataSource();
        double result = jedis.zscore(collectionName,key);
        returnResource(jedis);
        return result;
    }

    @Override
    public Set<Tuple> zsetRangeByScore(String collectionName,long min,long max) throws Exception {
        checkParameter(collectionName);
        Jedis jedis = getDataSource();
        Set<Tuple>  result = jedis.zrangeByScoreWithScores(collectionName, min, max);
        returnResource(jedis);
        return result;
    }

    @Override
    public Set<Tuple> zsetGetAll(String collectionName) throws Exception {
        checkParameter(collectionName);
        Jedis jedis = getDataSource();
        Set<Tuple> result = jedis.zrangeWithScores(collectionName, 0, -1);
        returnResource(jedis);
        return result;
    }

    @Override
    public Object evalShardLua(String luaScript, String mark, List<String> keys, List<String> argv) throws Exception {
        logger.warn("非分片策略，建议不要调用分片机制，已跳转到evalLua方法执行.");
        return evalLua( luaScript,keys,argv);
    }

    /**
     * 批量提交增加
     * @param list
     * @throws Exception
     */
    public void zsetPipAdd(List<SetCollectionBean> list) throws Exception {
        checkParameter(list);
        Jedis jedis = getDataSource();
        Pipeline pipeline = jedis.pipelined();
        for(SetCollectionBean set : list) {
            pipeline.zadd(set.getCollectionName(),set.getValue(),set.getKey());
        }
        pipeline.sync();
        returnResource(jedis);
    }

    /**
     * 批量删除
     * @param list
     * @throws Exception
     */
    public void zsetPipDelete(List<SetCollectionBean> list) throws Exception {
        checkParameter(list);
        Jedis jedis = getDataSource();
        Pipeline pipeline = jedis.pipelined();
        for(SetCollectionBean set : list) {
            pipeline.zrem(set.getCollectionName(),set.getKey());
        }
        pipeline.sync();
        returnResource(jedis);
    }
}
