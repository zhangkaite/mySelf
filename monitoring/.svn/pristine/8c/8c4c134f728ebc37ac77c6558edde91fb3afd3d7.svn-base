package com.ttmv.monitoring.redis.inf;

import java.util.List;
import java.util.Map;



/**
 * 定义查询redis详情方法的接口
 * @author zkt
 *
 */
public interface QueryRedisInfoServiceInf {
	
	/**
	 * 根据redis host信息查询redis存在的所有keys
	 * @param hostInfo
	 * @return
	 */
	public List<Object> queryKeys(String hostInfo)throws Exception ;
	
	
	/**
	 * 根据key信息获取key的类型
	 * @param key
	 * @return
	 */
	public String getKeyType(String hostInfo,String key) throws Exception ;
	
	/**
	 * 根据key名称和key类型查询信息列表
	 * @param keyName
	 * @param keyType
	 * @return
	 */
	public List<Object> getKeyList(String hostInfo,String keyName,String keyType) throws Exception ;
	
	
	
	/**
	 * 获取redis中 key的大小
	 * @param hostInfo
	 * @param keyName
	 * @return
	 */
	
	public String keySize(String hostInfo,String keyName) throws Exception ;
	
	
	/**
	 * 获取redis 的基本信息
	 */
	
	public Map<String, String> info(String hostInfo) throws Exception ;
	
	

}
