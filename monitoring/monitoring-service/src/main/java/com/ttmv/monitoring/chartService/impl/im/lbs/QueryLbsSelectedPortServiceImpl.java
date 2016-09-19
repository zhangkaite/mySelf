package com.ttmv.monitoring.chartService.impl.im.lbs;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QuerySelectedPort;
import com.ttmv.monitoring.entity.LbsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.ILbsServerDataInter;

public class QueryLbsSelectedPortServiceImpl extends QuerySelectedPort {

	private static Logger logger = LogManager.getLogger(QueryLbsSelectedPortServiceImpl.class);

	private ILbsServerDataInter iLbsServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		return iLbsServerDataInter.querySelectedLbsServerData((DataBeanQuery)obj);
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
	protected void addAttributesToQuery(Map reqMap, DataBeanQuery query) {

	}

	public ILbsServerDataInter getiLbsServerDataInter() {
		return iLbsServerDataInter;
	}

	public void setiLbsServerDataInter(ILbsServerDataInter iLbsServerDataInter) {
		this.iLbsServerDataInter = iLbsServerDataInter;
	}
}
