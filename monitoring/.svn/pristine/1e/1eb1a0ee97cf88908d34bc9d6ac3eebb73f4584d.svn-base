package com.ttmv.monitoring.imp;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.PHPServerData;
import com.ttmv.monitoring.entity.PhpVideoServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IPHPVideoServerDataInter;
import com.ttmv.monitoring.mapper.PHPServerDataDaoMapper;
import com.ttmv.monitoring.mapper.PHPVideoServerDataDaoMapper;

public class IPHPVideoServerDataInterImpl implements IPHPVideoServerDataInter {

	private PHPVideoServerDataDaoMapper pHPVideoServerDataDaoMapper;
	
	public Integer addPhpVideoServerData(PhpVideoServerData phpVideoServerData)
			throws Exception {
		Integer result = pHPVideoServerDataDaoMapper.addPhpVideoServerData(phpVideoServerData);
		return result;
	}

	public PhpVideoServerData queryPhpVideoServerData(BigInteger id)
			throws Exception {
		PhpVideoServerData result = pHPVideoServerDataDaoMapper.queryPhpVideoServerData(id);
		return result;
	}

	public PhpVideoServerData queryPhpVideoServerDataByIpAndServerTypeAndPort(
			DataBeanQuery phpVideoServerDataQuery) throws Exception {
		PhpVideoServerData result = pHPVideoServerDataDaoMapper.queryPhpVideoServerDataByIpAndServerTypeAndPort(phpVideoServerDataQuery);
		return result;
	}

	public List<PhpVideoServerData> querySelectedPhpVideoServerData(
			DataBeanQuery phpVideoServerDataQuery) throws Exception {
		List<PhpVideoServerData> result = pHPVideoServerDataDaoMapper.querySelectedPhpVideoServerData(phpVideoServerDataQuery);
		return result;
	}

	public List<PhpVideoServerData> queryListOnDatePhpVideoServerDataByIpAndServerTypeAndPort(
			DataBeanQuery phpVideoServerDataQuery) throws Exception {
		List<PhpVideoServerData> result = pHPVideoServerDataDaoMapper.queryPhpVideoServerDataListOnDateByIpAndServerTypeAndPort(phpVideoServerDataQuery);
		return result;
	}

	public PHPVideoServerDataDaoMapper getpHPVideoServerDataDaoMapper() {
		return pHPVideoServerDataDaoMapper;
	}

	public void setpHPVideoServerDataDaoMapper(
			PHPVideoServerDataDaoMapper pHPVideoServerDataDaoMapper) {
		this.pHPVideoServerDataDaoMapper = pHPVideoServerDataDaoMapper;
	}
}
