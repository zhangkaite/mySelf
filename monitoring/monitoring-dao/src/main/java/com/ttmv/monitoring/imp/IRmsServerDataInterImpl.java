package com.ttmv.monitoring.imp;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.RmsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IRmsServerDataInter;
import com.ttmv.monitoring.mapper.RmsServerDataDaoMapper;

public class IRmsServerDataInterImpl implements IRmsServerDataInter {

	private RmsServerDataDaoMapper rmsServerDataDaoMapper;
	
	public Integer addRmsServerData(RmsServerData rmsServerData)
			throws Exception {
		Integer result = rmsServerDataDaoMapper.addRmsServerData(rmsServerData);
		return result;
	}

	public RmsServerData queryRmsServerData(BigInteger id)
			throws Exception {
		RmsServerData result = rmsServerDataDaoMapper.queryRmsServerData(id);
		return result;
	}

	public RmsServerData queryRmsServerDataByIpAndServerTypeAndPort(
			DataBeanQuery rmsServerDataQuery) throws Exception {
		RmsServerData result = rmsServerDataDaoMapper.queryRmsServerDataByIpAndServerTypeAndPort(rmsServerDataQuery);
		return result;
	}

	public List<RmsServerData> querySelectedRmsServerData(
			DataBeanQuery rmsServerDataQuery) throws Exception {
		List<RmsServerData> result = rmsServerDataDaoMapper.querySelectedRmsServerData(rmsServerDataQuery);
		return result;
	}

	public List<RmsServerData> queryListOnDateRmsServerDataByIpAndServerTypeAndPort(
			DataBeanQuery rmsServerDataQuery) throws Exception {
		List<RmsServerData> result = rmsServerDataDaoMapper.queryRmsServerDataListOnDateByIpAndServerTypeAndPort(rmsServerDataQuery);
		return result;
	}

	public RmsServerDataDaoMapper getRmsServerDataDaoMapper() {
		return rmsServerDataDaoMapper;
	}

	public void setRmsServerDataDaoMapper(
			RmsServerDataDaoMapper rmsServerDataDaoMapper) {
		this.rmsServerDataDaoMapper = rmsServerDataDaoMapper;
	}
}
