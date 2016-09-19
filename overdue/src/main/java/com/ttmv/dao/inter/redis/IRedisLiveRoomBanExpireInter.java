package com.ttmv.dao.inter.redis;

import java.util.Date;
import java.util.List;

import com.ttmv.dao.bean.LiveRoomBanExpire;
import com.ttmv.dao.inter.IRedis;

public interface IRedisLiveRoomBanExpireInter extends IRedis{

	/**
	 * 添加LiveRoomBanExpire
	 * @param liveRoomBanExpire
	 * @return
	 * @throws Exception
	 */
	public void addRedisLiveRoomBanExpire(LiveRoomBanExpire liveRoomBanExpire)throws Exception;
	
	/**
	 * 修改LiveRoomBanExpire
	 * @param liveRoomBanExpire
	 * @return
	 * @throws Exception
	 */
	public void updateRedisLiveRoomBanExpire(LiveRoomBanExpire liveRoomBanExpire)throws Exception;
	
	/**
	 * 删除LiveRoomBanExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void deleteRedisLiveRoomBanExpire(String userId)throws Exception;
	
	/**
	 * 查询LiveRoomBanExpire
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public LiveRoomBanExpire queryRedisLiveRoomBanExpire(String userId)throws Exception;
	
	/**
	 * 时间范围查询LiveRoomBanExpire
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public List<LiveRoomBanExpire> queryRedisLiveRoomBanExpire(Date endTime)throws Exception;
}