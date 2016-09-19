package com.ttmv.monitoring.chartService.impl.im.lbs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryData;
import com.ttmv.monitoring.entity.LbsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.ILbsServerDataInter;

public class QueryLbsServerDataServiceImpl  extends QueryData{

	private static Logger logger = LogManager.getLogger(QueryLbsServerDataServiceImpl.class);

	private ILbsServerDataInter iLbsServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		List list = new ArrayList();
		list.add(iLbsServerDataInter.queryLbsServerDataByIpAndServerTypeAndPort((DataBeanQuery)obj));
		return list;
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new LbsServerData();
	}

	@Override
	protected void addAttributesToQuery(Map reqMap, Object data) {

	}

	public ILbsServerDataInter getiLbsServerDataInter() {
		return iLbsServerDataInter;
	}

	public void setiLbsServerDataInter(ILbsServerDataInter iLbsServerDataInter) {
		this.iLbsServerDataInter = iLbsServerDataInter;
	}
}
