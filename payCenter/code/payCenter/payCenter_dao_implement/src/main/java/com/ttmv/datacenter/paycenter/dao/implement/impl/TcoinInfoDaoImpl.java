package com.ttmv.datacenter.paycenter.dao.implement.impl;

import java.math.BigInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.paycenter.dao.implement.flow.tcoininfo.AddTcoinInfoService;
import com.ttmv.datacenter.paycenter.dao.implement.flow.tcoininfo.QueryTcoinInfoService;
import com.ttmv.datacenter.paycenter.dao.implement.flow.tcoininfo.UpdateBalanceTcoinInfoService;
import com.ttmv.datacenter.paycenter.dao.implement.flow.tcoininfo.UpdateFreezeBalanceTcoinInfoService;
import com.ttmv.datacenter.paycenter.dao.interfaces.TcoinInfoDao;
import com.ttmv.datacenter.paycenter.data.OperationInfo;
import com.ttmv.datacenter.paycenter.data.TcoinInfo;

public class TcoinInfoDaoImpl implements TcoinInfoDao {

	private final Logger logger = LogManager.getLogger(TcoinInfoDaoImpl.class);

	private QueryTcoinInfoService queryTcoinInfoService;
	private AddTcoinInfoService addTcoinInfoService;
	private UpdateBalanceTcoinInfoService updateBalanceTcoinInfoService;
	private UpdateFreezeBalanceTcoinInfoService updateFreezeBalanceTcoinInfoService;

	/**
	 * 添加TcoinInfo对象
	 */
	public Integer addTcoinInfo(TcoinInfo tcoinInfo) throws Exception {
		addTcoinInfoService.write(tcoinInfo);
		return 1;
	}

	/**
	 * 查询TcoinInfo对象
	 * @throws Exception 
	 */
	public TcoinInfo selectTcoinInfo(BigInteger userId) throws Exception{
		TcoinInfo tcoinInfo = queryTcoinInfoService.read(userId);
		return tcoinInfo;
	}

	/**
	 * 加/减 余额
	 * @param amount
	 * @param type
	 */
	public void changeBalance(OperationInfo operationInfo) throws Exception {
		logger.info("[" + operationInfo.getReqId() + "]@@" + "[进入T币账户余额充值或者消费]");
		updateBalanceTcoinInfoService.write(operationInfo);
	}

	/**
	 * 加/减 冻结余额
	 * 
	 * @param userid
	 * @param amount
	 * @param type
	 */
	public void changeFreezeBalance(OperationInfo operationInfo) throws Exception {
		logger.info("[" + operationInfo.getReqId() + "]@@" + "[进入T币账户冻结余额增加或者减少]");
		updateFreezeBalanceTcoinInfoService.write(operationInfo);
	}

	public void setAddTcoinInfoService(AddTcoinInfoService addTcoinInfoService) {
		this.addTcoinInfoService = addTcoinInfoService;
	}

	public void setQueryTcoinInfoService(QueryTcoinInfoService queryTcoinInfoService) {
		this.queryTcoinInfoService = queryTcoinInfoService;
	}

	public void setUpdateBalanceTcoinInfoService(UpdateBalanceTcoinInfoService updateBalanceTcoinInfoService) {
		this.updateBalanceTcoinInfoService = updateBalanceTcoinInfoService;
	}

	public void setUpdateFreezeBalanceTcoinInfoService(
			UpdateFreezeBalanceTcoinInfoService updateFreezeBalanceTcoinInfoService) {
		this.updateFreezeBalanceTcoinInfoService = updateFreezeBalanceTcoinInfoService;
	}
}
