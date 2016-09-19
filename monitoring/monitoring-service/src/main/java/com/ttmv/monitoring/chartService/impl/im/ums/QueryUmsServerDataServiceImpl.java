package com.ttmv.monitoring.chartService.impl.im.ums;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryData;
import com.ttmv.monitoring.entity.UmsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IUmsServerDataInter;

public class QueryUmsServerDataServiceImpl  extends QueryData{

	private static Logger logger = LogManager.getLogger(QueryUmsServerDataServiceImpl.class);

	private IUmsServerDataInter iUmsServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		List list = new ArrayList();
		list.add(iUmsServerDataInter.queryUmsServerDataByIpAndServerTypeAndPort((DataBeanQuery)obj));
		return list;
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new UmsServerData();
	}

	@Override
	protected void addAttributesToQuery(Map reqMap, Object data) {

	}

	public IUmsServerDataInter getiUmsServerDataInter() {
		return iUmsServerDataInter;
	}

	public void setiUmsServerDataInter(IUmsServerDataInter iUmsServerDataInter) {
		this.iUmsServerDataInter = iUmsServerDataInter;
	}
}
