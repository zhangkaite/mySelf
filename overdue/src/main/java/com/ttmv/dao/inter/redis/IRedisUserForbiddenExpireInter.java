package com.ttmv.dao.inter.redis;

import java.util.Date;
import java.util.List;

import com.ttmv.dao.bean.UserForbiddenExpire;
import com.ttmv.dao.inter.IRedis;

public interface IRedisUserForbiddenExpireInter extends IRedis{

	/**
	 * 添加UserForbiddenExpire
	 * @param userForbiddenExpire
	 * @return
	 * @throws Exception
	 */
	public void addRedisUserForbiddenExpire(UserForbiddenExpire userForbiddenExpire)throws Exception;
	
	/**
	 * 修改UserForbiddenExpire
	 * @param userForbiddenExpire
	 * @return
	 * @throws Exception
	 */
	public void updateRedisUserForbiddenExpire(UserForbiddenExpire userForbiddenExpire)throws Exception;
	
	/**
	 * 删除UserForbiddenExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void deleteRedisUserForbiddenExpire(String userId)throws Exception;
	
	/**
	 * 查询UserForbiddenExpire
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public UserForbiddenExpire queryRedisUserForbiddenExpire(String userId)throws Exception;
	
	/**
	 * 时间范围查询UserForbiddenExpire
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public List<UserForbiddenExpire> queryRedisUserForbiddenExpire(Date endTime)throws Exception;
}