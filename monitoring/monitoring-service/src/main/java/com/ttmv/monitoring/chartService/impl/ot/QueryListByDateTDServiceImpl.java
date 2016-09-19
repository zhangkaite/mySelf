package com.ttmv.monitoring.chartService.impl.ot;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryListByDate;
import com.ttmv.monitoring.entity.TranscodingData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.ITranscodingDataInter;

/**
 * CPU动态折线图
 * @author wll
 *
 */
public class QueryListByDateTDServiceImpl extends QueryListByDate {

	private static Logger logger = LogManager.getLogger(QueryListByDateTDServiceImpl.class);

	private ITranscodingDataInter iTranscodingDataInter;

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
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		List<TranscodingData> 	result = iTranscodingDataInter.queryListOnDateTranscodingDataByIpAndServerTypeAndPort((DataBeanQuery)obj);
		Date currDate =(Date)getReqMap().get("time");
		List<TranscodingData> list = dealData(result, currDate);
		return list;
	}

	/**
	 * 数据检查白名单
	 * @return
	 */
	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new TranscodingData();
	}

	public ITranscodingDataInter getiTranscodingDataInter() {
		return iTranscodingDataInter;
	}

	public void setiTranscodingDataInter(ITranscodingDataInter iTranscodingDataInter) {
		this.iTranscodingDataInter = iTranscodingDataInter;
	}
}
