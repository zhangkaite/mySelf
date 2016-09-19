package com.ttmv.monitoring.chartService.impl.mf;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryListDate;
import com.ttmv.monitoring.entity.MediaForwardData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IMediaForwardDataInter;

/**
 * CPU动态折线图
 * @author wll
 *
 */
public class QueryListMediaForwardDataServiceImpl extends QueryListDate {

	private static Logger logger = LogManager.getLogger(QueryListMediaForwardDataServiceImpl.class);
	
	private IMediaForwardDataInter iMediaForwardDataInter;

	/**
	 * 创建查询对象
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	protected List getQuery(Object obj) throws Exception {
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		return iMediaForwardDataInter.queryListMediaForwardDataByIpAndServerTypeAndPort((DataBeanQuery)obj);
	}

	@Override
	protected Object getDataType() {
		return new MediaForwardData();
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected void addAttributesToQuery(Map reqMap, DataBeanQuery query) {

	}

	public IMediaForwardDataInter getiMediaForwardDataInter() {
		return iMediaForwardDataInter;
	}

	public void setiMediaForwardDataInter(
			IMediaForwardDataInter iMediaForwardDataInter) {
		this.iMediaForwardDataInter = iMediaForwardDataInter;
	}
}
