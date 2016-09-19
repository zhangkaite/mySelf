package com.ttmv.monitoring.imp;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.MdsServerData;
import com.ttmv.monitoring.entity.MssServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IMssServerDataInter;
import com.ttmv.monitoring.mapper.MdsServerDataDaoMapper;
import com.ttmv.monitoring.mapper.MssServerDataDaoMapper;

public class IMssServerDataInterImpl implements IMssServerDataInter {

	private MssServerDataDaoMapper mssServerDataDaoMapper;
	
	public Integer addMssServerData(MssServerData mssServerData)
			throws Exception {
		Integer result = mssServerDataDaoMapper.addMssServerData(mssServerData);
		return result;
	}

	public MssServerData queryMssServerData(BigInteger id)
			throws Exception {
		MssServerData result = mssServerDataDaoMapper.queryMssServerData(id);
		return result;
	}

	public MssServerData queryMssServerDataByIpAndServerTypeAndPort(
			DataBeanQuery mssServerDataQuery) throws Exception {
		MssServerData result = mssServerDataDaoMapper.queryMssServerDataByIpAndServerTypeAndPort(mssServerDataQuery);
		return result;
	}

	public List<MssServerData> querySelectedMssServerData(
			DataBeanQuery mssServerDataQuery) throws Exception {
		List<MssServerData> result = mssServerDataDaoMapper.querySelectedMssServerData(mssServerDataQuery);
		return result;
	}

	public List<MssServerData> queryListOnDateMssServerDataByIpAndServerTypeAndPort(
			DataBeanQuery mssServerDataQuery) throws Exception {
		List<MssServerData> result = mssServerDataDaoMapper.queryMssServerDataListOnDateByIpAndServerTypeAndPort(mssServerDataQuery);
		return result;
	}

	public MssServerDataDaoMapper getMssServerDataDaoMapper() {
		return mssServerDataDaoMapper;
	}

	public void setMssServerDataDaoMapper(MssServerDataDaoMapper mssServerDataDaoMapper) {
		this.mssServerDataDaoMapper = mssServerDataDaoMapper;
	}
}
