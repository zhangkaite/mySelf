package com.ttmv.dao.inter.redis;

import java.util.Date;
import java.util.List;

import com.ttmv.dao.bean.RoomForbiddenExpire;
import com.ttmv.dao.inter.IRedis;

public interface IRedisRoomForbiddenExpireInter extends IRedis{

	/**
	 * 添加RoomForbiddenExpire
	 * @param roomForbiddenExpire
	 * @return
	 * @throws Exception
	 */
	public void addRedisRoomForbiddenExpire(RoomForbiddenExpire roomForbiddenExpire)throws Exception;
	
	/**
	 * 修改RoomForbiddenExpire
	 * @param roomForbiddenExpire
	 * @return
	 * @throws Exception
	 */
	public void updateRedisRoomForbiddenExpire(RoomForbiddenExpire roomForbiddenExpire)throws Exception;
	
	/**
	 * 删除RoomForbiddenExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void deleteRedisRoomForbiddenExpire(String userId)throws Exception;
	
	/**
	 * 查询RoomForbiddenExpire
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public RoomForbiddenExpire queryRedisRoomForbiddenExpire(String userId)throws Exception;
	
	/**
	 * 时间范围查询RoomForbiddenExpire
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public List<RoomForbiddenExpire> queryRedisRoomForbiddenExpire(Date endTime)throws Exception;
}