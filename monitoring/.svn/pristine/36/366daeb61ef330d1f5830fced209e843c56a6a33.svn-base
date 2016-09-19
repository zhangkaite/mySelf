package com.ttmv.monitoring.inter;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.UserInfo;
import com.ttmv.monitoring.entity.page.Page;
import com.ttmv.monitoring.entity.querybean.UserInfoQuery;

/**
 * 用户信息
 * @author wll
 */
public interface IUserInfoInter {

	/**
	 * 添加UserInfo
	 * @param UserInfo
	 * @return
	 */
	public Integer addUserInfo(UserInfo UserInfo) throws Exception;
	
	/**
	 * 修改UserInfo
	 * @param UserInfo
	 * @return
	 */
	public Integer updateUserInfo(UserInfo UserInfo) throws Exception;
	
	/**
	 * 修改UserInfo状态
	 * @param UserInfo
	 * @return
	 */
	public Integer deleteUserInfo(BigInteger id,Integer status) throws Exception;
	
	/**
	 * 根据ID查询UserInfo
	 * @param id
	 * @return
	 */
	public UserInfo queryUserInfo(BigInteger id) throws Exception;
	
	/**
	 * 条件查询UserInfo，支持模糊查询
	 * Map对象中存在两个key
	 * sum:查询总数(Integer)
	 * data :查询结果(List)
	 * @param UserInfoQuery
	 * @return
	 */
	public Page queryPageUserInfo(UserInfoQuery userInfoQuery) throws Exception;
	
	/**
	 * 条件查询UserInfo，精确查询
	 * @param UserInfoQuery
	 * @return
	 */
	public List<UserInfo> queryUserInfo(UserInfoQuery userInfoQuery) throws Exception;
	
	/**
	 * 用户登陆
	 * @return
	 * @throws Exception
	 */
	public UserInfo login(String userName,String userPasswd)throws Exception;
	
	/**
	 * 根据id的集合 查询用户列表
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List<UserInfo> queryListByIds(List<String> ids)throws Exception;
}
