package com.ttmv.monitoring.imp;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.MtsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IMtsServerDataInter;
import com.ttmv.monitoring.mapper.MtsServerDataDaoMapper;

public class IMtsServerDataInterImpl implements IMtsServerDataInter {

	private MtsServerDataDaoMapper mtsServerDataDaoMapper;
	
	public Integer addMtsServerData(MtsServerData mtsServerData)
			throws Exception {
		Integer result = mtsServerDataDaoMapper.addMtsServerData(mtsServerData);
		return result;
	}

	public MtsServerData queryMtsServerData(BigInteger id)
			throws Exception {
		MtsServerData result = mtsServerDataDaoMapper.queryMtsServerData(id);
		return result;
	}

	public MtsServerData queryMtsServerDataByIpAndServerTypeAndPort(
			DataBeanQuery mtsServerDataQuery) throws Exception {
		MtsServerData result = mtsServerDataDaoMapper.queryMtsServerDataByIpAndServerTypeAndPort(mtsServerDataQuery);
		return result;
	}

	public List<MtsServerData> querySelectedMtsServerData(
			DataBeanQuery mtsServerDataQuery) throws Exception {
		List<MtsServerData> result = mtsServerDataDaoMapper.querySelectedMtsServerData(mtsServerDataQuery);
		return result;
	}

	public List<MtsServerData> queryListOnDateMtsServerDataByIpAndServerTypeAndPort(
			DataBeanQuery mtsServerDataQuery) throws Exception {
		List<MtsServerData> result = mtsServerDataDaoMapper.queryMtsServerDataListOnDateByIpAndServerTypeAndPort(mtsServerDataQuery);
		return result;
	}

	public MtsServerDataDaoMapper getMtsServerDataDaoMapper() {
		return mtsServerDataDaoMapper;
	}

	public void setMtsServerDataDaoMapper(MtsServerDataDaoMapper mtsServerDataDaoMapper) {
		this.mtsServerDataDaoMapper = mtsServerDataDaoMapper;
	}
}
