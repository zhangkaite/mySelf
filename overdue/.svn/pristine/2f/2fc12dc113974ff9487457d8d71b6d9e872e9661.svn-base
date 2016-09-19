package com.ttmv.dao.inter.redis;

import java.util.Date;
import java.util.List;

import com.ttmv.dao.bean.LiveAnchorBanExpire;
import com.ttmv.dao.inter.IRedis;

public interface IRedisLiveAnchorBanExpireInter extends IRedis{

	/**
	 * 添加LiveAnchorBanExpire
	 * @param LiveAnchorBanExpire
	 * @return
	 * @throws Exception
	 */
	public void addRedisLiveAnchorBanExpire(LiveAnchorBanExpire liveAnchorBanExpire)throws Exception;
	
	/**
	 * 修改LiveAnchorBanExpire
	 * @param LiveAnchorBanExpire
	 * @return
	 * @throws Exception
	 */
	public void updateRedisLiveAnchorBanExpire(LiveAnchorBanExpire liveAnchorBanExpire)throws Exception;
	
	/**
	 * 删除LiveAnchorBanExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void deleteRedisLiveAnchorBanExpire(String userId)throws Exception;
	
	/**
	 * 查询LiveAnchorBanExpire
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public LiveAnchorBanExpire queryRedisLiveAnchorBanExpire(String userId)throws Exception;
	
	/**
	 * 时间范围查询LiveAnchorBanExpire
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public List<LiveAnchorBanExpire> queryRedisLiveAnchorBanExpire(Date endTime)throws Exception;
}