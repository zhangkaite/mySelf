package com.ttmv.monitoring.chartService.impl.mn;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QuerySelectedPort;
import com.ttmv.monitoring.entity.MediaControlData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IMediaControlDataInter;

public class QueryMNSelectedPortServiceImpl extends QuerySelectedPort {

	private static Logger logger = LogManager.getLogger(QueryMNSelectedPortServiceImpl.class);

	private IMediaControlDataInter iMediaControlDataInter;


	@Override
	protected List getQuery(Object obj) throws Exception {
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		return iMediaControlDataInter.querySelectedMediaControlData((DataBeanQuery)obj);
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new MediaControlData();
	}

	@Override
	protected void addAttributesToQuery(Map reqMap, DataBeanQuery query) {

	}

	public IMediaControlDataInter getiMediaControlDataInter() {
		return iMediaControlDataInter;
	}

	public void setiMediaControlDataInter(
			IMediaControlDataInter iMediaControlDataInter) {
		this.iMediaControlDataInter = iMediaControlDataInter;
	}
}
