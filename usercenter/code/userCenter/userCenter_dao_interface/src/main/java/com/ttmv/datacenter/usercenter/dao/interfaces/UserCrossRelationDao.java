package com.ttmv.datacenter.usercenter.dao.interfaces;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.datacenter.usercenter.domain.data.UserCrossRelation;
import com.ttmv.datacenter.usercenter.domain.operation.query.UserCrossRelationQuery;

public interface UserCrossRelationDao{
   
	/**
	 * 新增CrossRelation
	 * @param crossRelation
	 * @return
	 */
	public Integer addUserCrossRelation(UserCrossRelation crossRelation)throws Exception ;
	
	/**
	 * 修改CrossRelation
	 * @param crossRelation
	 * @return
	 */
	public Integer updateUserCrossRelation(UserCrossRelation crossRelation)throws Exception ;
	
	/**
	 * 删除CrossRelation
	 * @param id
	 * @return
	 */
	public Integer deleteUserCrossRelation(BigInteger id,String reqID)throws Exception ;
	
	/**
	 * 修改CrossRelation
	 * @param id
	 * @return
	 */
	public UserCrossRelation selectUserCrossRelation(BigInteger id)throws Exception ;
	
	/**
	 * 多条件查询
	 * @param crossRelation
	 * @return
	 */
	public List<UserCrossRelation> selectListBySelective(UserCrossRelationQuery crossRelationQuery)throws Exception ;
	/**
	 * 通过userid 查询UserInfo的好友的id的集合
	 * @param userid
	 * @return
	 */
	public List<BigInteger> selectUserInfoIdsByUserId(BigInteger userid,Integer type)throws Exception;
}