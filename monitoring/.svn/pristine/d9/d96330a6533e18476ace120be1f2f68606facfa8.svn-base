package com.ttmv.monitoring.webService.impl.configuration.alerter;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.UserInfo;
import com.ttmv.monitoring.entity.tmp.AlerterInfoTmp;
import com.ttmv.monitoring.inter.IAlerterInfoInter;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.webService.WebServerInf;
import com.ttmv.monitoring.webService.tools.ResponseTool;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月23日11:31:43
 * @explain : W009_修改报警器
 */
@SuppressWarnings({ "rawtypes","unused","unchecked"})
public class ModifyAlerterInfoServiceImpl implements WebServerInf{
	
	private static Logger logger = LogManager.getLogger(ModifyAlerterInfoServiceImpl.class);
	private IAlerterInfoInter iAlerterInfoInter;
	/**
	 * 修改报警器务逻辑处理
	 * @param reqMap
	 * @return resMap
	 */
	public Map handler(Map reqMap) {
		logger.info("[ModifyUserInfoServiceImpl] @ start...");
		// 请求数据校验
		try {
 			checkData(reqMap);
		} catch (Exception e) {
			logger.error("数据校验失败!!!" + e.getMessage());
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
		}
		//创建修改对象
		AlerterInfoTmp alerterInfo = this.creatUserInfo(reqMap);
		try {
			iAlerterInfoInter.updateAlerterInfo(alerterInfo);
			logger.debug("报警器信息修改操作成功!!!");
		} catch (Exception e) {
			logger.error("报警器信息修改失败!!!" , e);
			return ResponseTool.returnError();
		}
		return ResponseTool.returnSuccess();
	}
	
	/**
	 * 创建修改对象
	 * 
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	private AlerterInfoTmp creatUserInfo(Map reqMap) {
		// 创建修改对象
		AlerterInfoTmp alerterInfoTmp = new AlerterInfoTmp();
		alerterInfoTmp.setId(new BigInteger(reqMap.get("alerterID").toString()));
		if(reqMap.get("alerterType") != null){
			alerterInfoTmp.setAlerterType(reqMap.get("alerterType").toString());
		}
		if(reqMap.get("alerterCont") != null){
			alerterInfoTmp.setAlerterCount(Integer.parseInt(reqMap.get("alerterCont").toString()));
		}
		if(reqMap.get("alerterMsg") != null){
			alerterInfoTmp.setAlerterMsg(Integer.parseInt(reqMap.get("alerterMsg").toString()));
		}
		if(reqMap.get("alerterUsers") != null){
			alerterInfoTmp.setAlerterUser((List)reqMap.get("alerterUsers"));
		}
		return alerterInfoTmp;
		
	}

	/**
	 * 业务数据校验(非空，数据规格)
	 * @param reqMap
	 * @return void
	 * @exception Exception
	 */
	public void checkData(Map reqMap) throws Exception {
		if(reqMap.get("alerterID") == null || "".equals(reqMap.get("alerterID"))){//userID非空验证
			throw new Exception("[ModifyAlerterInfo_[alerterID] is null...]");
		}
	}

	public IAlerterInfoInter getiAlerterInfoInter() {
		return iAlerterInfoInter;
	}

	public void setiAlerterInfoInter(IAlerterInfoInter iAlerterInfoInter) {
		this.iAlerterInfoInter = iAlerterInfoInter;
	}
	
	
	
	
}
