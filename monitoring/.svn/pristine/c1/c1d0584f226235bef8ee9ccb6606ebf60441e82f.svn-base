package com.ttmv.monitoring.imp;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.MediaForwardData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IMediaForwardDataInter;
import com.ttmv.monitoring.mapper.MediaForwardDataDaoMapper;

public class IMediaForwardDataInterImpl implements IMediaForwardDataInter{

	private MediaForwardDataDaoMapper mediaForwardDataDaoMapper;
	
	public Integer addMediaForwardData(MediaForwardData mediaForwardData) throws Exception{
		Integer result = mediaForwardDataDaoMapper.addMediaForwardData(mediaForwardData);
		return result;
	}

	public MediaForwardData queryMediaForwardData(BigInteger id) throws Exception{
		MediaForwardData data = mediaForwardDataDaoMapper.queryMediaForwardData(id);
		return data;
	}

	public MediaForwardData queryMediaForwardDataByIpAndServerTypeAndPort(DataBeanQuery mediaForwardDataQuery) throws Exception {
		MediaForwardData data = mediaForwardDataDaoMapper.queryMediaForwardDataByIpAndServerTypeAndPort(mediaForwardDataQuery);
		return data;
	}

	public void setmediaForwardDataDaoMapper(MediaForwardDataDaoMapper mediaForwardDataDaoMapper) throws Exception{
		this.mediaForwardDataDaoMapper = mediaForwardDataDaoMapper;
	}

	public List<MediaForwardData> queryListMediaForwardDataByIpAndServerTypeAndPort(
			DataBeanQuery mediaForwardDataQuery) throws Exception {
		List<MediaForwardData> datas =  mediaForwardDataDaoMapper.queryMediaForwardDataListByIpAndServerTypeAndPort(mediaForwardDataQuery);
		return datas;
	}
	
	public List<MediaForwardData> querySelectedMediaForwardData(DataBeanQuery mediaForwardDataQuery)throws Exception {
		List<MediaForwardData> datas =  mediaForwardDataDaoMapper.querySelectedMediaForwardData(mediaForwardDataQuery);
		return datas;
	}

	public List<MediaForwardData> queryListOnDateMediaForwardDataByIpAndServerTypeAndPort(
			DataBeanQuery mediaForwardDataQuery) throws Exception {
		List<MediaForwardData> datas =  mediaForwardDataDaoMapper.queryMediaForwardDataListOnDateByIpAndServerTypeAndPort(mediaForwardDataQuery);
		return datas;
	}
}
