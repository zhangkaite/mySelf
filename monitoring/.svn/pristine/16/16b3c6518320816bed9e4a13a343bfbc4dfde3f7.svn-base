package com.ttmv.monitoring.chartService.impl.ot;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QuerySelectedPort;
import com.ttmv.monitoring.entity.TranscodingData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.ITranscodingDataInter;

public class QueryTDSelectedPortServiceImpl extends QuerySelectedPort{

	private static Logger logger = LogManager.getLogger(QueryTDSelectedPortServiceImpl.class);

	private ITranscodingDataInter iTranscodingDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		return iTranscodingDataInter.querySelectedTranscodingData((DataBeanQuery) obj);
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new TranscodingData();
	}

	@Override
	protected void addAttributesToQuery(Map reqMap, DataBeanQuery query) {

	}

	public ITranscodingDataInter getiTranscodingDataInter() {
		return iTranscodingDataInter;
	}

	public void setiTranscodingDataInter(ITranscodingDataInter iTranscodingDataInter) {
		this.iTranscodingDataInter = iTranscodingDataInter;
	}
}
