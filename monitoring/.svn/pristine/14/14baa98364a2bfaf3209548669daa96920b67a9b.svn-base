package com.ttmv.monitoring.imp;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.MdsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IMdsServerDataInter;
import com.ttmv.monitoring.mapper.MdsServerDataDaoMapper;

public class IMdsServerDataInterImpl implements IMdsServerDataInter {

	private MdsServerDataDaoMapper mdsServerDataDaoMapper;
	
	public Integer addMdsServerData(MdsServerData mdsServerData)
			throws Exception {
		Integer result = mdsServerDataDaoMapper.addMdsServerData(mdsServerData);
		return result;
	}

	public MdsServerData queryMdsServerData(BigInteger id)
			throws Exception {
		MdsServerData result = mdsServerDataDaoMapper.queryMdsServerData(id);
		return result;
	}

	public MdsServerData queryMdsServerDataByIpAndServerTypeAndPort(
			DataBeanQuery mdsServerDataQuery) throws Exception {
		MdsServerData result = mdsServerDataDaoMapper.queryMdsServerDataByIpAndServerTypeAndPort(mdsServerDataQuery);
		return result;
	}

	public List<MdsServerData> querySelectedMdsServerData(
			DataBeanQuery mdsServerDataQuery) throws Exception {
		List<MdsServerData> result = mdsServerDataDaoMapper.querySelectedMdsServerData(mdsServerDataQuery);
		return result;
	}

	public List<MdsServerData> queryListOnDateMdsServerDataByIpAndServerTypeAndPort(
			DataBeanQuery mdsServerDataQuery) throws Exception {
		List<MdsServerData> result = mdsServerDataDaoMapper.queryMdsServerDataListOnDateByIpAndServerTypeAndPort(mdsServerDataQuery);
		return result;
	}

	public MdsServerDataDaoMapper getMdsServerDataDaoMapper() {
		return mdsServerDataDaoMapper;
	}

	public void setMdsServerDataDaoMapper(MdsServerDataDaoMapper mdsServerDataDaoMapper) {
		this.mdsServerDataDaoMapper = mdsServerDataDaoMapper;
	}
}
