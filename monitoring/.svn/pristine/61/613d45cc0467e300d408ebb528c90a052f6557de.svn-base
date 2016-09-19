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
 * @version 创建时间：2015年9月15日18:12:10
 * @explain : W001_添加用户
 */
@SuppressWarnings({ "rawtypes"})
public class AddUserServiceImpl implements WebServerInf{
	private static Logger logger = LogManager.getLogger(AddUserServiceImpl.class);
	private IUserInfoInter iUserInfoInter;

	/**
	 * 用户添加务逻辑处理
	 * @param reqMap
	 * @return resMap
	 */
	public Map handler(Map reqMap) {
		logger.info("[AddUserServiceImpl] @ start...");
		//请求数据校验
		try {
			checkData(reqMap);
		} catch (Exception e) {
			logger.error("数据校验失败!!!" + e.getMessage());
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
		}
		//创建userInfo对象
		UserInfo userInfo = null;
		try {
			userInfo = this.creatUserInfo(reqMap);
		} catch (Exception e) {
			logger.error("创建userInfo对象出现异常!!!",e);
			return ResponseTool.returnError();
		}
		//校验用户是否被使用
		UserInfoQuery userInfoQuery = new UserInfoQuery();
		userInfoQuery.setUserName(userInfo.getUserName());
		List<UserInfo> users = null;
		try {
			users = iUserInfoInter.queryUserInfo(userInfoQuery);
		} catch (Exception e) {
			logger.error("用户名查询处理异常!!!" , e);
			return ResponseTool.returnError();
		}
		if(users!=null && users.size() != 0){
			logger.info("校验失败,用户名已经 被使用!!! userName:" + userInfo.getUserName() + " size:" + users.size());
			return ResponseTool.returnError(ResultCodeConstant.USERNAME_ONLY_ERROR);
		}
		//数据入库
		try {
			iUserInfoInter.addUserInfo(userInfo);
			logger.info("[用户添加成功!!!] userName:" + userInfo.getUserName());
			return ResponseTool.returnSuccess();
		} catch (Exception e) {
			logger.error("用户添加处理异常!!!",e);
			return ResponseTool.returnError();
		}
	}
	
	/**
	 * 创建新增对象
	 * @param reqMap
	 * @return UserInfo
	 * @throws Exception
	 */
	private UserInfo creatUserInfo(Map reqMap) throws Exception {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserRealName(reqMap.get("realName").toString());
		userInfo.setUserName(reqMap.get("userName").toString());
		userInfo.setUserMail(reqMap.get("email").toString());
		userInfo.setUserMobile(reqMap.get("mobile").toString());
		userInfo.setUserPasswd(reqMap.get("password").toString());
		userInfo.setUserStatus(SmsConstant.USTATE_NORMAL);// 用户状态，默认正常
		return userInfo;
		
	}
	
	
	/**
	 * 业务数据校验(非空，数据规格)
	 * @param reqMap
	 * @return void
	 * @exception Exception
	 */
	public void checkData(Map reqMap) throws Exception{
		if(reqMap.get("userName") == null || "".equals(reqMap.get("userName"))){//用户名非空验证
			throw new Exception("[AddUser_[userName] is null...]");
		}
		if(reqMap.get("mobile") == null || "".equals(reqMap.get("mobile"))){//用户手机非空验证
			throw new Exception("[AddUser_[mobile] is null...]");
		}
		if(reqMap.get("email") == null || "".equals(reqMap.get("email"))){//用户邮箱非空验证
			throw new Exception("[AddUser_[email] is null...]");
		}
		if(reqMap.get("password") == null || "".equals(reqMap.get("password"))){//用户密码非空验证
			throw new Exception("[AddUser_[password] is null...]");
		}
		if(reqMap.get("realName") == null || "".equals(reqMap.get("realName"))){//用户真实姓名非空验证
			throw new Exception("[AddUser_[realName] is null...]");
		}
		
	}

	public IUserInfoInter getiUserInfoInter() {
		return iUserInfoInter;
	}

	public void setiUserInfoInter(IUserInfoInter iUserInfoInter) {
		this.iUserInfoInter = iUserInfoInter;
	}

	


}
