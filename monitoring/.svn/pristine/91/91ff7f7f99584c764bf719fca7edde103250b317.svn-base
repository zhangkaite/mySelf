package com.ttmv.monitoring.chartService.impl.ot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryData;
import com.ttmv.monitoring.entity.TranscodingData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.ITranscodingDataInter;

public class QueryTDServerDataServiceImpl  extends QueryData {

	private static Logger logger = LogManager.getLogger(QueryTDServerDataServiceImpl.class);

	private ITranscodingDataInter iTranscodingDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		List list = new ArrayList();
		list.add(iTranscodingDataInter.queryTranscodingDataByIpAndServerTypeAndPort((DataBeanQuery)obj));
		return list;
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new TranscodingData();
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

	public ITranscodingDataInter getiTranscodingDataInter() {
		return iTranscodingDataInter;
	}

	public void setiTranscodingDataInter(ITranscodingDataInter iTranscodingDataInter) {
		this.iTranscodingDataInter = iTranscodingDataInter;
	}
}
