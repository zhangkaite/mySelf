package com.ttmv.monitoring.chartService.impl.im.hp;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QuerySelectedIp;
import com.ttmv.monitoring.entity.PHPServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IHpServerDataInter;

public class QueryHpSelectedIpServiceImpl extends QuerySelectedIp {

	private static Logger logger = LogManager.getLogger(QueryHpSelectedIpServiceImpl.class);

	private IHpServerDataInter iHpServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		return iHpServerDataInter.querySelectedHpServerData((DataBeanQuery)obj);
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

	public IHpServerDataInter getiHpServerDataInter() {
		return iHpServerDataInter;
	}

	public void setiHpServerDataInter(IHpServerDataInter iHpServerDataInter) {
		this.iHpServerDataInter = iHpServerDataInter;
	}
}
