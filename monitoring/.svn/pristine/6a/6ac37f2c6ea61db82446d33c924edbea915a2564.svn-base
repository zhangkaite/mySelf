package com.ttmv.monitoring.webService.impl.record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.AlertRecordInfo;
import com.ttmv.monitoring.entity.page.Page;
import com.ttmv.monitoring.entity.querybean.AlertRecordInfoQuery;
import com.ttmv.monitoring.inter.IAlertRecordInfoInter;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.webService.WebServerInf;
import com.ttmv.monitoring.webService.tools.ResponseTool;


/**
 * @author Damon_Zs
 * @version 创建时间：2015年10月16日15:27:27
 * @explain : 查询报警记录
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryAlertRecordServiceImpl implements WebServerInf{
	
	private static Logger logger = LogManager.getLogger(QueryAlertRecordServiceImpl.class);
	
	private IAlertRecordInfoInter iAlertRecordInfoInter ;

	public Map handler(Map reqMap) {
		logger.info("[QueryAlertRecordServiceImpl] @ start...");
		//请求数据校验
		try {
			checkData(reqMap);
		} catch (Exception e) {
			logger.error("数据校验失败!!!" + e.getMessage());
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
		}
		//创建查询对象
		AlertRecordInfoQuery alertRecordInfoQuery = new AlertRecordInfoQuery();
		alertRecordInfoQuery.setPage(Integer.parseInt(reqMap.get("page").toString()));
		alertRecordInfoQuery.setPageSize(Integer.parseInt(reqMap.get("pageSize").toString()));
		if(reqMap.get("serverType") != null){
			alertRecordInfoQuery.setServerType(reqMap.get("serverType").toString());
		}
		if(reqMap.get("startTime") != null){
			alertRecordInfoQuery.setStartTime(reqMap.get("startTime").toString());
		}
		if(reqMap.get("endTime") != null){
			alertRecordInfoQuery.setEndTime(reqMap.get("endTime").toString());
		}
		if (reqMap.get("alertType")!=null) {
			alertRecordInfoQuery.setAlertType(reqMap.get("alertType").toString());
		}
		
		//创建查询对象
		Page pages = null;
		try {
			pages =  iAlertRecordInfoInter.queryPageAlertRecordInfo(alertRecordInfoQuery);
		} catch (Exception e) {
			logger.error("报警历史记录查询处理异常!!!" , e);
			return ResponseTool.returnError();
		}
		logger.info("[报警历史记录列表查询成功!!!]");
		return ResponseTool.returnSuccess(takeResData(pages));
	}
	
	/**
	 * 组装返回数据
	 * @param pages
	 * @param userInfo
	 * @return 
	 */
	private Map takeResData(Page pages){
		Map resData = new HashMap();
		if (null==pages) {
			return null;
		}
		resData.put("dataSum", pages.getSum());//总条数
		List<AlertRecordInfo> alertRecordInfos = pages.getData();
		logger.info("查询记录数：" +  alertRecordInfos.size());
		List alertRecords = new ArrayList();
		for(int i = 0; i < alertRecordInfos.size();i++){
			Map map = new HashMap();
			map.put("alertID", alertRecordInfos.get(i).getId());
			map.put("alertTime", alertRecordInfos.get(i).getAlertTime());
			map.put("serverType", alertRecordInfos.get(i).getServerType());
			map.put("IP", alertRecordInfos.get(i).getIp());
			map.put("alertMsg", alertRecordInfos.get(i).getAlertMsg());
			map.put("alertType", alertRecordInfos.get(i).getAlertType());
			map.put("serverID", alertRecordInfos.get(i).getServerId());
			alertRecords.add(map);
		}
		resData.put("alerts", alertRecords);
		return resData;
	}
	

	public void checkData(Map reqMap) throws Exception {
		if(reqMap.get("page") == null || "".equals(reqMap.get("page").toString())){
			throw new Exception("[QueryAlertRecord_[page] is null...]");
		}
		if(reqMap.get("pageSize") == null || "".equals(reqMap.get("pageSize").toString())){
			throw new Exception("[QueryAlertRecord_[pageSize] is null...]");
		}
		
	}

	public IAlertRecordInfoInter getiAlertRecordInfoInter() {
		return iAlertRecordInfoInter;
	}

	public void setiAlertRecordInfoInter(IAlertRecordInfoInter iAlertRecordInfoInter) {
		this.iAlertRecordInfoInter = iAlertRecordInfoInter;
	}


	
	

}
