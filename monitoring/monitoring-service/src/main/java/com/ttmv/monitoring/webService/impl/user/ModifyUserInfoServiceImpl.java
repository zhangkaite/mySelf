package com.ttmv.monitoring.webService.impl.user;

import java.math.BigInteger;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.UserInfo;
import com.ttmv.monitoring.inter.IUserInfoInter;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.tools.constant.SmsConstant;
import com.ttmv.monitoring.webService.WebServerInf;
import com.ttmv.monitoring.webService.tools.ResponseTool;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月16日10:40:34
 * @explain : W002_修改用户
 */
@SuppressWarnings({ "rawtypes"})
public class ModifyUserInfoServiceImpl implements WebServerInf {

	private static Logger logger = LogManager.getLogger(ModifyUserInfoServiceImpl.class);
	private IUserInfoInter iUserInfoInter;
	
	/**
	 * 用户修改务逻辑处理
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
		Integer type = Integer.parseInt(reqMap.get("type").toString());
		if(SmsConstant.OPERATION_DELETE.equals(type)){//删除用户
			try {
				iUserInfoInter.deleteUserInfo(new  BigInteger(reqMap.get("userID").toString()) , SmsConstant.USTATE_REMOVED);
			} catch (Exception e) {
				logger.error("用户删除处理异常!!!" , e);
				return ResponseTool.returnError();
			}
		}else if(SmsConstant.OPERATION_UPDATE.equals(type)){//修改资料
			//创建修改对象
			UserInfo userInfo= creatUserInfo(reqMap);
			try {
				iUserInfoInter.updateUserInfo(userInfo);
				logger.debug("用户信息修改操作成功!!!");
			} catch (Exception e) {
				logger.error("用户信息修改失败!!!" , e);
				return ResponseTool.returnError();
			}
		}else{//参数非法
			logger.error("操作类型非法!!!");
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_ILLEGAL_TYPE);
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
	private UserInfo creatUserInfo(Map reqMap) {
		// 创建修改对象
		UserInfo userInfo = new UserInfo();
		userInfo.setId(new BigInteger(reqMap.get("userID").toString()));
		if(reqMap.get("email") != null){
			userInfo.setUserMail(reqMap.get("email").toString());
		}
		if(reqMap.get("mobile") != null){
			userInfo.setUserMobile(reqMap.get("mobile").toString());
		}
		if(reqMap.get("realName") != null){
			userInfo.setUserRealName(reqMap.get("realName").toString());
		}
		return userInfo;
		
	}
	

	/**
	 * 业务数据校验(非空，数据规格)
	 * @param reqMap
	 * @return void
	 * @exception Exception
	 */
	public void checkData(Map reqMap) throws Exception{
		if(reqMap.get("userID") == null || "".equals(reqMap.get("userID"))){//userID非空验证
			throw new Exception("[ModifyUserInfo_[userID] is null...]");
		}
		if(reqMap.get("type") == null || "".equals(reqMap.get("type"))){//修改类型非空验证
			throw new Exception("[ModifyUserInfo_[type] is null...]");
		}
		
	}

	public IUserInfoInter getiUserInfoInter() {
		return iUserInfoInter;
	}

	public void setiUserInfoInter(IUserInfoInter iUserInfoInter) {
		this.iUserInfoInter = iUserInfoInter;
	}
	
	
	

}
