package com.ttmv.monitoring.chartService.impl.im.mds;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QuerySelectedPort;
import com.ttmv.monitoring.entity.MdsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IMdsServerDataInter;

public class QueryMdsSelectedPortServiceImpl extends QuerySelectedPort {

	private static Logger logger = LogManager.getLogger(QueryMdsSelectedPortServiceImpl.class);

	private IMdsServerDataInter iMdsServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		return iMdsServerDataInter.querySelectedMdsServerData((DataBeanQuery)obj);
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
	protected void addAttributesToQuery(Map reqMap, DataBeanQuery query) {

	}

	public IMdsServerDataInter getiMdsServerDataInter() {
		return iMdsServerDataInter;
	}

	public void setiMdsServerDataInter(IMdsServerDataInter iMdsServerDataInter) {
		this.iMdsServerDataInter = iMdsServerDataInter;
	}
}
