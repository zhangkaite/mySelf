package com.ttmv.monitoring.chartService.impl.im.mts;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QuerySelectedPort;
import com.ttmv.monitoring.entity.MtsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IMtsServerDataInter;

public class QueryMtsSelectedPortServiceImpl extends QuerySelectedPort {

	private static Logger logger = LogManager.getLogger(QueryMtsSelectedPortServiceImpl.class);

	private IMtsServerDataInter iMtsServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		return iMtsServerDataInter.querySelectedMtsServerData((DataBeanQuery)obj);
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
	protected void addAttributesToQuery(Map reqMap, DataBeanQuery query) {

	}

	public IMtsServerDataInter getiMtsServerDataInter() {
		return iMtsServerDataInter;
	}

	public void setiMtsServerDataInter(IMtsServerDataInter iMtsServerDataInter) {
		this.iMtsServerDataInter = iMtsServerDataInter;
	}
}
