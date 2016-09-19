package com.ttmv.datacenter.paycenter.dao.interfaces;

import java.math.BigInteger;

import com.ttmv.datacenter.paycenter.data.OperationInfo;
import com.ttmv.datacenter.paycenter.data.TcoinInfo;

public interface TcoinInfoDao {

	/**
	 * 添加T币账户
	 * 
	 * @param TcoinInfo
	 * @return
	 */
	public Integer addTcoinInfo(TcoinInfo tcoinInfo)throws Exception;

	/**
	 * 查询T币账户
	 * 
	 * @param query
	 * @return
	 */
	public TcoinInfo selectTcoinInfo(BigInteger userid)throws Exception;
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
