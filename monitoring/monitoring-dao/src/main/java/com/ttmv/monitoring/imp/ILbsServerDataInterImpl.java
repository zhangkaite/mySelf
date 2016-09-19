package com.ttmv.monitoring.imp;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.LbsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.ILbsServerDataInter;
import com.ttmv.monitoring.mapper.LbsServerDataDaoMapper;

public class ILbsServerDataInterImpl implements ILbsServerDataInter {

	private LbsServerDataDaoMapper lbsServerDataDaoMapper;
	
	public Integer addLbsServerData(LbsServerData lbsServerData)
			throws Exception {
		Integer result = lbsServerDataDaoMapper.addLbsServerData(lbsServerData);
		return result;
	}

	public LbsServerData queryLbsServerData(BigInteger id)
			throws Exception {
		LbsServerData result = lbsServerDataDaoMapper.queryLbsServerData(id);
		return result;
	}

	public LbsServerData queryLbsServerDataByIpAndServerTypeAndPort(
			DataBeanQuery lbsServerDataQuery) throws Exception {
		LbsServerData result = lbsServerDataDaoMapper.queryLbsServerDataByIpAndServerTypeAndPort(lbsServerDataQuery);
		return result;
	}

	public List<LbsServerData> querySelectedLbsServerData(
			DataBeanQuery lbsServerDataQuery) throws Exception {
		List<LbsServerData> result = lbsServerDataDaoMapper.querySelectedLbsServerData(lbsServerDataQuery);
		return result;
	}

	public List<LbsServerData> queryListOnDateLbsServerDataByIpAndServerTypeAndPort(
			DataBeanQuery lbsServerDataQuery) throws Exception {
		List<LbsServerData> result = lbsServerDataDaoMapper.queryLbsServerDataListOnDateByIpAndServerTypeAndPort(lbsServerDataQuery);
		return result;
	}

	public LbsServerDataDaoMapper getLbsServerDataDaoMapper() {
		return lbsServerDataDaoMapper;
	}

	public void setLbsServerDataDaoMapper(LbsServerDataDaoMapper lbsServerDataDaoMapper) {
		this.lbsServerDataDaoMapper = lbsServerDataDaoMapper;
	}
}
