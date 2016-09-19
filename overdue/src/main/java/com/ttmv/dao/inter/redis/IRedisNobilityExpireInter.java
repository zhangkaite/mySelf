package com.ttmv.dao.inter.redis;

import java.util.Date;
import java.util.List;

import com.ttmv.dao.bean.NobilityExpire;
import com.ttmv.dao.inter.IRedis;

public interface IRedisNobilityExpireInter extends IRedis{

	/**
	 * 添加NobilityExpire
	 * @param nobilityExpire
	 * @return
	 * @throws Exception
	 */
	public void addRedisNobilityExpire(NobilityExpire nobilityExpire)throws Exception;
	
	/**
	 * 修改NobilityExpire
	 * @param nobilityExpire
	 * @return
	 * @throws Exception
	 */
	public void updateRedisNobilityExpire(NobilityExpire nobilityExpire)throws Exception;
	
	/**
	 * 删除NobilityExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void deleteRedisNobilityExpire(String userId)throws Exception;
	
	/**
	 * 查询NobilityExpire
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public NobilityExpire queryRedisNobilityExpire(String userId)throws Exception;
	
	/**
	 * 时间范围查询NobilityExpire
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public List<NobilityExpire> queryRedisNobilityExpire(Date endTime)throws Exception;
}