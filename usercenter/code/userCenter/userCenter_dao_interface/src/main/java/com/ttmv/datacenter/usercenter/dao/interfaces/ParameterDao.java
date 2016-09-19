package com.ttmv.datacenter.usercenter.dao.interfaces;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.datacenter.usercenter.domain.data.Parameter;
import com.ttmv.datacenter.usercenter.domain.operation.query.ParameterQuery;

public interface ParameterDao {
	/**
	 * 新增Parameter
	 * @param Parameter
	 * @return
	 */
	public int addParameter(Parameter parameter)throws Exception ;

	/**
	 * 修改Parameter
	 * @param Parameter
	 * @return
	 */
	public int updateParameter(Parameter parameter)throws Exception ;

	/**
	 * 删除Parameter
	 * @param id
	 * @return
	 */
	public int deleteParameter(BigInteger id)throws Exception ;

	/**
	 * 通过key删除parameter
	 * @param key
	 * @throws Exception
	 */
	public int deleteParameterByKey(String key)throws Exception;
	/**
	 * 修改Parameter
	 * @param id
	 * @return
	 */
	public Parameter selectParameter(BigInteger id)throws Exception ;
	
	/**
	 * 多条件查询
	 * @param map
	 * @return
	 */
	public List<Parameter> selectListBySelective(ParameterQuery parameterQuery)throws Exception ;
}