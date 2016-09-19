package com.ttmv.monitoring.redis.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttmv.monitoring.redis.QueryRedisInfoDao;
import com.ttmv.monitoring.redis.inf.QueryRedisInfoServiceInf;

@Service("queryRedisInfoServiceImpl")
public class QueryRedisInfoServiceImpl implements QueryRedisInfoServiceInf {
	
	@Autowired
	private QueryRedisInfoDao queryRedisInfoDao;
	

	public List<Object> queryKeys(String hostInfo) throws Exception  {
		return queryRedisInfoDao.queryKeys(hostInfo);
	}

	public String getKeyType(String hostInfo,String key)throws Exception  {
		return queryRedisInfoDao.queryKeyType(hostInfo, key);
	}

	public List<Object> getKeyList(String hostInfo,String keyName, String keyType) throws Exception {
		return queryRedisInfoDao.getKeyList(hostInfo, keyName, keyType);
	}

	public String keySize(String hostInfo, String keyName) throws Exception {
		return queryRedisInfoDao.getKeySize(hostInfo);
	}

	public Map<String, String> info(String hostInfo) throws Exception {
		return queryRedisInfoDao.info(hostInfo);
	}

}
