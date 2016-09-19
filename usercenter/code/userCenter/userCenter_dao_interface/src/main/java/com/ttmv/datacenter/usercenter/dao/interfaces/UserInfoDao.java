package com.ttmv.datacenter.usercenter.dao.interfaces;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.operation.query.UserInfoQuery;


public interface UserInfoDao {
	/**
	 * 新增UserInfo
	 * @param UserInfo
	 * @throws Exception
	 */
	public Integer addUserInfo(UserInfo userInfo) throws Exception;
	/**
	 * 修改UserInfo
	 * @param UserInfo
	 * @throws Exception
	 */
	public Integer updateUserInfo(UserInfo userInfo) throws Exception;
	/**
	 * 查询UserInfo
	 * @param userId
	 * @return UserInfo
	 * @throws Exception
	 */
	public UserInfo selectUserInfoByUserId(BigInteger userId) throws Exception;
	/**
	 * 查询UserInfo
	 * @param ttnum
	 * @return UserInfo
	 * @throws Exception
	 */
	public UserInfo selectUserInfoByTTNum(String ttnum) throws Exception;
	/**
	 * 多条件查询
	 * @param UserInfoQuery
	 * @return List<UserInfo>
	 * @throws Exception
	 */
	public List<UserInfo> selectListBySelectivePaging(UserInfoQuery userInfoQuery) throws Exception;
	
	/**
	 * 用户登陆
	 * @param userInfoQuery
	 * @return
	 */
	public List<UserInfo> userLogin(UserInfoQuery userInfoQuery)throws Exception;
	
	/**
	 * 查询用户列表信息根据id列表
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List<UserInfo> selectUserInfosByIds(List<BigInteger> ids)throws Exception;
	
	/**
	 * 添加靓号
	 * 默认装填
	 * @return
	 * @throws Exception
	 */
	public Integer addGoodTTnum(BigInteger userid,String ttNum)throws Exception;
	/**
	 * 修改靓号状态
	 * @param TTnum
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public Integer updateGoodTTnum(BigInteger userid,Integer type)throws Exception;
	
	/**
	 * 查询靓号
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public String selectGoodTTnum(BigInteger userid)throws Exception; 
	
}