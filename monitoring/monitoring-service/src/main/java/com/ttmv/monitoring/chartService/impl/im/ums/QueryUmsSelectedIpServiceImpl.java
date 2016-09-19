package com.ttmv.monitoring.chartService.impl.im.ums;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QuerySelectedIp;
import com.ttmv.monitoring.entity.UmsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IUmsServerDataInter;

public class QueryUmsSelectedIpServiceImpl extends QuerySelectedIp {

	private static Logger logger = LogManager.getLogger(QueryUmsSelectedIpServiceImpl.class);

	private IUmsServerDataInter iUmsServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		return iUmsServerDataInter.querySelectedUmsServerData((DataBeanQuery)obj);
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
	protected void addAttributesToQuery(Map reqMap, DataBeanQuery query) {

	}

	public IUmsServerDataInter getiUmsServerDataInter() {
		return iUmsServerDataInter;
	}

	public void setiUmsServerDataInter(IUmsServerDataInter iUmsServerDataInter) {
		this.iUmsServerDataInter = iUmsServerDataInter;
	}
}
