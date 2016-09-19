package com.ttmv.datacenter.paycenter.dao.interfaces;

import java.math.BigInteger;

import com.ttmv.datacenter.paycenter.data.BrokerageInfo;
import com.ttmv.datacenter.paycenter.data.OperationInfo;

public interface BrokerageInfoDao {

	/**
	 * 添加佣金账户
	 * 
	 * @param brokerageInfo
	 * @return
	 */
	public Integer addBrokerageInfo(BrokerageInfo brokerageInfo)throws Exception;

	/**
	 * 查询佣金账户
	 * 
	 * @param query
	 * @return
	 */
	public BrokerageInfo selectBrokerageInfo(BigInteger userid)throws Exception;
	
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
