package com.ttmv.monitoring.imp;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.MediaControlData;
import com.ttmv.monitoring.entity.PHPServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IPHPServerDataInter;
import com.ttmv.monitoring.mapper.PHPServerDataDaoMapper;

public class IPHPServerDataInterImpl implements IPHPServerDataInter {

	private PHPServerDataDaoMapper pHPServerDataDaoMapper;
	
	public Integer addPHPServerData(PHPServerData pHPServerData)throws Exception {
		Integer result =  pHPServerDataDaoMapper.addPHPServerData(pHPServerData);
		return result;
	}

	public PHPServerData queryPHPServerData(BigInteger id) throws Exception {
		PHPServerData data =  pHPServerDataDaoMapper.queryPHPServerData(id);
		return data;
	}
	
	public PHPServerData queryPHPServerDataByIpAndServerTypeAndPort(DataBeanQuery pHPServerDataQuery)throws Exception{
		PHPServerData data = pHPServerDataDaoMapper.queryPhpServerDataByIpAndServerTypeAndPort(pHPServerDataQuery);
		return data;
	}

	public List<PHPServerData> querySelectedPHPServerData(DataBeanQuery pHPServerDataQuery) throws Exception {
		List<PHPServerData> datas = pHPServerDataDaoMapper.querySelectedPHPServerData(pHPServerDataQuery);
		return datas;
	}
	
	public List<PHPServerData> queryListPHPServerDataByIpAndServerTypeAndPort(
			DataBeanQuery pHPServerDataQuery) throws Exception {
		List<PHPServerData> datas = pHPServerDataDaoMapper.queryPHPServerDataListByIpAndServerTypeAndPort(pHPServerDataQuery);
		return datas;
	}

	public List<PHPServerData> queryListOnDatePHPServerDataByIpAndServerTypeAndPort(
			DataBeanQuery pHPServerDataQuery) throws Exception {
		List<PHPServerData> datas =  pHPServerDataDaoMapper.queryPHPServerDataListOnDateByIpAndServerTypeAndPort(pHPServerDataQuery);
		return datas;
	}

	public void setpHPServerDataDaoMapper(PHPServerDataDaoMapper pHPServerDataDaoMapper) {
		this.pHPServerDataDaoMapper = pHPServerDataDaoMapper;
	}
}
