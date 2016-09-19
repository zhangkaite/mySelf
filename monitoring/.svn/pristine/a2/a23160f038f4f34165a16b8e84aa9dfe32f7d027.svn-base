package com.ttmv.monitoring.chartService.impl.php;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryListByDate;
import com.ttmv.monitoring.chartService.tools.constant.ChartConstant;
import com.ttmv.monitoring.chartService.tools.util.DateUtil;
import com.ttmv.monitoring.entity.PHPServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IPHPServerDataInter;

/**
 * PHP直播动态折线图预置数据查询
 * @author wll
 *
 */
public class QueryListByDatePHPServiceImpl extends QueryListByDate {

	private static final Integer PRESET_TIME = -1;
	private static Logger logger = LogManager.getLogger(QueryListByDatePHPServiceImpl.class);

	private IPHPServerDataInter iPHPServerDataInter;


	/**
	 * 创建查询对象
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	protected List getQuery(Object obj) throws Exception {
		if( obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空。");
		List<PHPServerData> result = iPHPServerDataInter.queryListOnDatePHPServerDataByIpAndServerTypeAndPort((DataBeanQuery) obj);
		Date currDate =(Date)getReqMap().get("time");
		List list = dealData(result, currDate);
		return list;
	}

	/**
	 * 对查询对象增加属性
	 * @return
	 * @throws Exception
	 */
	@Override
	protected void addAttributesToQuery(Map reqMap,DataBeanQuery query){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		query.setCurentTime(sdf.format(new Date()));
		/* 查询前一小时 */
		query.setPreviousTime(sdf.format(DateUtil.getQueryTime(ChartConstant.DATE_HOUR, PRESET_TIME)));
	}

	/**
	 * 返回数据的类型，用于做数据类型判断
	 * @return
	 */
	@Override
	protected Object getDataType(){
		return new PHPServerData();
	}

	/**
	 * 数据检查白名单
	 * @return
	 */
	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	public IPHPServerDataInter getiPHPServerDataInter() {
		return iPHPServerDataInter;
	}

	public void setiPHPServerDataInter(IPHPServerDataInter iPHPServerDataInter) {
		this.iPHPServerDataInter = iPHPServerDataInter;
	}
}
