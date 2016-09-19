package com.ttmv.monitoring.webService.impl.configuration.alerter;

import java.math.BigInteger;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.tmp.AlerterInfoTmp;
import com.ttmv.monitoring.inter.IAlerterInfoInter;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.webService.WebServerInf;
import com.ttmv.monitoring.webService.tools.ResponseTool;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月23日18:46:26
 * @explain : W012_删除报警器
 */
@SuppressWarnings({ "rawtypes"})
public class DeleteAlerterServiceImpl implements WebServerInf{
	
	private static Logger logger = LogManager.getLogger(DeleteAlerterServiceImpl.class);
	private IAlerterInfoInter iAlerterInfoInter;
	/**
	 * 修改报警器务逻辑处理
	 * @param reqMap
	 * @return resMap
	 */
	public Map handler(Map reqMap) {
		logger.info("[DeleteAlerterServiceImpl] @ start...");
		//请求数据验证
		try {
			checkData(reqMap);
		} catch (Exception e) {
			logger.error("数据校验失败!!!" + e.getMessage());
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
		}
		//删除前查询该报警器是否关联用户
		AlerterInfoTmp alerterInfoTmp = null;
		try {
			alerterInfoTmp = iAlerterInfoInter.queryAlerterInfo(new BigInteger(reqMap.get("alerterID").toString()));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(alerterInfoTmp.getAlerterUser().size()>=1){//存在关联用户
			logger.warn("该报警器已关联用户,无法直接删除！！！");
			return ResponseTool.returnError(ResultCodeConstant.ALERT_USER_Y);
		}
		
		//删除
		try {
			iAlerterInfoInter.deleteAlerterInfo(new BigInteger(reqMap.get("alerterID").toString()));
		} catch (Exception e) {
			logger.error("报警器删除处理异常!!!",e);
			return ResponseTool.returnError();
		}
		logger.info("[报警器删除成功!!!]");
		return ResponseTool.returnSuccess();
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
