package com.ttmv.datacenter.usercenter.dao.interfaces;

import java.util.List;

import com.ttmv.datacenter.usercenter.domain.data.record.UserLoginRecord;
import com.ttmv.datacenter.usercenter.domain.operation.query.UserLoginRecordQuery;

public interface UserLoginRecordDao {

	/**
	 * 添加UserLogin
	 * @param userLogin
	 * @throws Exception
	 */
	public void addUserLoginRecord(UserLoginRecord userLogin)throws Exception;
	
	/**
     * 多条件查询UserLogin
     * @param userLoginQuery
     * @return List<UserLogin>
     * @throws Exception
     */
	public List<UserLoginRecord> selectListBySelective(UserLoginRecordQuery query)throws Exception;
	
}
