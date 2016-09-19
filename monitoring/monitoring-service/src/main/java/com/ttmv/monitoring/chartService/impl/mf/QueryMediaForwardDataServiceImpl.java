package com.ttmv.monitoring.chartService.impl.mf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryData;
import com.ttmv.monitoring.entity.MediaForwardData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IMediaForwardDataInter;

/**
 * CPU动态折线图
 * @author wll
 *
 */
public class QueryMediaForwardDataServiceImpl  extends QueryData {

	private static Logger logger = LogManager.getLogger(QueryMediaForwardDataServiceImpl.class);
	
	private IMediaForwardDataInter iMediaForwardDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		List list = new ArrayList();
		list.add(iMediaForwardDataInter.queryMediaForwardDataByIpAndServerTypeAndPort((DataBeanQuery)obj));
		return list;
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new MediaForwardData();
	}

	@Override
	protected void addAttributesToQuery(Map reqMap, Object data) {

	}

	public IMediaForwardDataInter getiMediaForwardDataInter() {
		return iMediaForwardDataInter;
	}

	public void setiMediaForwardDataInter(
			IMediaForwardDataInter iMediaForwardDataInter) {
		this.iMediaForwardDataInter = iMediaForwardDataInter;
	}
}
