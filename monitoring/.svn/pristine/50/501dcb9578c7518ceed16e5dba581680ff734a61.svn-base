package com.ttmv.monitoring.imp;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.HpServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IHpServerDataInter;
import com.ttmv.monitoring.mapper.HpServerDataDaoMapper;

public class IHpServerDataInterImpl implements IHpServerDataInter {

	private HpServerDataDaoMapper hpServerDataDaoMapper;
	
	public Integer addHpServerData(HpServerData hpServerData)
			throws Exception {
		Integer result = hpServerDataDaoMapper.addHpServerData(hpServerData);
		return result;
	}

	public HpServerData queryHpServerData(BigInteger id)
			throws Exception {
		HpServerData result = hpServerDataDaoMapper.queryHpServerData(id);
		return result;
	}

	public HpServerData queryHpServerDataByIpAndServerTypeAndPort(
			DataBeanQuery hpServerDataQuery) throws Exception {
		HpServerData result = hpServerDataDaoMapper.queryHpServerDataByIpAndServerTypeAndPort(hpServerDataQuery);
		return result;
	}

	public List<HpServerData> querySelectedHpServerData(
			DataBeanQuery hpServerDataQuery) throws Exception {
		List<HpServerData> result = hpServerDataDaoMapper.querySelectedHpServerData(hpServerDataQuery);
		return result;
	}

	public List<HpServerData> queryListOnDateHpServerDataByIpAndServerTypeAndPort(
			DataBeanQuery hpServerDataQuery) throws Exception {
		List<HpServerData> result = hpServerDataDaoMapper.queryHpServerDataListOnDateByIpAndServerTypeAndPort(hpServerDataQuery);
		return result;
	}

	public HpServerDataDaoMapper getHpServerDataDaoMapper() {
		return hpServerDataDaoMapper;
	}

	public void setHpServerDataDaoMapper(HpServerDataDaoMapper hpServerDataDaoMapper) {
		this.hpServerDataDaoMapper = hpServerDataDaoMapper;
	}
}
