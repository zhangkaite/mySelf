package com.ttmv.monitoring.chartService.impl.im.tas;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QuerySelectedIp;
import com.ttmv.monitoring.entity.TasServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.ITasServerDataInter;

public class QueryTasSelectedIpServiceImpl extends QuerySelectedIp {

	private static Logger logger = LogManager.getLogger(QueryTasSelectedIpServiceImpl.class);

	private ITasServerDataInter iTasServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		return iTasServerDataInter.querySelectedTasServerData((DataBeanQuery)obj);
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
	protected void addAttributesToQuery(Map reqMap, DataBeanQuery query) {

	}

	public ITasServerDataInter getiTasServerDataInter() {
		return iTasServerDataInter;
	}

	public void setiTasServerDataInter(ITasServerDataInter iTasServerDataInter) {
		this.iTasServerDataInter = iTasServerDataInter;
	}
}
