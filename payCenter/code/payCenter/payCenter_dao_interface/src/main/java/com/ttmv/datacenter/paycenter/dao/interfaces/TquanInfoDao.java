package com.ttmv.datacenter.paycenter.dao.interfaces;

import java.math.BigInteger;

import com.ttmv.datacenter.paycenter.data.OperationInfo;
import com.ttmv.datacenter.paycenter.data.TquanInfo;

public interface TquanInfoDao {

	/**
	 * 添加T券账户
	 * 
	 * @param TquanInfo
	 * @return
	 */
	public Integer addTquanInfo(TquanInfo tquanInfo)throws Exception;

	/**
	 * 查询T券账户
	 * 
	 * @param query
	 * @return
	 */
	public TquanInfo selectTquanInfo(BigInteger userid)throws Exception;
	/**
	 * 加/减 余额
	 * @param userid
	 * @param amount
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public void changeBalance(OperationInfo operationInfo)throws Exception;
	/**
	 * 加/减 冻结余额
	 * @param userid
	 * @param amount
	 * @param type
	 */
	public void changeFreezeBalance(OperationInfo operationInfo)throws Exception;
	
}
