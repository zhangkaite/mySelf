package com.ttmv.datacenter.agent.redis;


import com.ttmv.datacenter.agent.redis.beans.SetCollectionBean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisAgent{
    /**
     * set值到库，通过key - value的方式 (对已经有的值不会做替换)
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    public long setIfNotExist(String key, String value) throws Exception;
    
    /**
     * set值到库，通过key - value的方式 (如果值存在，用新值覆盖原来的值)
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    public String setOverride(String key, String value) throws Exception; 
    
	/**
	 * set值到库，设置key的失效时间
	 */
    public String setex(String key,int seconds,String value) throws Exception;

    /**
     *  delete一个值，通过key的方式
     * @param key
     * @throws Exception
     */
    public void deleteData(String key) throws Exception;

    /**
     *  set值到库并设置这个值的生存时间，通过key - time的方式
     * @param key
     * @param timeout
     * @throws Exception
     */
    public void setDataTimeout(String key, int timeout) throws Exception;
    
    /**
     * get一个值，通过key
     * @param key
     * @return
     * @throws Exception
     */
    public String getValue(String key) throws Exception;
    
    /**
     * 检查一个值是否在redis库中存在，通过key
     * @param key
     * @return
     * @throws Exception
     */
    public boolean exists(String key) throws Exception;

    /**
     * set值到库，数据结构使用的是：散列结构
     * 此方法一次设置一个field：field - value
     * @param key
     * @param field
     * @param value
     * @return long
     * @throws Exception
     */
    public long setHashKeyField(String key , String field , String value)throws Exception;
    
    /**
     * set值到库，数据结构使用的是：散列结构
     * 此方法一次可以设置多个field：field - value值，通过使用Map结构
     * @param key
     * @param fieldValues
     * @return long
     * @throws Exception
     */
    public String setMulipleHashKeyFields(String key , Map<String ,String> fieldValues)throws Exception;
    
    /**
     * get一个Hash的field值，通过key和field
     * @param key
     * @param field
     * @return String
     * @throws Exception
     */
    public String getHashKeyField(String key , String field) throws Exception;
    
    /**
     * get一个Hash的key所有的field值，通过key
     * 返回Map，Map的key-value对应Hash的field-value
     * @param key
     * @return Map
     * @throws Exception
     */
    public Map<String,String> getHashKeyAllField(String key)throws Exception;
    
    /**
     * 运行一个lua脚本，keys不能为null
     * @param luaScript
     * @param keys
     * @param argv
     * @return
     * @throws Exception
     */
    public Object evalLua(String luaScript, List<String> keys, List<String> argv) throws Exception;

  
    /**
     * 运行一个lua脚本,适合于分片的策略，mark为分片的唯一关键字，建议用lua脚本中的唯一key，keys不能为null
     * @param luaScript
     * @param mark
     * @param keys
     * @param argv
     * @return
     * @throws Exception
     */
    public Object evalShardLua(String luaScript,String mark,List<String> keys, List<String> argv) throws Exception;


    /**
     * 增加一条数据，在有序set集合中
     * @param collectionName  set集合的名称
     * @param key   key值
     * @param value  value值（须为double,long,int类型）
     * @return
     * @throws Exception
     */
    public Long zsetAdd(String collectionName,String key,double value) throws Exception;

    /**
     * 删除一条数据，在有序set集合中
     * @param key key值
     * @param collectionName set集合的名称
     * @throws Exception
     */
    public void zsetDelete(String collectionName,String key) throws Exception;

    /**
     * 查询在有序set集合中单个一条数据的分数值
     * @param key 数据的key
     * @param collectionName set集合名
     * @return
     * @throws Exception
     */
    public double zsetGetValue(String collectionName, String key) throws Exception;

    /**
     * 在有序set集合中查询value为 min - max之间的数据
     * @param collectionName set集合名
     * @param min 最小分数值
     * @param max 最大分数值
     * @return
     * @throws Exception
     */
    public Set<Tuple> zsetRangeByScore(String collectionName,long min,long max) throws Exception;

    /**
     * 获取有序set集合中全部的元素
     * @param collectionName set集合名
     * @return
     * @throws Exception
     */
    public Set<Tuple> zsetGetAll(String collectionName) throws Exception;

    /**
     * 批量提交增加
     * @param list
     * @throws Exception
     */
    public void zsetPipAdd(List<SetCollectionBean> list) throws Exception;

    /**
     * 批量删除
     * @param list
     * @throws Exception
     */
    public void zsetPipDelete(List<SetCollectionBean> list) throws Exception;

}
