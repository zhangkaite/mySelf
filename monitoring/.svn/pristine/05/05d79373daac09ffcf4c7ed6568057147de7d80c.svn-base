package com.ttmv.monitoring.webService.impl.configuration.threshold;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.ThresholdInfo;
import com.ttmv.monitoring.inter.IThresholdInfoInter;
import com.ttmv.monitoring.webService.WebServerInf;
import com.ttmv.monitoring.webService.tools.ResponseTool;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月23日20:26:49
 * @explain : 根据系统类型查询系统阀值
 */
@SuppressWarnings({ "rawtypes"})
public class QueryAllThresholdServiceImpl implements WebServerInf{
	
	private static Logger logger = LogManager.getLogger(QueryAllThresholdServiceImpl.class);

	private IThresholdInfoInter iThresholdInfoInter;
	
	/**
	 * 业务处理
	 * @param reqMap
	 * @return Map
	 */
	public Map handler(Map reqMap) {
		logger.info("[QueryThresholdByTypeServiceImpl] @ start...");
		List<ThresholdInfo> thresholdInfos = null;
		try {
			thresholdInfos = iThresholdInfoInter.queryAllThresholdInfo();
		} catch (Exception e) {
			logger.error("查询阀值失败!!!");
			return ResponseTool.returnError();
		}
		//拼接返回数据
		return ResponseTool.returnSuccess(thresholdInfos);
	}
	
	public IThresholdInfoInter getiThresholdInfoInter() {
		return iThresholdInfoInter;
	}

	public void setiThresholdInfoInter(IThresholdInfoInter iThresholdInfoInter) {
		this.iThresholdInfoInter = iThresholdInfoInter;
	}

	public void checkData(Map reqMap) throws Exception {
		
	}
}
