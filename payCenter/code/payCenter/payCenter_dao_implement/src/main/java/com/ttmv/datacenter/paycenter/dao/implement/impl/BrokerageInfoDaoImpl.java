package com.ttmv.datacenter.paycenter.dao.implement.impl;

import java.math.BigInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.paycenter.dao.implement.flow.brokerageinfo.AddBrokerageInfoService;
import com.ttmv.datacenter.paycenter.dao.implement.flow.brokerageinfo.QueryBrokerageInfoService;
import com.ttmv.datacenter.paycenter.dao.implement.flow.brokerageinfo.UpdateBalanceBrokerageInfoService;
import com.ttmv.datacenter.paycenter.dao.implement.flow.brokerageinfo.UpdateFreezeBalanceBrokerageInfoService;
import com.ttmv.datacenter.paycenter.dao.interfaces.BrokerageInfoDao;
import com.ttmv.datacenter.paycenter.data.BrokerageInfo;
import com.ttmv.datacenter.paycenter.data.OperationInfo;

public class BrokerageInfoDaoImpl implements BrokerageInfoDao {

	private final Logger logger = LogManager.getLogger(BrokerageInfoDaoImpl.class);
	
	private QueryBrokerageInfoService queryBrokerageInfoService;
	private AddBrokerageInfoService addBrokerageInfoService;
	private UpdateBalanceBrokerageInfoService updateBalanceBrokerageInfoService;
	private UpdateFreezeBalanceBrokerageInfoService updateFreezeBalanceBrokerageInfoService;

	/**
	 * 添加BrokerageInfo对象
	 */
	public Integer addBrokerageInfo(BrokerageInfo BrokerageInfo) throws Exception {
		addBrokerageInfoService.write(BrokerageInfo);
		return 1;
	}

	/**
	 * 查询BrokerageInfo对象
	 * @throws Exception 
	 */
	public BrokerageInfo selectBrokerageInfo(BigInteger userId) throws Exception{
		BrokerageInfo BrokerageInfo = queryBrokerageInfoService.read(userId);
		return BrokerageInfo;
	}

	/**
	 * 加/减 余额
	 * 
	 * @param amount
	 * @param type
	 */
	public void changeBalance(OperationInfo operationInfo) throws Exception {
		logger.info("[" + operationInfo.getReqId() + "]@@" + "[进入佣金账户余额充值或者消费]");
		updateBalanceBrokerageInfoService.write(operationInfo);
	}

	/**
	 * 加/减 冻结余额
	 * 
	 * @param userid
	 * @param amount
	 * @param type
	 */
	public void changeFreezeBalance(OperationInfo operationInfo) throws Exception {
		logger.info("[" + operationInfo.getReqId() + "]@@" + "[进入佣金账户冻结余额增加或者减少]");
		updateFreezeBalanceBrokerageInfoService.write(operationInfo);
	}

	public void setAddBrokerageInfoService(AddBrokerageInfoService addBrokerageInfoService) {
		this.addBrokerageInfoService = addBrokerageInfoService;
	}

	public void setQueryBrokerageInfoService(QueryBrokerageInfoService queryBrokerageInfoService) {
		this.queryBrokerageInfoService = queryBrokerageInfoService;
	}

	public void setUpdateBalanceBrokerageInfoService(UpdateBalanceBrokerageInfoService updateBalanceBrokerageInfoService) {
		this.updateBalanceBrokerageInfoService = updateBalanceBrokerageInfoService;
	}

	public void setUpdateFreezeBalanceBrokerageInfoService(
			UpdateFreezeBalanceBrokerageInfoService updateFreezeBalanceBrokerageInfoService) {
		this.updateFreezeBalanceBrokerageInfoService = updateFreezeBalanceBrokerageInfoService;
	}
}
