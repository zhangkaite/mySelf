package com.ttmv.monitoring.chartService.impl.im.tas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryData;
import com.ttmv.monitoring.entity.TasServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.ITasServerDataInter;

public class QueryTasServerDataServiceImpl  extends QueryData{

	private static Logger logger = LogManager.getLogger(QueryTasServerDataServiceImpl.class);

	private ITasServerDataInter iTasServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		List list = new ArrayList();
		list.add(iTasServerDataInter.queryTasServerDataByIpAndServerTypeAndPort((DataBeanQuery)obj));
		return list;
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new TasServerData();
	}

	@Override
	protected void addAttributesToQuery(Map reqMap, Object data) {

	}

	public ITasServerDataInter getiTasServerDataInter() {
		return iTasServerDataInter;
	}

	public void setiTasServerDataInter(ITasServerDataInter iTasServerDataInter) {
		this.iTasServerDataInter = iTasServerDataInter;
	}
}
