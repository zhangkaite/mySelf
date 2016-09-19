package com.ttmv.monitoring.imp;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.ScreenShotData;
import com.ttmv.monitoring.entity.TranscodingData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.ITranscodingDataInter;
import com.ttmv.monitoring.mapper.TranscodingDataDaoMapper;

public class ITranscodingDataInterImpl implements ITranscodingDataInter {

	private TranscodingDataDaoMapper transcodingDataDaoMapper;
	
	public Integer addTranscodingData(TranscodingData transcodingData)throws Exception {
		Integer result = transcodingDataDaoMapper.addTranscodingData(transcodingData);
		return result;
	}

	public TranscodingData queryTranscodingData(BigInteger id) throws Exception {
		TranscodingData data = transcodingDataDaoMapper.queryTranscodingData(id);
		return data;
	}

	public List<TranscodingData> querySelectedTranscodingData(DataBeanQuery transcodingDataQuery) throws Exception {
		List<TranscodingData> datas = transcodingDataDaoMapper.querySelectedTranscodingData(transcodingDataQuery);
		return datas;
	}
	
	public TranscodingData queryTranscodingDataByIpAndServerTypeAndPort(DataBeanQuery transcodingDataQuery) throws Exception {
		TranscodingData data = transcodingDataDaoMapper.queryTranscodingDataByIpAndServerTypeAndPort(transcodingDataQuery);
		return data;
	}
	
	public List<TranscodingData> queryListTranscodingDataByIpAndServerTypeAndPort(
			DataBeanQuery transcodingDataQuery) throws Exception {
		List<TranscodingData> datas = transcodingDataDaoMapper.queryTranscodingDataListByIpAndServerTypeAndPort(transcodingDataQuery);
		return datas;
	}

	public List<TranscodingData> queryListOnDateTranscodingDataByIpAndServerTypeAndPort(
			DataBeanQuery transcodingDataQuery) throws Exception {
		List<TranscodingData> datas =  transcodingDataDaoMapper.queryTranscodingDataListOnDateByIpAndServerTypeAndPort(transcodingDataQuery);
		return datas;
	}

	public void setTranscodingDataDaoMapper(
			TranscodingDataDaoMapper transcodingDataDaoMapper) {
		this.transcodingDataDaoMapper = transcodingDataDaoMapper;
	}


}
