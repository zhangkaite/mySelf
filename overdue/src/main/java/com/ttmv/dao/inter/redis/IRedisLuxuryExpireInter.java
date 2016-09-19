package com.ttmv.dao.inter.redis;

import java.util.Date;
import java.util.List;

import com.ttmv.dao.bean.LuxuryExpire;
import com.ttmv.dao.inter.IRedis;

public interface IRedisLuxuryExpireInter extends IRedis{

	/**
	 * 添加LuxuryExpire
	 * @param luxuryExpire
	 * @return
	 * @throws Exception
	 */
	public void addRedisLuxuryExpire(LuxuryExpire luxuryExpire)throws Exception;
	
	/**
	 * 修改LuxuryExpire
	 * @param luxuryExpire
	 * @return
	 * @throws Exception
	 */
	public void updateRedisLuxuryExpire(LuxuryExpire luxuryExpire)throws Exception;
	
	/**
	 * 删除LuxuryExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void deleteRedisLuxuryExpire(String userId,String carId)throws Exception;
	
	/**
	 * 查询LuxuryExpire
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public LuxuryExpire queryRedisLuxuryExpire(String userId,String carId)throws Exception;
	
	/**
	 * 时间范围查询LuxuryExpire
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public List<LuxuryExpire> queryRedisLuxuryExpire(Date endTime)throws Exception;
}