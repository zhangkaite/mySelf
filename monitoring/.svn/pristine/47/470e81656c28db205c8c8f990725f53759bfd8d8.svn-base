package com.ttmv.monitoring.imp;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.PrsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IPrsServerDataInter;
import com.ttmv.monitoring.mapper.PrsServerDataDaoMapper;

public class IPrsServerDataInterImpl implements IPrsServerDataInter {

	private PrsServerDataDaoMapper prsServerDataDaoMapper;
	
	public Integer addPrsServerData(PrsServerData prsServerData)
			throws Exception {
		Integer result = prsServerDataDaoMapper.addPrsServerData(prsServerData);
		return result;
	}

	public PrsServerData queryPrsServerData(BigInteger id)
			throws Exception {
		PrsServerData result = prsServerDataDaoMapper.queryPrsServerData(id);
		return result;
	}

	public PrsServerData queryPrsServerDataByIpAndServerTypeAndPort(
			DataBeanQuery prsServerDataQuery) throws Exception {
		PrsServerData result = prsServerDataDaoMapper.queryPrsServerDataByIpAndServerTypeAndPort(prsServerDataQuery);
		return result;
	}

	public List<PrsServerData> querySelectedPrsServerData(
			DataBeanQuery prsServerDataQuery) throws Exception {
		List<PrsServerData> result = prsServerDataDaoMapper.querySelectedPrsServerData(prsServerDataQuery);
		return result;
	}

	public List<PrsServerData> queryListOnDatePrsServerDataByIpAndServerTypeAndPort(
			DataBeanQuery prsServerDataQuery) throws Exception {
		List<PrsServerData> result = prsServerDataDaoMapper.queryPrsServerDataListOnDateByIpAndServerTypeAndPort(prsServerDataQuery);
		return result;
	}

	public PrsServerDataDaoMapper getPrsServerDataDaoMapper() {
		return prsServerDataDaoMapper;
	}

	public void setPrsServerDataDaoMapper(
			PrsServerDataDaoMapper prsServerDataDaoMapper) {
		this.prsServerDataDaoMapper = prsServerDataDaoMapper;
	}
}
