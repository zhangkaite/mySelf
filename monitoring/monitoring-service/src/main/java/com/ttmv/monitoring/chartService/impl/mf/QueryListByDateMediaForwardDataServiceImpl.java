package com.ttmv.monitoring.chartService.impl.mf;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryListByDate;
import com.ttmv.monitoring.entity.MediaForwardData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IMediaForwardDataInter;

/**
 * CPU动态折线图
 * @author wll
 *
 */
public class QueryListByDateMediaForwardDataServiceImpl extends QueryListByDate {

	private static Logger logger = LogManager.getLogger(QueryListByDateMediaForwardDataServiceImpl.class);

	private IMediaForwardDataInter iMediaForwardDataInter;
	

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
		return iMediaForwardDataInter.queryListOnDateMediaForwardDataByIpAndServerTypeAndPort((DataBeanQuery)obj);
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new MediaForwardData();
	}

	public IMediaForwardDataInter getiMediaForwardDataInter() {
		return iMediaForwardDataInter;
	}

	public void setiMediaForwardDataInter(
			IMediaForwardDataInter iMediaForwardDataInter) {
		this.iMediaForwardDataInter = iMediaForwardDataInter;
	}
}
