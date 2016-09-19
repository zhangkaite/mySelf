package com.ttmv.monitoring.chartService.impl.mf;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QuerySelectedPort;
import com.ttmv.monitoring.entity.MediaForwardData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IMediaForwardDataInter;

public class QuerySelectedPortServiceImpl extends QuerySelectedPort {

	private static Logger logger = LogManager.getLogger(QuerySelectedPortServiceImpl.class);

	private IMediaForwardDataInter iMediaForwardDataInter;


	@Override
	protected List getQuery(Object obj) throws Exception {
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		return iMediaForwardDataInter.querySelectedMediaForwardData((DataBeanQuery)obj);
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new MediaForwardData();
	}

	@Override
	protected void addAttributesToQuery(Map reqMap, DataBeanQuery query) {

	}

	public IMediaForwardDataInter getiMediaForwardDataInter() {
		return iMediaForwardDataInter;
	}

	public void setiMediaForwardDataInter(
			IMediaForwardDataInter iMediaForwardDataInter) {
		this.iMediaForwardDataInter = iMediaForwardDataInter;
	}
}
