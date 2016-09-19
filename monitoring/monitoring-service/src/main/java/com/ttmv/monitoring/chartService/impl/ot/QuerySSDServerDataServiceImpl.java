package com.ttmv.monitoring.chartService.impl.ot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryData;
import com.ttmv.monitoring.entity.ScreenShotData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IScreenShotDataInter;

public class QuerySSDServerDataServiceImpl  extends QueryData {

	private static Logger logger = LogManager.getLogger(QuerySSDServerDataServiceImpl.class);

	private IScreenShotDataInter iScreenShotDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		List list = new ArrayList();
		list.add(iScreenShotDataInter.queryScreenShotDataByIpAndServerTypeAndPort((DataBeanQuery)obj));
		return list;
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new ScreenShotData();
	}

	protected Object getResData(List result) throws Exception {
		if (result == null || result.size() < 1)
			throw new Exception("执行查询时，传递参数为空.");
		Object obj = result.get(0);
		Map map = dataTOResultObject(obj);
		return map;
	}

	@Override
	protected void addAttributesToQuery(Map reqMap, Object data) {

	}

	public IScreenShotDataInter getiScreenShotDataInter() {
		return iScreenShotDataInter;
	}

	public void setiScreenShotDataInter(IScreenShotDataInter iScreenShotDataInter) {
		this.iScreenShotDataInter = iScreenShotDataInter;
	}
}
