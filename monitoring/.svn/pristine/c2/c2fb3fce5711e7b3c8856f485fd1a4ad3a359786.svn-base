package com.ttmv.monitoring.chartService.impl.im.rms;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QuerySelectedIp;
import com.ttmv.monitoring.entity.RmsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IRmsServerDataInter;

public class QueryRmsSelectedIpServiceImpl extends QuerySelectedIp {

	private static Logger logger = LogManager.getLogger(QueryRmsSelectedIpServiceImpl.class);

	private IRmsServerDataInter iRmsServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		return iRmsServerDataInter.querySelectedRmsServerData((DataBeanQuery)obj);
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new RmsServerData();
	}


	@Override
	protected void addAttributesToQuery(Map reqMap, DataBeanQuery query) {

	}

	public IRmsServerDataInter getiRmsServerDataInter() {
		return iRmsServerDataInter;
	}

	public void setiRmsServerDataInter(IRmsServerDataInter iRmsServerDataInter) {
		this.iRmsServerDataInter = iRmsServerDataInter;
	}
}
