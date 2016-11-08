package com.ttmv.datacenter.agent.redis;

import java.util.List;
import java.util.Set;

/**
 * Created by zbs on 15/11/18.
 */
public interface RedisQueue {

    /**
     *  把值放入队列
     * @param key
     * @param value
     * @throws Exception
     */
    public void setValue(String key,String value) throws Exception;

    /**
     * 得到队列长度
     * @param key
     * @return
     * @throws Exception
     */
    public long size(String key) throws  Exception;

    /**
     * 从队列中获取一个值
     * @param key
     * @return
     * @throws Exception
     */
    public String getValue(String key) throws Exception;

    /**
     * 从队列中查询指定的一段值
     * @param key
     * @param start
     * @param stop
     * @return
     * @throws Exception
     */
    public List<String> searchtValueByRange(String key,int start,int stop) throws  Exception ;

    /**
     * 从队列中获取一段值
     * @param key
     * @param number
     * @return
     * @throws Exception
     */
    public List<String> getValueBatch(String key,int number ) throws  Exception ;

    /**
     * 从队列中删除一个值
     * @param key
     * @param value
     * @throws Exception
     */
    public void delValue(String key,String value) throws  Exception ;

    /**
     * 向一个Set集中添加一个元素
     * @param key
     * @param value
     * @throws Exception
     */
    public void setSetValue(String key,String value) throws Exception;
    
    /**
     * 获取Set集合中所有的元素
     * @param key
     * @return
     * @throws Exception
     */
    public Set<String> getSetValues(String key)throws Exception;
    
    /**
     * 删除Set队列的元素
     * @param key
     * @param value
     * @throws Exception
     */
    public void deleteSetValue(String key,String value)throws Exception;

}
