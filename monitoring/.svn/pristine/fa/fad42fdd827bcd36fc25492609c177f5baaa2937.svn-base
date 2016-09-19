package com.ttmv.monitoring.chartService.impl.im.mds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryData;
import com.ttmv.monitoring.entity.MdsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IMdsServerDataInter;

public class QueryMdsServerDataServiceImpl  extends QueryData{

	private static Logger logger = LogManager.getLogger(QueryMdsServerDataServiceImpl.class);

	private IMdsServerDataInter iMdsServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		List list = new ArrayList();
		list.add(iMdsServerDataInter.queryMdsServerDataByIpAndServerTypeAndPort((DataBeanQuery)obj));
		return list;
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new MdsServerData();
	}

	@Override
	protected void addAttributesToQuery(Map reqMap, Object data) {

	}

	public IMdsServerDataInter getiMdsServerDataInter() {
		return iMdsServerDataInter;
	}

	public void setiMdsServerDataInter(IMdsServerDataInter iMdsServerDataInter) {
		this.iMdsServerDataInter = iMdsServerDataInter;
	}
}
