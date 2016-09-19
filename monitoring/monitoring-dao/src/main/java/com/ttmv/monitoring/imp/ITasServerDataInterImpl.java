package com.ttmv.monitoring.imp;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.TasServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.ITasServerDataInter;
import com.ttmv.monitoring.mapper.TasServerDataDaoMapper;

public class ITasServerDataInterImpl implements ITasServerDataInter {

	private TasServerDataDaoMapper tasServerDataDaoMapper;
	
	public Integer addTasServerData(TasServerData tasServerData)
			throws Exception {
		Integer result = tasServerDataDaoMapper.addTasServerData(tasServerData);
		return result;
	}

	public TasServerData queryTasServerData(BigInteger id)
			throws Exception {
		TasServerData result = tasServerDataDaoMapper.queryTasServerData(id);
		return result;
	}

	public TasServerData queryTasServerDataByIpAndServerTypeAndPort(
			DataBeanQuery tasServerDataQuery) throws Exception {
		TasServerData result = tasServerDataDaoMapper.queryTasServerDataByIpAndServerTypeAndPort(tasServerDataQuery);
		return result;
	}

	public List<TasServerData> querySelectedTasServerData(
			DataBeanQuery tasServerDataQuery) throws Exception {
		List<TasServerData> result = tasServerDataDaoMapper.querySelectedTasServerData(tasServerDataQuery);
		return result;
	}

	public List<TasServerData> queryListOnDateTasServerDataByIpAndServerTypeAndPort(
			DataBeanQuery tasServerDataQuery) throws Exception {
		List<TasServerData> result = tasServerDataDaoMapper.queryTasServerDataListOnDateByIpAndServerTypeAndPort(tasServerDataQuery);
		return result;
	}

	public TasServerDataDaoMapper getTasServerDataDaoMapper() {
		return tasServerDataDaoMapper;
	}

	public void setTasServerDataDaoMapper(
			TasServerDataDaoMapper tasServerDataDaoMapper) {
		this.tasServerDataDaoMapper = tasServerDataDaoMapper;
	}
}
