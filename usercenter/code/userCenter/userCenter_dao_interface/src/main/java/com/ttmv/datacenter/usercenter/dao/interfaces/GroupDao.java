package com.ttmv.datacenter.usercenter.dao.interfaces;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.datacenter.usercenter.domain.data.Group;
import com.ttmv.datacenter.usercenter.domain.operation.query.GroupQuery;

public interface GroupDao {
	/**
	 * 新增Ugroup
	 * 
	 * @param Group
	 * @return
	 */
	public void addUgroup(Group ugroup)throws Exception ;

	/**
	 * 修改Ugroup
	 * 
	 * @param Group
	 * @return
	 */
	public void updateUgroup(Group ugroup)throws Exception ;

	/**
	 * 删除Ugroup
	 * 
	 * @param id
	 * @return
	 */
	public void deleteUgroup(BigInteger id,String reqID)throws Exception ;

	/**
	 * 根据id查询Group
	 * 
	 * @param id
	 * @return
	 */
	public Group selectUgroup(BigInteger id)throws Exception ;
	/**
	 * 多条件查询
	 * @param map
	 * @return
	 */
	public List<Group> selectListBySelective(GroupQuery groupQuery)throws Exception ;
	
	/**
	 * 通过多个id查询Group集合
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List<Group> selectGroupsByIds(List<BigInteger> ids)throws Exception;
}