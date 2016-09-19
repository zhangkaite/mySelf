package com.ttmv.monitoring.chartService.impl.im.mss;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QuerySelectedIp;
import com.ttmv.monitoring.entity.MssServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IMssServerDataInter;

public class QueryMssSelectedIpServiceImpl extends QuerySelectedIp {

	private static Logger logger = LogManager.getLogger(QueryMssSelectedIpServiceImpl.class);

	private IMssServerDataInter iMssServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		return iMssServerDataInter.querySelectedMssServerData((DataBeanQuery)obj);
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
	protected void addAttributesToQuery(Map reqMap, DataBeanQuery query) {

	}

	public IMssServerDataInter getiMssServerDataInter() {
		return iMssServerDataInter;
	}

	public void setiMssServerDataInter(IMssServerDataInter iMssServerDataInter) {
		this.iMssServerDataInter = iMssServerDataInter;
	}
}
