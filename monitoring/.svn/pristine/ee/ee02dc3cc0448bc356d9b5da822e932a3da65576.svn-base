package com.ttmv.monitoring.webService.impl.user;

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
 * @version 创建时间：2015年9月17日09:41:32
 * @explain : W006_用户登录
 */
@SuppressWarnings({ "rawtypes"})
public class LoginServiceImpl implements WebServerInf{
	private static Logger logger = LogManager.getLogger(LoginServiceImpl.class);
	private IUserInfoInter iUserInfoInter;

	/**
	 * 用户登录
	 * @param reqMap
	 * @return resMap
	 */
	public Map handler(Map reqMap) {
		logger.info("[LoginServiceImpl] @ start...");
		//请求数据校验
		try {
			checkData(reqMap);
		} catch (Exception e) {
			logger.error("数据校验失败!!!" + e.getMessage());
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
		}
		//登录验证
		UserInfo user = null;
		try {
			user = iUserInfoInter.login(reqMap.get("loginName").toString(), reqMap.get("password").toString());
		} catch (Exception e) {
			logger.error("用户登录处理异常!!!",e);
			return ResponseTool.returnError();
		}
		if(user==null){
			logger.debug("登录失败!!!");
			return ResponseTool.returnError(ResultCodeConstant.LOGIN_ERROR);
		}
		//用户状态判断
		if(user.getUserStatus().equals(SmsConstant.USTATE_REMOVED)){//删除，返回失败
			return ResponseTool.returnError(ResultCodeConstant.USERNAME_DELETE);
		}else if(user.getUserStatus().equals(SmsConstant.USTATE_NORMAL)){//返回登录成功
			return ResponseTool.returnSuccess();
		}else{//异常的用户状态
			return ResponseTool.returnError(ResultCodeConstant.SELECT_DATA_NULL);
		}
	}
	
	
	/**
	 * 业务数据校验(非空，数据规格)
	 * @param reqMap
	 * @return void
	 * @exception Exception
	 */
	public void checkData(Map reqMap) throws Exception {
		if(reqMap.get("loginName") == null || "".equals(reqMap.get("loginName"))){//用户名非空验证
			throw new Exception("[Login_[loginName] is null...]");
		}
		if(reqMap.get("password") == null || "".equals(reqMap.get("password"))){//密码非空验证
			throw new Exception("[Login_[password] is null...]");
		}
		
	}

	public IUserInfoInter getiUserInfoInter() {
		return iUserInfoInter;
	}

	public void setiUserInfoInter(IUserInfoInter iUserInfoInter) {
		this.iUserInfoInter = iUserInfoInter;
	}
	
	

}
