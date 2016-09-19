package com.ttmv.monitoring.imp;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.PhpManagerServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IPHPManagerServerDataInter;
import com.ttmv.monitoring.mapper.PHPManagerServerDataDaoMapper;

public class IPHPManagerServerDataInterImpl implements IPHPManagerServerDataInter {

	private PHPManagerServerDataDaoMapper pHPManagerServerDataDaoMapper;
	
	public Integer addPhpManagerServerData(PhpManagerServerData phpManagerServerData)
			throws Exception {
		Integer result = pHPManagerServerDataDaoMapper.addPhpManagerServerData(phpManagerServerData);
		return result;
	}

	public PhpManagerServerData queryPhpManagerServerData(BigInteger id)
			throws Exception {
		PhpManagerServerData result = pHPManagerServerDataDaoMapper.queryPhpManagerServerData(id);
		return result;
	}

	public PhpManagerServerData queryPhpManagerServerDataByIpAndServerTypeAndPort(
			DataBeanQuery phpManagerServerDataQuery) throws Exception {
		PhpManagerServerData result = pHPManagerServerDataDaoMapper.queryPhpManagerServerDataByIpAndServerTypeAndPort(phpManagerServerDataQuery);
		return result;
	}

	public List<PhpManagerServerData> querySelectedPhpManagerServerData(
			DataBeanQuery phpManagerServerDataQuery) throws Exception {
		List<PhpManagerServerData> result = pHPManagerServerDataDaoMapper.querySelectedPhpManagerServerData(phpManagerServerDataQuery);
		return result;
	}

	public List<PhpManagerServerData> queryListOnDatePhpManagerServerDataByIpAndServerTypeAndPort(
			DataBeanQuery phpManagerServerDataQuery) throws Exception {
		List<PhpManagerServerData> result = pHPManagerServerDataDaoMapper.queryPhpManagerServerDataListOnDateByIpAndServerTypeAndPort(phpManagerServerDataQuery);
		return result;
	}

	public PHPManagerServerDataDaoMapper getpHPManagerServerDataDaoMapper() {
		return pHPManagerServerDataDaoMapper;
	}

	public void setpHPManagerServerDataDaoMapper(
			PHPManagerServerDataDaoMapper pHPManagerServerDataDaoMapper) {
		this.pHPManagerServerDataDaoMapper = pHPManagerServerDataDaoMapper;
	}
}
