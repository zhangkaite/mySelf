package com.ttmv.datacenter.usercenter.service.facade.impl.userBasic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.operation.query.UserInfoQuery;
import com.ttmv.datacenter.usercenter.domain.protocol.UserValidation;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.UcConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年1月26日 下午4:40:11  
 * @explain :用户校验
 */
public class UserValidationServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(UserValidationServiceImpl.class);
	
	private UserInfoDao userInfoDao;
	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[用户校验]_Start...");
		Long startTime = System.currentTimeMillis();
		//数据校验
		UserValidation userValidation = null;
		try {
			userValidation = checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		//校验
		int in = -1;
		try {
			in = checkvalidation(userValidation, reqID);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + " 校验失败_" + e.getMessage());
			return ResponseTool.returnException();
		}
		Map resData = new HashMap();
		resData.put("result", in);
		//返回校验结果
		logger.info("[" + reqID + "]@@"+ "[***校验成功***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(resData);
	}
	
	
	
	/**
	 * 验证
	 * @param userValidation
	 * @param reqID
	 * @return
	 * @throws Exception
	 */
	private int checkvalidation(UserValidation userValidation,String reqID) throws Exception{
		UserInfoQuery userInfoQuery = new UserInfoQuery();
		int in = 1;
		if(UcConstant.VERIFY_ONLYUSERNAME_CODE.equals(userValidation.getType())){//用户名注册验证
			userInfoQuery.setUserName(userValidation.getValue());
			List <UserInfo> users = userInfoDao.selectListBySelectivePaging(userInfoQuery);
			if (users != null) {
				if (users.size() >= 1) {// 用户名已经注册
					logger.info("[" + reqID + "]@@" + userInfoQuery.getUserName() + "[用户名已经被占用！！！]");
					return 0;
				}
			}
		}else if(UcConstant.VERIFY_ONLYMOBILE_CODE.equals(userValidation.getType())){//手机注册验证
			userInfoQuery.setLoginMobile(userValidation.getValue());
			List <UserInfo> users = userInfoDao.selectListBySelectivePaging(userInfoQuery);
			if (users != null) {
				if (users.size() >= 1) {// 手机已经注册
					logger.info("[" + reqID + "]@@" + userInfoQuery.getLoginMobile() + "[注册手机已经被占用！！！]");
					return 0;
				}
				UserInfoQuery userInfoQuery5 = new UserInfoQuery();
				userInfoQuery5.setBindingMobile(userValidation.getValue());
				List <UserInfo> users5 = userInfoDao.selectListBySelectivePaging(userInfoQuery5);
				if (users5 != null) {
					if (users5.size() >= 5) {// 手机已经绑定
						logger.info("[" + reqID + "]@@" + userInfoQuery5.getBindingMobile() + "[手机绑定次数已经超过5次！！！]");
						return 0;
					}
				}
			}
		}else if(UcConstant.VERIFY_ONLYEMAIL_CODE.equals(userValidation.getType())){//邮箱注册验证
			userInfoQuery.setBindingEmail(userValidation.getValue());
			List <UserInfo> users = userInfoDao.selectListBySelectivePaging(userInfoQuery);
			if (users != null) {
				if (users.size() >= 1) {// 用户名已经注册
					logger.info("[" + reqID + "]@@" + userInfoQuery.getBindingEmail() + "[邮箱已经被占用！！！]");
					return 0;
				}
			}
		}else if(UcConstant.VERIFY_BINDINGMOBILE_CODE.equals(userValidation.getType())){//手机绑定次数
			userInfoQuery.setBindingMobile(userValidation.getValue());
			List <UserInfo> users5 = userInfoDao.selectListBySelectivePaging(userInfoQuery);
			if(users5!=null){
				if (users5.size() >= 5) {// 手机已经绑定
					logger.info("[" + reqID + "]@@" + userInfoQuery.getBindingMobile() + "[手机绑定次数已经超过5次！！！]");
					return 0;
				}
			}
		}
		return in;
	}
	
	
	/**
	 * 业务数据校验
	 * @param addUser
	 * @param resMap
	 * @param reqID
	 * @return
	 * @throws Exception 
	 */
	private UserValidation checkData(Object object) throws Exception{
		UserValidation validation =(UserValidation)object;
		if(validation == null){
			throw new Exception("[对象转换失败！！！]");
		}
		if (validation.getType() == null) {
			throw new Exception("[校验_校验类型 type 为空！！！]");
		}
		if (validation.getValue() == null) {
			throw new Exception("[校验_校验值 value 为空！！！]");
		}
		return validation;
	}

}
