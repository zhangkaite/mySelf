package com.ttmv.monitoring.webService.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.alerterService.bean.AlerterLogMessage;
import com.ttmv.monitoring.entity.ThresholdInfo;
import com.ttmv.monitoring.inter.IThresholdInfoInter;

@SuppressWarnings({"unchecked","rawtypes"})
public class MonitoringInitBean {
	private static Logger logger = LogManager.getLogger(MonitoringInitBean.class);
	
	private IThresholdInfoInter iThresholdInfoInter;
	
	/**
	 * 阀值bean
	 */
	public Map<String, ThresholdInfo> thresholdMap = new HashMap();
	
	/**
	 * --------------最新监控数据上报记录-----------------
	 */
	public Map<String,Object> newDataMap = new ConcurrentHashMap<String, Object>() ;
	
	/**
	 * 报警记录（用于判断是否重复报警）
	 */
	public Map<String,AlerterLogMessage> monitoringRecord = new ConcurrentHashMap<String, AlerterLogMessage>();
	
	public void initMonitoringInitBean(){
		List<ThresholdInfo> ls = null;
		try {
			ls = iThresholdInfoInter.queryAllThresholdInfo();
		} catch (Exception e) {
			logger.error("初始化阀值异常!!!" , e);
			e.printStackTrace();
		}
		if(ls!=null && ls.size()>0){
			for (int i = 0; i < ls.size(); i++) {
				thresholdMap.put(ls.get(i).getThresholdType()+"_"+ls.get(i).getThresholdName(),
						(ThresholdInfo)ls.get(i));
			}
		}else{
			logger.warn("没有设置阀值!!!");
		}
	}

	public IThresholdInfoInter getiThresholdInfoInter() {
		return iThresholdInfoInter;
	}

	public void setiThresholdInfoInter(IThresholdInfoInter iThresholdInfoInter) {
		this.iThresholdInfoInter = iThresholdInfoInter;
	}

	public Map<String, ThresholdInfo> getThresholdMap() {
		return thresholdMap;
	}

	public void setThresholdMap(Map<String, ThresholdInfo> thresholdMap) {
		this.thresholdMap = thresholdMap;
	}

	public Map<String, Object> getNewDataMap() {
		return newDataMap;
	}

	public void setNewDataMap(Map<String, Object> newDataMap) {
		this.newDataMap = newDataMap;
	}

	public Map<String, AlerterLogMessage> getMonitoringRecord() {
		return monitoringRecord;
	}

	public void setMonitoringRecord(Map<String, AlerterLogMessage> monitoringRecord) {
		this.monitoringRecord = monitoringRecord;
	}


	
	
	
	

	
	
	
	
	


	
	
	
}
