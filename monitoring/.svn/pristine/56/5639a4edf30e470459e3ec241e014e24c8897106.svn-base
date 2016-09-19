package com.ttmv.monitoring.chartService.impl.php;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryData;
import com.ttmv.monitoring.entity.PHPServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IPHPServerDataInter;

public class QueryPhpServerDataServiceImpl  extends QueryData{

	private static Logger logger = LogManager.getLogger(QueryPhpServerDataServiceImpl.class);

	private IPHPServerDataInter iPHPServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		List list = new ArrayList();
		list.add(iPHPServerDataInter.queryPHPServerDataByIpAndServerTypeAndPort((DataBeanQuery)obj));
		return list;
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
	protected void addAttributesToQuery(Map reqMap, Object data) {

	}

	public IPHPServerDataInter getiPHPServerDataInter() {
		return iPHPServerDataInter;
	}

	public void setiPHPServerDataInter(IPHPServerDataInter iPHPServerDataInter) {
		this.iPHPServerDataInter = iPHPServerDataInter;
	}
}
