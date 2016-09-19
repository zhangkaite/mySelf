package com.ttmv.monitoring.chartService.impl.im.mss;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryData;
import com.ttmv.monitoring.entity.MssServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IMssServerDataInter;

public class QueryMssServerDataServiceImpl  extends QueryData{

	private static Logger logger = LogManager.getLogger(QueryMssServerDataServiceImpl.class);

	private IMssServerDataInter iMssServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		List list = new ArrayList();
		list.add(iMssServerDataInter.queryMssServerDataByIpAndServerTypeAndPort((DataBeanQuery)obj));
		return list;
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new MssServerData();
	}

	@Override
	protected void addAttributesToQuery(Map reqMap, Object data) {

	}

	public IMssServerDataInter getiMssServerDataInter() {
		return iMssServerDataInter;
	}

	public void setiMssServerDataInter(IMssServerDataInter iMssServerDataInter) {
		this.iMssServerDataInter = iMssServerDataInter;
	}
}
