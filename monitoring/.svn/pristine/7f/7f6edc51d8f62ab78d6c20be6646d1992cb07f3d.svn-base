package com.ttmv.monitoring.chartService.impl.ot;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryListByDate;
import com.ttmv.monitoring.entity.ScreenShotData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IScreenShotDataInter;

/**
 * CPU动态折线图
 * @author wll
 *
 */
public class QueryListByDateSSDServiceImpl extends QueryListByDate {

	private static Logger logger = LogManager.getLogger(QueryListByDateSSDServiceImpl.class);

	private IScreenShotDataInter iScreenShotDataInter;
	

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
		List<ScreenShotData> result = iScreenShotDataInter.queryListOnDateScreenShotDataByIpAndServerTypeAndPort((DataBeanQuery)obj);
		Date currDate =(Date)getReqMap().get("time");
		List list = dealData(result,currDate);
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
		return new ScreenShotData();
	}

	public IScreenShotDataInter getiScreenShotDataInter() {
		return iScreenShotDataInter;
	}

	public void setiScreenShotDataInter(IScreenShotDataInter iScreenShotDataInter) {
		this.iScreenShotDataInter = iScreenShotDataInter;
	}
}
