package com.ttmv.monitoring.imp;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.UmsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IUmsServerDataInter;
import com.ttmv.monitoring.mapper.UmsServerDataDaoMapper;

public class IUmsServerDataInterImpl implements IUmsServerDataInter {

	private UmsServerDataDaoMapper umsServerDataDaoMapper;
	
	public Integer addUmsServerData(UmsServerData umsServerData)
			throws Exception {
		Integer result = umsServerDataDaoMapper.addUmsServerData(umsServerData);
		return result;
	}

	public UmsServerData queryUmsServerData(BigInteger id)
			throws Exception {
		UmsServerData result = umsServerDataDaoMapper.queryUmsServerData(id);
		return result;
	}

	public UmsServerData queryUmsServerDataByIpAndServerTypeAndPort(
			DataBeanQuery umsServerDataQuery) throws Exception {
		UmsServerData result = umsServerDataDaoMapper.queryUmsServerDataByIpAndServerTypeAndPort(umsServerDataQuery);
		return result;
	}

	public List<UmsServerData> querySelectedUmsServerData(
			DataBeanQuery umsServerDataQuery) throws Exception {
		List<UmsServerData> result = umsServerDataDaoMapper.querySelectedUmsServerData(umsServerDataQuery);
		return result;
	}

	public List<UmsServerData> queryListOnDateUmsServerDataByIpAndServerTypeAndPort(
			DataBeanQuery umsServerDataQuery) throws Exception {
		List<UmsServerData> result = umsServerDataDaoMapper.queryUmsServerDataListOnDateByIpAndServerTypeAndPort(umsServerDataQuery);
		return result;
	}

	public UmsServerDataDaoMapper getUmsServerDataDaoMapper() {
		return umsServerDataDaoMapper;
	}

	public void setUmsServerDataDaoMapper(
			UmsServerDataDaoMapper umsServerDataDaoMapper) {
		this.umsServerDataDaoMapper = umsServerDataDaoMapper;
	}
}
