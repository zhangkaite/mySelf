package com.ttmv.datacenter.usercenter.service.facade.impl.userBasic;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.protocol.PwdValidation;
import com.ttmv.datacenter.usercenter.service.facade.impl.userQuery.QueryUserByIdServiceImpl;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.UcConstant;


/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年10月13日20:33:19 
 * @explain :密码是否存在验证
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PwdValidationServiceImpl extends AbstractUserBase{
	
private static Logger logger = LogManager.getLogger(QueryUserByIdServiceImpl.class);
	
	private UserInfoDao userInfoDao;
	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public Map handler(Object object, String reqID) {
		logger.debug("[" + reqID + "]@@" + "[密码是否存在验证]_Start...");
		Long startTime = System.currentTimeMillis();
		//数据校验
		PwdValidation pwdValidation = null;
		try {
			pwdValidation = checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		//查询信息
		UserInfo userInfo = null;
		BigInteger userId = pwdValidation.getUserID();
		try {
			userInfo = userInfoDao.selectUserInfoByUserId(userId);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "用户信息查询失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERERROR_CODE);
		}
		if(userInfo == null){
			logger.debug("[" + reqID + "]@@" + "userID对应用户信息不存在！！！");
			return ResponseTool.returnSuccess(null);
		}
		logger.info("[" + reqID + "]@@"+ "[***密码是否存在验证 完成***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(takeResData(userInfo));
	}
	
	/**
	 * 组装返回数据
	 * @param resData
	 * @param userInfo
	 * @return
	 */
	protected Map takeResData(UserInfo userInfo){
		Map resData = new HashMap();
		int result = 0;
		if("".equals(userInfo.getPassword()) || userInfo.getPassword() == null){
			result = UcConstant.PWD_NO_CODE;
		}else{
			result = UcConstant.PWD_YES_CODE;
		}
		resData.put("result", result);
		return resData;
	}
	
	/**
	 * 业务数据校验
	 * @param addUser
	 * @param resMap
	 * @param reqID
	 * @return
	 * @throws Exception 
	 */
	private PwdValidation checkData(Object object) throws Exception{
		PwdValidation pwdValidation =(PwdValidation)object;
		if(pwdValidation == null){
			throw new Exception("[对象转换失败！！！]");
		}
		if (pwdValidation.getUserID() == null) {
			throw new Exception("[校验_校验类型 UserID 为空！！！]");
		}
		return pwdValidation;
	}

}
