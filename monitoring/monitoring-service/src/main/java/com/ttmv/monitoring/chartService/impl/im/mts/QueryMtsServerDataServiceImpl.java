package com.ttmv.monitoring.chartService.impl.im.mts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryData;
import com.ttmv.monitoring.entity.MtsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IMtsServerDataInter;

public class QueryMtsServerDataServiceImpl  extends QueryData{

	private static Logger logger = LogManager.getLogger(QueryMtsServerDataServiceImpl.class);

	private IMtsServerDataInter iMtsServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		List list = new ArrayList();
		list.add(iMtsServerDataInter.queryMtsServerDataByIpAndServerTypeAndPort((DataBeanQuery)obj));
		return list;
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new MtsServerData();
	}

	@Override
	protected void addAttributesToQuery(Map reqMap, Object data) {

	}

	public IMtsServerDataInter getiMtsServerDataInter() {
		return iMtsServerDataInter;
	}

	public void setiMtsServerDataInter(IMtsServerDataInter iMtsServerDataInter) {
		this.iMtsServerDataInter = iMtsServerDataInter;
	}
}
