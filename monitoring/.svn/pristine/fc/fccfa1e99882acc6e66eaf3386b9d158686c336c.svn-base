package com.ttmv.monitoring.webService.impl.configuration.threshold;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.ThresholdInfo;
import com.ttmv.monitoring.inter.IThresholdInfoInter;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.webService.WebServerInf;
import com.ttmv.monitoring.webService.entity.MonitoringInitBean;
import com.ttmv.monitoring.webService.impl.configuration.alerter.AddAlerterServiceImpl;
import com.ttmv.monitoring.webService.tools.ResponseTool;


/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月23日20:26:49
 * @explain : 根据系统类型修改阀值
 */
@SuppressWarnings({ "rawtypes","unused","unchecked"})
public class ModifyThresholdServiceImpl  implements WebServerInf{
	
	private static Logger logger = LogManager.getLogger(ModifyThresholdServiceImpl.class);
	
	private IThresholdInfoInter iThresholdInfoInter;
	private MonitoringInitBean monitoringInitBean;
	
	/**
	 * 业务处理
	 * @param reqMap
	 * @return Map
	 */
	public Map handler(Map reqMap) {
		logger.info("[ModifyThresholdServiceImpl] @ start...");
		//请求数据校验
		try {
			checkData(reqMap);
		} catch (Exception e) {
			logger.error("数据校验失败!!!" + e.getMessage());
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
		}
		
		try {
			iThresholdInfoInter.updateListThresholdInfo(reqMap);
		} catch (Exception e) {
			logger.error("修改阀值失败!!!");
			return ResponseTool.returnError();
		}
		//阀值修改后重新加载阀值
		monitoringInitBean.initMonitoringInitBean();
		logger.info("阀值修改成功!!!");
		return ResponseTool.returnSuccess();
	}
	
	/**
	 * 业务数据校验(非空，数据规格)
	 * @param reqMap
	 * @return void
	 * @exception Exception
	 */
	public void checkData(Map reqMap) throws Exception {
		if(reqMap.get("threshold_type") == null || "".equals(reqMap.get("threshold_type"))){
			throw new Exception("[ModifyThreshold_[threshold_type] is null...]");
		}
		if(reqMap.get("alerterID") == null || "".equals(reqMap.get("alerterID"))){
			throw new Exception("[ModifyThreshold_[alerterID] is null...]");
		}
		if(reqMap.get("params") == null || "".equals(reqMap.get("params"))){
			throw new Exception("[ModifyThreshold_[params] is null...]");
		}
		
	}

	public IThresholdInfoInter getiThresholdInfoInter() {
		return iThresholdInfoInter;
	}

	public void setiThresholdInfoInter(IThresholdInfoInter iThresholdInfoInter) {
		this.iThresholdInfoInter = iThresholdInfoInter;
	}

	public MonitoringInitBean getMonitoringInitBean() {
		return monitoringInitBean;
	}

	public void setMonitoringInitBean(MonitoringInitBean monitoringInitBean) {
		this.monitoringInitBean = monitoringInitBean;
	}
	
	
	
	
	
}
