package com.ttmv.monitoring.chartService.impl.php;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QuerySelectedIp;
import com.ttmv.monitoring.entity.PhpManagerServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IPHPManagerServerDataInter;

public class QueryPhpManagerSelectedIpServiceImpl extends QuerySelectedIp {

	private static Logger logger = LogManager.getLogger(QueryPhpManagerSelectedIpServiceImpl.class);

	private IPHPManagerServerDataInter iPHPManagerServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		return iPHPManagerServerDataInter.querySelectedPhpManagerServerData((DataBeanQuery)obj);
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new PhpManagerServerData();
	}


	@Override
	protected void addAttributesToQuery(Map reqMap, DataBeanQuery query) {

	}

	public IPHPManagerServerDataInter getiPHPManagerServerDataInter() {
		return iPHPManagerServerDataInter;
	}

	public void setiPHPManagerServerDataInter(IPHPManagerServerDataInter iPHPManagerServerDataInter) {
		this.iPHPManagerServerDataInter = iPHPManagerServerDataInter;
	}
}
