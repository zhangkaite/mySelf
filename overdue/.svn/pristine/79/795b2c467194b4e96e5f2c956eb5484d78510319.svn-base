package com.ttmv.dao.inter.redis;

import java.util.Date;
import java.util.List;

import com.ttmv.dao.bean.VipExpire;
import com.ttmv.dao.inter.IRedis;

public interface IRedisVipExpireInter extends IRedis{

	/**
	 * 添加VipExpire
	 * @param QueryVipExpire
	 * @return
	 * @throws Exception
	 */
	public void addRedisVipExpire(VipExpire vipExpire)throws Exception;
	
	/**
	 * 修改VipExpire
	 * @param QueryVipExpire
	 * @return
	 * @throws Exception
	 */
	public void updateRedisVipExpire(VipExpire vipExpire)throws Exception;
	
	/**
	 * 删除VipExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void deleteRedisVipExpire(String userId)throws Exception;
	
	/**
	 * 查询VipExpire
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public VipExpire queryRedisVipExpire(String userId)throws Exception;
	
	/**
	 * 时间范围查询VipExpire
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public List<VipExpire> queryRedisVipExpire(Date endTime)throws Exception;
}