package com.ttmv.monitoring.webService.impl.configuration.alerter;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.AlerterInfo;
import com.ttmv.monitoring.entity.UserInfo;
import com.ttmv.monitoring.entity.querybean.AlerterInfoQuery;
import com.ttmv.monitoring.entity.querybean.UserInfoQuery;
import com.ttmv.monitoring.entity.tmp.AlerterInfoTmp;
import com.ttmv.monitoring.inter.IAlerterInfoInter;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.tools.constant.SmsConstant;
import com.ttmv.monitoring.webService.WebServerInf;
import com.ttmv.monitoring.webService.tools.ResponseTool;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月23日10:19:45
 * @explain : W008_新增报警器
 */
@SuppressWarnings({ "rawtypes","unused","unchecked"})
public class AddAlerterServiceImpl implements WebServerInf{
	private static Logger logger = LogManager.getLogger(AddAlerterServiceImpl.class);
	private IAlerterInfoInter iAlerterInfoInter;

	/**
	 * 新增报警器务逻辑处理
	 * @param reqMap
	 * @return resMap
	 */
	public Map handler(Map reqMap) {
		logger.info("[AddAlerterServiceImpl] @ start...");
		//请求数据校验
		try {
			checkData(reqMap);
		} catch (Exception e) {
			logger.error("数据校验失败!!!" + e.getMessage());
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
		}
		//创建AlerterInfo对象
		AlerterInfoTmp alerterInfo = null;
		try {
			alerterInfo = this.creatAlerterInfo(reqMap);
		} catch (Exception e) {
			logger.error("创建userInfo对象出现异常!!!",e);
			return ResponseTool.returnError();
		}
		//校验报警器名称是否被使用
		AlerterInfoQuery alerterInfoQuery = new AlerterInfoQuery();
		alerterInfoQuery.setAlerterName(alerterInfo.getAlerterName());
		List<AlerterInfoTmp> alerters = null;
		try {
			alerters = iAlerterInfoInter.queryAlerterInfo(alerterInfoQuery);
		} catch (Exception e) {
			logger.error("报警器查询处理异常!!!" , e);
			return ResponseTool.returnError();
		}
		if(alerters!=null && alerters.size() != 0){
			logger.info("校验失败,报警器名称 被使用!!! alerterName:" + alerterInfoQuery.getAlerterName() + " size:" + alerters.size());
			return ResponseTool.returnError(ResultCodeConstant.USERNAME_ONLY_ERROR);
		}
		//数据入库
		try {
			iAlerterInfoInter.addAlerterInfo(alerterInfo);
			logger.info("[报警器添加成功!!!] alerterName:" + alerterInfo.getAlerterName());
			return ResponseTool.returnSuccess();
		} catch (Exception e) {
			logger.error("报警器添加处理异常!!!",e);
			return ResponseTool.returnError();
		}
	}
	
	/**
	 * 创建新增对象
	 * @param reqMap
	 * @return AlerterInfo
	 * @throws Exception
	 */
	private AlerterInfoTmp creatAlerterInfo(Map reqMap) throws Exception {
		AlerterInfoTmp alerterInfo = new AlerterInfoTmp();
		alerterInfo.setAlerterName(reqMap.get("alerterName").toString());//报警器名称
		alerterInfo.setAlerterType(reqMap.get("alerterType").toString());//报警类型（邮箱+短信）
		alerterInfo.setAlerterCount(Integer.parseInt(reqMap.get("alerterCont").toString()));//次数
		alerterInfo.setAlerterMsg(Integer.parseInt(reqMap.get("alerterMsg").toString()));//消息模板
		alerterInfo.setAlerterUser((List)reqMap.get("alerterUsers"));//用户列表
		return alerterInfo;
		
	}
	
	/**
	 * 业务数据校验(非空，数据规格)
	 * @param reqMap
	 * @return void
	 * @exception Exception
	 */
	public void checkData(Map reqMap) throws Exception {
		if(reqMap.get("alerterName") == null || "".equals(reqMap.get("alerterName"))){
			throw new Exception("[AddAlerter_[alerterName] is null...]");
		}
		if(reqMap.get("alerterType") == null || "".equals(reqMap.get("alerterType"))){
			throw new Exception("[AddAlerter_[alerterType] is null...]");
		}
		if(reqMap.get("alerterCont") == null || "".equals(reqMap.get("alerterCont"))){
			throw new Exception("[AddAlerter_[alerterCont] is null...]");
		}
		if(reqMap.get("alerterUsers") == null || "".equals(reqMap.get("alerterUsers"))){
			throw new Exception("[AddAlerter_[alerterUsers] is null...]");
		}
		if(reqMap.get("alerterMsg") == null || "".equals(reqMap.get("alerterMsg"))){
			throw new Exception("[AddAlerter_[alerterMsg] is null...]");
		}
	}

	public IAlerterInfoInter getiAlerterInfoInter() {
		return iAlerterInfoInter;
	}

	public void setiAlerterInfoInter(IAlerterInfoInter iAlerterInfoInter) {
		this.iAlerterInfoInter = iAlerterInfoInter;
	}


	
	

}
