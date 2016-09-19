package com.ttmv.monitoring.chartService.impl.ot;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QuerySelectedPort;
import com.ttmv.monitoring.entity.ScreenShotData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IScreenShotDataInter;

public class QuerySSDSelectedPortServiceImpl extends QuerySelectedPort {

	private static Logger logger = LogManager.getLogger(QuerySSDSelectedPortServiceImpl.class);

	private IScreenShotDataInter iScreenShotDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		return iScreenShotDataInter.querySelectedScreenShotData((DataBeanQuery)obj);
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new ScreenShotData();
	}

	@Override
	protected void addAttributesToQuery(Map reqMap, DataBeanQuery query) {

	}

	public IScreenShotDataInter getiScreenShotDataInter() {
		return iScreenShotDataInter;
	}

	public void setiScreenShotDataInter(IScreenShotDataInter iScreenShotDataInter) {
		this.iScreenShotDataInter = iScreenShotDataInter;
	}
}
