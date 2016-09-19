package com.ttmv.monitoring.webService.impl.configuration.alerter;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.AlerterInfo;
import com.ttmv.monitoring.entity.UserInfo;
import com.ttmv.monitoring.entity.tmp.AlerterInfoTmp;
import com.ttmv.monitoring.inter.IAlerterInfoInter;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.webService.WebServerInf;
import com.ttmv.monitoring.webService.tools.ResponseTool;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月23日11:46:00
 * @explain : W010_报警器详细信息查询
 */
@SuppressWarnings({ "rawtypes","unused","unchecked"})
public class QueryAlerterInfoByIdServiceImpl implements WebServerInf{
	
	private static Logger logger = LogManager.getLogger(QueryAlerterInfoByIdServiceImpl.class);
	private IAlerterInfoInter iAlerterInfoInter;
	/**
	 * 报警器务逻辑处理
	 * @param reqMap
	 * @return resMap
	 */ 
	public Map handler(Map reqMap) {
		logger.info("[QueryAlerterInfoByIdServiceImpl] @ start...");
		//请求数据验证
		try {
			checkData(reqMap);
		} catch (Exception e) {
			logger.error("数据校验失败!!!" + e.getMessage());
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
		}
		AlerterInfoTmp alerterInfo = null;
		try {
			alerterInfo = iAlerterInfoInter.queryAlerterInfo(
					new BigInteger(reqMap.get("alerterID").toString()) );
		} catch (Exception e) {
			logger.error("报警器信息查询处理异常!!!" , e);
			return ResponseTool.returnError();
		}
		if(alerterInfo == null){
			logger.warn("alerterID查询报警器不存在!!!");
			return ResponseTool.returnSuccess(null);
		}
		//装返回结果resMap
		Map resData = this.takeResData(alerterInfo);
		return ResponseTool.returnSuccess(resData);
		
	}
	
	/**
	 * 组装返回数据
	 * @param resData
	 * @param alerterInfo
	 * @return 
	 */
	private Map takeResData(AlerterInfoTmp alerterInfo){
		Map resData = new HashMap();
		resData.put("alerterID", alerterInfo.getId());
		resData.put("alerterName", alerterInfo.getAlerterName());
		resData.put("alerterType", alerterInfo.getAlerterType());
		resData.put("alerterCont", alerterInfo.getAlerterCount());
		resData.put("alerterUsers", alerterInfo.getAlerterUser());
		resData.put("alerterMsg", alerterInfo.getAlerterMsg());
		return resData;
	}

	/**
	 * 业务数据校验(非空，数据规格)
	 * @param reqMap
	 * @return void
	 * @exception Exception
	 */
	public void checkData(Map reqMap) throws Exception {
		if(reqMap.get("alerterID") == null || "".equals(reqMap.get("alerterID"))){
			throw new Exception("[QueryAlerterInfoById_[alerterID] is null...]");
		}
	}

	public IAlerterInfoInter getiAlerterInfoInter() {
		return iAlerterInfoInter;
	}

	public void setiAlerterInfoInter(IAlerterInfoInter iAlerterInfoInter) {
		this.iAlerterInfoInter = iAlerterInfoInter;
	}
	
	
	
	
}
