package com.ttmv.monitoring.chartService.impl.im.prs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryData;
import com.ttmv.monitoring.entity.PrsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IPrsServerDataInter;

public class QueryPrsServerDataServiceImpl  extends QueryData{

	private static Logger logger = LogManager.getLogger(QueryPrsServerDataServiceImpl.class);

	private IPrsServerDataInter iPrsServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		List list = new ArrayList();
		list.add(iPrsServerDataInter.queryPrsServerDataByIpAndServerTypeAndPort((DataBeanQuery)obj));
		return list;
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new PrsServerData();
	}

	@Override
	protected void addAttributesToQuery(Map reqMap, Object data) {

	}

	public IPrsServerDataInter getiPrsServerDataInter() {
		return iPrsServerDataInter;
	}

	public void setiPrsServerDataInter(IPrsServerDataInter iPrsServerDataInter) {
		this.iPrsServerDataInter = iPrsServerDataInter;
	}
}
