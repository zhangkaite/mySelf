package com.ttmv.monitoring.chartService.impl.im.hp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryData;
import com.ttmv.monitoring.entity.HpServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IHpServerDataInter;

public class QueryHpServerDataServiceImpl  extends QueryData{

	private static Logger logger = LogManager.getLogger(QueryHpServerDataServiceImpl.class);

	private IHpServerDataInter iHpServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		List list = new ArrayList();
		list.add(iHpServerDataInter.queryHpServerDataByIpAndServerTypeAndPort((DataBeanQuery)obj));
		return list;
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new HpServerData();
	}

	@Override
	protected void addAttributesToQuery(Map reqMap, Object data) {

	}

	public IHpServerDataInter getiHpServerDataInter() {
		return iHpServerDataInter;
	}

	public void setiHpServerDataInter(IHpServerDataInter iHpServerDataInter) {
		this.iHpServerDataInter = iHpServerDataInter;
	}
}
