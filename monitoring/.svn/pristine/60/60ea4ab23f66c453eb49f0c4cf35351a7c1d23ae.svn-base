package com.ttmv.monitoring.chartService.impl.php;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryListDate;
import com.ttmv.monitoring.entity.PHPServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IPHPServerDataInter;

/**
 * PHP直播动态折线图
 * @author wll
 *
 */
public class QueryListPHPServiceImpl extends QueryListDate {

	private static Logger logger = LogManager.getLogger(QueryListPHPServiceImpl.class);

	private IPHPServerDataInter iPHPServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		return iPHPServerDataInter.queryListPHPServerDataByIpAndServerTypeAndPort((DataBeanQuery)obj);
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new PHPServerData();
	}

	@Override
	protected void addAttributesToQuery(Map reqMap, DataBeanQuery query) {

	}

	public IPHPServerDataInter getiPHPServerDataInter() {
		return iPHPServerDataInter;
	}

	public void setiPHPServerDataInter(IPHPServerDataInter iPHPServerDataInter) {
		this.iPHPServerDataInter = iPHPServerDataInter;
	}
}
