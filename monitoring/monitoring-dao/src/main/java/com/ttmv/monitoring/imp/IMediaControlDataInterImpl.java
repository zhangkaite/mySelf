package com.ttmv.monitoring.imp;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.MediaControlData;
import com.ttmv.monitoring.entity.MediaForwardData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IMediaControlDataInter;
import com.ttmv.monitoring.mapper.MediaControlDataDaoMapper;

public class IMediaControlDataInterImpl implements IMediaControlDataInter{

	private MediaControlDataDaoMapper mediaControlDataDaoMapper;
	
	public Integer addMediaControlData(MediaControlData mediaControlData) throws Exception{
		Integer result = mediaControlDataDaoMapper.addMediaControlData(mediaControlData);
		return result;
	}

	public MediaControlData queryMediaControlData(BigInteger id) throws Exception{
		MediaControlData data = mediaControlDataDaoMapper.queryMediaControlData(id);
		return data;
	}

	public MediaControlData queryMediaControlDataByIpAndServerTypeAndPort(DataBeanQuery mediaControlDataQuery) throws Exception {
		MediaControlData data = mediaControlDataDaoMapper.queryMediaControlDataByIpAndServerTypeAndPort(mediaControlDataQuery);
		return data;
	}

	public List<MediaControlData> querySelectedMediaControlData(DataBeanQuery mediaControlDataQuery)throws Exception {
		List<MediaControlData> datas =  mediaControlDataDaoMapper.querySelectedMediaControlData(mediaControlDataQuery);
		return datas;
	}

	public List<MediaControlData> queryListMediaControlDataByIpAndServerTypeAndPort(
			DataBeanQuery mediaControlDataQuery) throws Exception {
		List<MediaControlData> datas =  mediaControlDataDaoMapper.queryMediaControlDataListByIpAndServerTypeAndPort(mediaControlDataQuery);
		return datas;
	}
	
	public List<MediaControlData> queryListOnDateMediaControlDataByIpAndServerTypeAndPort(
			DataBeanQuery mediaControlDataQuery) throws Exception {
		List<MediaControlData> datas =  mediaControlDataDaoMapper.queryMediaControlDataListOnDateByIpAndServerTypeAndPort(mediaControlDataQuery);
		return datas;
	}

	public void setMediaControlDataDaoMapper(MediaControlDataDaoMapper mediaControlDataDaoMapper) throws Exception{
		this.mediaControlDataDaoMapper = mediaControlDataDaoMapper;
	}
}
