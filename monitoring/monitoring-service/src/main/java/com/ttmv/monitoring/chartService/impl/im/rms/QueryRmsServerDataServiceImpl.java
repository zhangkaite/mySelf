package com.ttmv.monitoring.chartService.impl.im.rms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryData;
import com.ttmv.monitoring.entity.RmsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IRmsServerDataInter;

public class QueryRmsServerDataServiceImpl  extends QueryData{

	private static Logger logger = LogManager.getLogger(QueryRmsServerDataServiceImpl.class);

	private IRmsServerDataInter iRmsServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		List list = new ArrayList();
		list.add(iRmsServerDataInter.queryRmsServerDataByIpAndServerTypeAndPort((DataBeanQuery)obj));
		return list;
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
	protected void addAttributesToQuery(Map reqMap, Object data) {

	}

	public IRmsServerDataInter getiRmsServerDataInter() {
		return iRmsServerDataInter;
	}

	public void setiRmsServerDataInter(IRmsServerDataInter iRmsServerDataInter) {
		this.iRmsServerDataInter = iRmsServerDataInter;
	}
}
