package com.ttmv.monitoring.chartService.impl.php;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QuerySelectedIp;
import com.ttmv.monitoring.entity.PHPServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IPHPServerDataInter;

public class QueryPHPSelectedIpServiceImpl extends QuerySelectedIp {

	private static Logger logger = LogManager.getLogger(QueryPHPSelectedIpServiceImpl.class);

	private IPHPServerDataInter iPHPServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		return iPHPServerDataInter.querySelectedPHPServerData((DataBeanQuery)obj);
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new PHPServerData();
	}


	@Override
	protected void addAttributesToQuery(Map reqMap, DataBeanQuery query) {

	}

	public IPHPServerDataInter getiPHPServerDataInter() {
		return iPHPServerDataInter;
	}

	public void setiPHPServerDataInter(IPHPServerDataInter iPHPServerDataInter) {
		this.iPHPServerDataInter = iPHPServerDataInter;
	}
}
