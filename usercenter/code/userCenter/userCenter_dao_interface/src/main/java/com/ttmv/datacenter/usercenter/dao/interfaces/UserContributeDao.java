package com.ttmv.datacenter.usercenter.dao.interfaces;

import java.util.List;

import com.ttmv.datacenter.usercenter.domain.data.UserContribution;


public interface UserContributeDao {
	
	/**
	 * 根据房间ID查询所有刷的用户
	 * @return
	 * @throws Exception
	 */
	public List<UserContribution> getAllUserContributionByRoomId(UserContribution queryContributionList)throws Exception;
	
	/**
	 * 修改用户贡献信息
	 * @param userContribution
	 * @return
	 * @throws Exception
	 */
	public Integer updateUserContribution(UserContribution userContribution)throws Exception;
	
	/**
	 * 根据用户room_id和datatype删除用户数据
	 * @param userContribution
	 * @return
	 * @throws Exception
	 */
	public Integer deleteByRoomIdAndDataType(UserContribution userContribution)throws Exception;
}