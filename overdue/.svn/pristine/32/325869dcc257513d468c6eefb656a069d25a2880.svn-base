package com.ttmv.dao.inter.redis;

import java.util.Date;
import java.util.List;

import com.ttmv.dao.bean.GoodNumberExpire;
import com.ttmv.dao.inter.IRedis;

public interface IRedisGoodNumberExpireInter extends IRedis{

	/**
	 * 添加GoodNumberExpire
	 * @param goodNumberExpire
	 * @return
	 * @throws Exception
	 */
	public void addRedisGoodNumberExpire(GoodNumberExpire goodNumberExpire)throws Exception;
	
	/**
	 * 修改GoodNumberExpire
	 * @param goodNumberExpire
	 * @return
	 * @throws Exception
	 */
	public void updateRedisGoodNumberExpire(GoodNumberExpire goodNumberExpire)throws Exception;
	
	/**
	 * 删除GoodNumberExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void deleteRedisGoodNumberExpire(String userId,String goodNumberId)throws Exception;
	
	/**
	 * 查询GoodNumberExpire
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public GoodNumberExpire queryRedisGoodNumberExpire(String userId,String goodNumberId)throws Exception;
	
	/**
	 * 时间范围查询GoodNumberExpire
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public List<GoodNumberExpire> queryRedisGoodNumberExpire(Date endTime)throws Exception;
}