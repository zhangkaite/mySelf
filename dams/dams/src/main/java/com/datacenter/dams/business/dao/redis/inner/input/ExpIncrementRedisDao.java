package com.datacenter.dams.business.dao.redis.inner.input;

import java.util.List;

import com.datacenter.dams.business.dao.util.RedisQueueUtil;
import com.ttmv.datacenter.agent.redis.queue.RedisQueueImpi;

/**
 * 获取用户或是明星登陆的记录数据
 * 读取所有的数据用于给用户或是明星增长经验
 * @author wulinli
 */
public class ExpIncrementRedisDao {

	private static RedisQueueImpi redisQueueImpi;
	
	/**
	 * 获取登陆用户的记录
	 * @return
	 */
	public static List<String> getLoginUsers() throws Exception{
		List<String> datas = redisQueueImpi.searchtValueByRange(RedisQueueUtil.DAMS_USER_ONLINE_QUEUE, 0,-1);
		if(datas != null && datas.size() > 0){
			return datas;
		}
		return null;
	}
	
	/**
	 * 获取登陆明星的记录
	 * @return
	 */
	public static List<String> getLoginStars()throws Exception{
		List<String> datas = redisQueueImpi.searchtValueByRange(RedisQueueUtil.DAMS_STAR_ONLINE_QUEUE, 0, -1);
		if(datas != null && datas.size() > 0){
			return datas;
		}
		return null;
	}
	
	public static RedisQueueImpi getRedisQueueImpi() {
		return redisQueueImpi;
	}

	public static void setRedisQueueImpi(RedisQueueImpi redisQueueImpi) {
		ExpIncrementRedisDao.redisQueueImpi = redisQueueImpi;
	}
}
