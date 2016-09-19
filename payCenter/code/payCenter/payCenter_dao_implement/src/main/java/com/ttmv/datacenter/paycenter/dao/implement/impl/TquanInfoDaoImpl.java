package com.ttmv.datacenter.paycenter.dao.implement.impl;

import java.math.BigInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.paycenter.dao.implement.flow.tquaninfo.AddTquanInfoService;
import com.ttmv.datacenter.paycenter.dao.implement.flow.tquaninfo.QueryTquanInfoService;
import com.ttmv.datacenter.paycenter.dao.implement.flow.tquaninfo.UpdateBalanceTquanInfoService;
import com.ttmv.datacenter.paycenter.dao.implement.flow.tquaninfo.UpdateFreezeBalanceTquanInfoService;
import com.ttmv.datacenter.paycenter.dao.interfaces.TquanInfoDao;
import com.ttmv.datacenter.paycenter.data.OperationInfo;
import com.ttmv.datacenter.paycenter.data.TquanInfo;

public class TquanInfoDaoImpl implements TquanInfoDao {

	private final Logger logger = LogManager.getLogger(TquanInfoDaoImpl.class);
	
	private QueryTquanInfoService queryTquanInfoService;
	private AddTquanInfoService addTquanInfoService;
	private UpdateBalanceTquanInfoService updateBalanceTquanInfoService;
	private UpdateFreezeBalanceTquanInfoService updateFreezeBalanceTquanInfoService;

	/**
	 * 添加TquanInfo对象
	 */
	public Integer addTquanInfo(TquanInfo tquanInfo) throws Exception {
		addTquanInfoService.write(tquanInfo);
		return 1;
	}

	/**
	 * 查询TquanInfo对象
	 * @throws Exception 
	 */
	public TquanInfo selectTquanInfo(BigInteger userId) throws Exception{
		TquanInfo TquanInfo = queryTquanInfoService.read(userId);
		return TquanInfo;
	}

	/**
	 * 加/减 余额
	 * 
	 * @param amount
	 * @param type
	 */
	public void changeBalance(OperationInfo operationInfo) throws Exception {
		logger.info("[" + operationInfo.getReqId() + "]@@" + "[进入T卷账户余额充值或者消费]");
		updateBalanceTquanInfoService.write(operationInfo);
	}

	/**
	 * 加/减 冻结余额
	 * 
	 * @param userid
	 * @param amount
	 * @param type
	 */
	public void changeFreezeBalance(OperationInfo operationInfo) throws Exception {
		logger.info("[" + operationInfo.getReqId() + "]@@" + "[进入T卷账户冻结余额增加或者减少]");
		updateFreezeBalanceTquanInfoService.write(operationInfo);
	}

	public void setAddTquanInfoService(AddTquanInfoService addTquanInfoService) {
		this.addTquanInfoService = addTquanInfoService;
	}

	public void setQueryTquanInfoService(QueryTquanInfoService queryTquanInfoService) {
		this.queryTquanInfoService = queryTquanInfoService;
	}

	public void setUpdateBalanceTquanInfoService(UpdateBalanceTquanInfoService updateBalanceTquanInfoService) {
		this.updateBalanceTquanInfoService = updateBalanceTquanInfoService;
	}

	public void setUpdateFreezeBalanceTquanInfoService(
			UpdateFreezeBalanceTquanInfoService updateFreezeBalanceTquanInfoService) {
		this.updateFreezeBalanceTquanInfoService = updateFreezeBalanceTquanInfoService;
	}
}
