package com.ttmv.monitoring.imp;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.MediaControlData;
import com.ttmv.monitoring.entity.ScreenShotData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IScreenShotDataInter;
import com.ttmv.monitoring.mapper.ScreenShotDataDaoMapper;

public class IScreenShotDataInterImpl implements IScreenShotDataInter {

	private ScreenShotDataDaoMapper screenShotDataDaoMapper;
	public Integer addScreenShotData(ScreenShotData screenShotData)
			throws Exception {
		Integer result = screenShotDataDaoMapper.addScreenShotData(screenShotData);
		return result;
	}
	
	public ScreenShotData queryScreenShotData(BigInteger id) throws Exception {
		ScreenShotData data = screenShotDataDaoMapper.queryScreenShotData(id);
		return data;
	}

	public List<ScreenShotData> querySelectedScreenShotData(
			DataBeanQuery screenShotDataQuery) throws Exception {
		List<ScreenShotData> datas = screenShotDataDaoMapper.querySelectedScreenShotData(screenShotDataQuery);
		return datas;
	}

	public ScreenShotData queryScreenShotDataByIpAndServerTypeAndPort(
			DataBeanQuery screenShotDataQuery) throws Exception {
		ScreenShotData data  = screenShotDataDaoMapper.queryScreenShotDataByIpAndServerTypeAndPort(screenShotDataQuery);
		return data;
	}
	
	public List<ScreenShotData> queryListScreenShotDataByIpAndServerTypeAndPort(
			DataBeanQuery screenShotDataQuery) throws Exception {
		List<ScreenShotData> datas = screenShotDataDaoMapper.queryScreenShotDataListByIpAndServerTypeAndPort(screenShotDataQuery);
		return datas;
	}
	
	public List<ScreenShotData> queryListOnDateScreenShotDataByIpAndServerTypeAndPort(
			DataBeanQuery screenShotDataQuery) throws Exception {
		List<ScreenShotData> datas =  screenShotDataDaoMapper.queryScreenShotDataListOnDateByIpAndServerTypeAndPort(screenShotDataQuery);
		return datas;
	}

	public void setScreenShotDataDaoMapper(ScreenShotDataDaoMapper screenShotDataDaoMapper) {
		this.screenShotDataDaoMapper = screenShotDataDaoMapper;
	}
}
