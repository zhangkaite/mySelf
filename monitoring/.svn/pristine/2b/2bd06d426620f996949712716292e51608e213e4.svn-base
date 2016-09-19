package com.ttmv.monitoring.chartService.impl.mn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryData;
import com.ttmv.monitoring.entity.MediaControlData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IMediaControlDataInter;

/**
 * CPU动态折线图
 * @author wll
 *
 */
public class QueryMediaControlDataServiceImpl  extends QueryData {

	private static Logger logger = LogManager.getLogger(QueryMediaControlDataServiceImpl.class);

	private IMediaControlDataInter iMediaControlDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		List list = new ArrayList();
		list.add(iMediaControlDataInter.queryMediaControlDataByIpAndServerTypeAndPort((DataBeanQuery)obj));
		return list;
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new MediaControlData();
	}

	@Override
	protected void addAttributesToQuery(Map reqMap, Object data) {

	}

	public IMediaControlDataInter getiMediaControlDataInter() {
		return iMediaControlDataInter;
	}

	public void setiMediaControlDataInter(
			IMediaControlDataInter iMediaControlDataInter) {
		this.iMediaControlDataInter = iMediaControlDataInter;
	}
}
