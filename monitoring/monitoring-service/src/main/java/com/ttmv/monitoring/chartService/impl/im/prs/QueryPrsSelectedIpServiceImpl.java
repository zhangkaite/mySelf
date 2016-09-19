package com.ttmv.monitoring.chartService.impl.im.prs;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QuerySelectedIp;
import com.ttmv.monitoring.entity.PrsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IPrsServerDataInter;

public class QueryPrsSelectedIpServiceImpl extends QuerySelectedIp {

	private static Logger logger = LogManager.getLogger(QueryPrsSelectedIpServiceImpl.class);

	private IPrsServerDataInter iPrsServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		return iPrsServerDataInter.querySelectedPrsServerData((DataBeanQuery)obj);
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
	protected void addAttributesToQuery(Map reqMap, DataBeanQuery query) {

	}

	public IPrsServerDataInter getiPrsServerDataInter() {
		return iPrsServerDataInter;
	}

	public void setiPrsServerDataInter(IPrsServerDataInter iPrsServerDataInter) {
		this.iPrsServerDataInter = iPrsServerDataInter;
	}
}
