package com.ttmv.monitoring.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

/**
 * 根据service业务做redis查询
 * 
 * @author zkt
 *
 */

@Service
@SuppressWarnings({ "resource", "rawtypes" })
public class QueryRedisInfoDao {

	/**
	 * 根据host查询该redis的所有keys
	 * 
	 * @param host
	 * @return
	 */

	public List<Object> queryKeys(String host) throws Exception {
		String[] hostInfo = host.split(":");
		Jedis jedis = new Jedis(hostInfo[0], Integer.valueOf(hostInfo[1]));
		Set<String> keySet = jedis.keys("*");
		List<Object> ls = new ArrayList<Object>();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			ls.add(key);
		}
		return ls;
	}

	/**
	 * 根据key获取对应的key 类型 一共四种类型"none", "string", "list", "set".
	 * 
	 * @param host
	 * @param key
	 * @return
	 */

	public String queryKeyType(String host, String key) throws Exception {
		if (null!=key&&!"".equals(key)) {
			String[] hostInfo = host.split(":");
			Jedis jedis = new Jedis(hostInfo[0], Integer.valueOf(hostInfo[1]));
			String keyType = jedis.type(key);
			return keyType;
		}
		
		return "";
	}

	/**
	 * 根据类型调用不同的查询方法
	 * 
	 * @param hostInfo
	 * @param keyName
	 * @param keyType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getKeyList(String host, String keyName, String keyType)
			throws Exception {
		List<Object> ls = new ArrayList<Object>();
		if (null!=keyName&&!"".equals(keyName)&&null!=keyType&&!"".equals(keyType)) {
			String[] hostInfo = host.split(":");
			Jedis jedis = new Jedis(hostInfo[0], Integer.valueOf(hostInfo[1]));
			if ("none".equals(keyType)) {
				return null;
			} else if ("String".equals(keyType)) {
				return null;
			} else if ("list".equals(keyType)) {
				List keysList = jedis.lrange(keyName, 0, -1);
				return keysList;
			} else if ("zset".equals(keyType)) {
				Set<Tuple> keySets = jedis.zrangeWithScores(keyName.getBytes(), 0, -1);
				for (Iterator iterator = keySets.iterator(); iterator.hasNext();) {
					Tuple key = (Tuple) iterator.next();
					ls.add(key);
				}
				return ls;
			} else if ("hash".equals(keyType)) {
				 Set<String> keySets=jedis.smembers(keyName);
				 for (String key : keySets) {
					 ls.add(key);
				}
				return ls;
			}else{
				return null;
			}
		}
		
		return null;
	
	}

	/**
	 * 获取redis内存占用大小
	 * 
	 * @param host
	 * @param keyName
	 * @return
	 * @throws Exception
	 */
	public String getKeySize(String host) throws Exception {
		String[] hostInfo = host.split(":");
		Jedis jedis = new Jedis(hostInfo[0], Integer.valueOf(hostInfo[1]));
		Long size = jedis.getDB();
		return String.valueOf(size);
	}
	
	/**
	 * 获取redis主机的详细信息
	 * @param host
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> info(String host)throws Exception{
		Map resultMap=new HashMap<String, String>();
		String[] hostInfo = host.split(":");
		Jedis jedis = new Jedis(hostInfo[0], Integer.valueOf(hostInfo[1]));
		String info=jedis.info();
		String[] infos=info.split("\n");
		for(String inf:infos){
			if (inf.contains(":")) {
				String[] infs=inf.split(":");
				//System.out.println(infs[0]+"\t"+infs[1]);
				resultMap.put(infs[0], infs[1]);
			}
		}
		
		return  resultMap;
		
	}
	
	
	

	public static void main(String[] args) throws Exception {
		QueryRedisInfoDao queDao = new QueryRedisInfoDao();
		System.out.print(queDao.info("192.168.13.157:50041"));
		//System.out.println(queDao.getKeySize("192.168.13.157:50041"));
		/*try {
			List<Object> ls = queDao.queryKeys("192.168.13.157:50041");
			for (Object object : ls) {
				String key = (String) object;
				String keyType = queDao.queryKeyType("192.168.13.157:50041",
						key);
				List<Object> rs = queDao.getKeyList("192.168.13.157:50041",
						key, keyType);
				for (Object os : rs) {
					System.out.println(os.toString());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
