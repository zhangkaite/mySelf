package com.ttmv.monitoring.chartService.impl.php;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QuerySelectedIp;
import com.ttmv.monitoring.entity.PhpVideoServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IPHPVideoServerDataInter;

public class QueryVideoSelectedIpServiceImpl extends QuerySelectedIp {

	private static Logger logger = LogManager.getLogger(QueryVideoSelectedIpServiceImpl.class);

	private IPHPVideoServerDataInter iPHPVideoServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		return iPHPVideoServerDataInter.querySelectedPhpVideoServerData((DataBeanQuery)obj);
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new PhpVideoServerData();
	}


	@Override
	protected void addAttributesToQuery(Map reqMap, DataBeanQuery query) {

	}

	public IPHPVideoServerDataInter getiPHPVideoServerDataInter() {
		return iPHPVideoServerDataInter;
	}

	public void setiPHPVideoServerDataInter(IPHPVideoServerDataInter iPHPVideoServerDataInter) {
		this.iPHPVideoServerDataInter = iPHPVideoServerDataInter;
	}
}
