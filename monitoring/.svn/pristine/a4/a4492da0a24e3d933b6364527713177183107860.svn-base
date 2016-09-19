package com.ttmv.monitoring.chartService.impl.mn;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryListByDate;
import com.ttmv.monitoring.entity.MediaControlData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IMediaControlDataInter;

/**
 * CPU动态折线图
 * @author wll
 *
 */
public class QueryListByDateMNServiceImpl  extends QueryListByDate {


	private static Logger logger = LogManager.getLogger(QueryListByDateMNServiceImpl.class);

	private IMediaControlDataInter iMediaControlDataInter;

	/**
	 * 对查询对象增加属性
	 * @return
	 * @throws Exception
	 */
	@Override
	protected void addAttributesToQuery(Map reqMap,DataBeanQuery query) {
	
	}

	@Override
	protected List getQuery(Object obj) throws Exception {
		return iMediaControlDataInter.queryListOnDateMediaControlDataByIpAndServerTypeAndPort((DataBeanQuery)obj);
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new MediaControlData();
	}

	public IMediaControlDataInter getiMediaControlDataInter() {
		return iMediaControlDataInter;
	}

	public void setiMediaControlDataInter(
			IMediaControlDataInter iMediaControlDataInter) {
		this.iMediaControlDataInter = iMediaControlDataInter;
	}
}
