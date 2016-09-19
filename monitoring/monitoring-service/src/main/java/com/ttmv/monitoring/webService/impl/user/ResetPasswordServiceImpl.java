package com.ttmv.monitoring.webService.impl.user;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.UserInfo;
import com.ttmv.monitoring.entity.querybean.UserInfoQuery;
import com.ttmv.monitoring.inter.IUserInfoInter;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.tools.constant.SmsConstant;
import com.ttmv.monitoring.webService.WebServerInf;
import com.ttmv.monitoring.webService.tools.ResponseTool;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月16日10:50:53
 * @explain : W003_修改密码
 */
@SuppressWarnings({ "rawtypes"})
public class ResetPasswordServiceImpl  implements WebServerInf{
	
	private static Logger logger = LogManager.getLogger(ResetPasswordServiceImpl.class);
	private IUserInfoInter iUserInfoInter;

	/**
	 * 用户密码修改
	 * @param reqMap
	 * @return resMap
	 */
	public Map handler(Map reqMap) {
		logger.info("[ResetPasswordServiceImpl] @ start...");
		//请求数据校验
		try {
			checkData(reqMap);
		} catch (Exception e) {
			logger.error("数据校验失败!!!" + e.getMessage());
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
		}
		
		Integer type = Integer.parseInt(reqMap.get("type").toString());
		if(SmsConstant.RESETPWD_USER.equals(type)){//普通修改
			//校验身份，查询修改对象
			UserInfo userInfoQuery = new UserInfo();
			userInfoQuery.setUserName(reqMap.get("userName").toString());
			userInfoQuery.setUserPasswd(reqMap.get("password").toString());
			UserInfo user = null;
			try {
				//登录验证
				user = iUserInfoInter.login(reqMap.get("userName").toString(), reqMap.get("password").toString());
			} catch (Exception e1) {
				logger.error("密码修改，对象查询失败!!!" , e1);
				return ResponseTool.returnError();
			}
			if(user != null){
				user.setUserPasswd(reqMap.get("newPassword").toString());
				try {
					//密码修改
					iUserInfoInter.updateUserInfo(user);
					logger.info("密码修改成功!!! userName:" + user.getUserName());
				} catch (Exception e) {
					logger.error("用户密码修改失败!!!" , e);
					return ResponseTool.returnError();
				}
			}else {
				logger.warn("用户密码修改，数据查询为空!!!");
				return ResponseTool.returnError(ResultCodeConstant.SELECT_DATA_NULL);
			}
		}else if(SmsConstant.RESETPWD_ADMIN.equals(type)){//管理员修改
			//创建查询
			UserInfoQuery userInfoQuery = new UserInfoQuery();
			userInfoQuery.setUserName(reqMap.get("userName").toString());
			List<UserInfo> users = null;
			try {
				// TODO 精确查询
				users = iUserInfoInter.queryUserInfo(userInfoQuery);
			} catch (Exception e) {
				logger.error("密码修改，对象查询失败!!!" , e);
				return ResponseTool.returnError();
			}
			
			if(users.size()==1){
				UserInfo userInfo = users.get(0);
				userInfo.setUserPasswd(reqMap.get("newPassword").toString());
				try {
					//密码修改
					iUserInfoInter.updateUserInfo(userInfo);
					logger.info("密码修改成功!!! userName:" + userInfo.getUserName());
				} catch (Exception e) {
					logger.error("密码修改失败!!!" , e);
					return ResponseTool.returnError();
				}
			}else {
				logger.warn("密码修改，数据查询为空!!!");
				return ResponseTool.returnError(ResultCodeConstant.SELECT_DATA_NULL);
			}
		}else {
			logger.error("操作类型非法!!!");
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_ILLEGAL_TYPE);
		}
		return ResponseTool.returnSuccess();
	}
	
	/**
	 * 业务数据校验(非空，数据规格)
	 * @param reqMap
	 * @return void
	 * @exception Exception
	 */
	public void checkData(Map reqMap) throws Exception {
		if(reqMap.get("userName") == null || "".equals(reqMap.get("userName"))){//userName非空验证
			throw new Exception("[ResetPassword_[userName] is null...]");
		}
		if(reqMap.get("type") == null || "".equals(reqMap.get("type"))){//type非空验证
			throw new Exception("[ResetPassword_[type] is null...]");
		}
		if(reqMap.get("type").toString().equals(SmsConstant.OPERATION_UPDATE)){//普通修改,需校验原密码
			if(reqMap.get("password") == null || "".equals(reqMap.get("password"))){//password非空验证
				throw new Exception("[ResetPassword_[password] is null...]");
			}
		}
		if(reqMap.get("newPassword") == null || "".equals(reqMap.get("newPassword"))){//newPassword非空验证
			throw new Exception("[ResetPassword_[newPassword] is null...]");
		}
		
	}

	public IUserInfoInter getiUserInfoInter() {
		return iUserInfoInter;
	}

	public void setiUserInfoInter(IUserInfoInter iUserInfoInter) {
		this.iUserInfoInter = iUserInfoInter;
	}
	
	

}
