package com.ttmv.monitoring.interfaceService.impl.alert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.AlertRecordInfo;
import com.ttmv.monitoring.entity.ThresholdInfo;
import com.ttmv.monitoring.inter.IAlertRecordInfoInter;
import com.ttmv.monitoring.msgNotification.MessageServiceInf;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.webService.entity.MonitoringInitBean;
import com.ttmv.monitoring.webService.tools.ResponseTool;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月17日18:53:41
 * @explain : 直接告警服务接口
 */
@SuppressWarnings({ "rawtypes","unchecked" })
public class WarningServiceImpl {
	
	private static Logger logger = LogManager.getLogger(WarningServiceImpl.class);
	
	private MessageServiceInf messageServiceInf;
	
	private IAlertRecordInfoInter iAlertRecordInfoInter;
	
	@Resource(name = "monitoringInitBean")
	private MonitoringInitBean monitoringInitBean;

	public Map handler(Map reqMap){
		 Map<String, ThresholdInfo> map = monitoringInitBean.getThresholdMap();
		 String name = reqMap.get("ServerType")+"_"+"CPU";
		 ThresholdInfo t = map.get(name);
		 t.getThresholdAlerterIds();
		 
		logger.info("[WarningServiceImpl] @ start...");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//1.数据校验
		try {
			checkData(reqMap);
		} catch (Exception e) {
			logger.error("数据校验失败!!!" + e.getMessage());
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
		}
		
		//2、调用报警接口（短信、邮件）
		Map params = new HashMap();
		params.put("msgType", "FATALERROR");
		params.put("ip", reqMap.get("IP").toString());
		params.put("serverName", reqMap.get("ServerType").toString());
		params.put("serverID", reqMap.get("serverId").toString());
		
		params.put("alerterID", t.getThresholdAlerterIds());
		 
		
		try {
			params.put("serverTime", sdf.parse(reqMap.get("alertTime").toString()));
		} catch (ParseException e2) {
			logger.warn("时间转换错误！！！" + e2.getMessage());
		}
		params.put("errorMsg", reqMap.get("errorMsg").toString());
		try {
			messageServiceInf.sendMessage(params);
		} catch (Exception e) {
			logger.warn("报警服务调用失败！！！" ,e);
		}
		
		//3、报警历史对象组装
		AlertRecordInfo alertRecordInfo = new AlertRecordInfo();
		alertRecordInfo.setServerType(reqMap.get("ServerType").toString());
		alertRecordInfo.setAlertMsg(reqMap.get("errorMsg").toString());
		try {
			alertRecordInfo.setAlertTime(sdf.parse(reqMap.get("alertTime").toString()));
			alertRecordInfo.setIp(reqMap.get("IP").toString());
		} catch (ParseException e1) {
			logger.error("时间转换错误！！！" + e1.getMessage());
		}
		alertRecordInfo.setAlertType(reqMap.get("type").toString());
		alertRecordInfo.setServerId(reqMap.get("serverId").toString());
		//4、数据入库
		try {
			iAlertRecordInfoInter.addAlertRecordInfo(alertRecordInfo);
		} catch (Exception e) {
			logger.error("直接报警信息入库失败！！！");
			return ResponseTool.returnError();
		}
		
		return ResponseTool.returnSuccess();
	}
	
	/**
	 * 业务数据校验(非空，数据规格)
	 * @param reqMap
	 * @return void
	 * @exception Exception
	 */
	public void checkData(Map reqMap) throws Exception{
		if(reqMap.get("ServerType") == null || "".equals(reqMap.get("ServerType"))){
			throw new Exception("[WarningServiceImpl_[ServerType] is null...]");
		}
		if(reqMap.get("serverId") == null || "".equals(reqMap.get("serverId"))){
			throw new Exception("[WarningServiceImpl_[serverId] is null...]");
		}
		if(reqMap.get("IP") == null || "".equals(reqMap.get("IP"))){
			throw new Exception("[WarningServiceImpl_[IP] is null...]");
		}
		if(reqMap.get("alertTime") == null || "".equals(reqMap.get("alertTime"))){
			throw new Exception("[WarningServiceImpl_[alertTime] is null...]");
		}
		if(reqMap.get("type") == null || "".equals(reqMap.get("type"))){
			throw new Exception("[WarningServiceImpl_[type] is null...]");
		}
		if(reqMap.get("errorMsg") == null || "".equals(reqMap.get("errorMsg"))){
			throw new Exception("[WarningServiceImpl_[errorMsg] is null...]");
		}
		
	}
	
	
	
	


	public MessageServiceInf getMessageServiceInf() {
		return messageServiceInf;
	}

	public void setMessageServiceInf(MessageServiceInf messageServiceInf) {
		this.messageServiceInf = messageServiceInf;
	}

	public IAlertRecordInfoInter getiAlertRecordInfoInter() {
		return iAlertRecordInfoInter;
	}

	public void setiAlertRecordInfoInter(IAlertRecordInfoInter iAlertRecordInfoInter) {
		this.iAlertRecordInfoInter = iAlertRecordInfoInter;
	}
	
	
	

	

}
