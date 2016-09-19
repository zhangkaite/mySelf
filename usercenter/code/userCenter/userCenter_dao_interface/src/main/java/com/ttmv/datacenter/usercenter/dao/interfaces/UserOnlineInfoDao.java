package com.ttmv.datacenter.usercenter.dao.interfaces;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.datacenter.usercenter.domain.data.UserOnlineInfo;
import com.ttmv.datacenter.usercenter.domain.operation.query.UserOnlineInfoQuery;

public interface UserOnlineInfoDao {
	/**
	 * 新增UserOnline
	 * @param UserOnlineInfo
	 * @throws Exception
	 */
	public void addUserOnlineInfo(UserOnlineInfo userOnline) throws Exception;
	/**
	 * 修改UserOnline
	 * @param UserOnlineInfo
	 * @throws Exception
	 */
	public void updateUserOnlineInfo(UserOnlineInfo userOnline)
			throws Exception;
	/**
	 * 删除UserOnline
	 * @param id
	 * @throws Exception
	 */
	public void deleteUserOnlineInfo(BigInteger id) throws Exception;
	/**
	 * 修改UserOnline
	 * @param id
	 * @return UserOnline
	 * @throws Exception
	 */
	public UserOnlineInfo selectUserOnlineInfo(BigInteger id) throws Exception;
	/**
	 * 多条件查询
	 * @param userOnlineQuery
	 * @return List<UserOnline>
	 * @throws Exception
	 */
	public List<UserOnlineInfo> selectListBySelective(
			UserOnlineInfoQuery userOnlineRecordQuery) throws Exception;
}
