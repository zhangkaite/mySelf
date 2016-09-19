package com.ttmv.datacenter.usercenter.service.facade.impl.userBasic;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.operation.query.UserInfoQuery;
import com.ttmv.datacenter.usercenter.domain.protocol.ResetPassword;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.UcConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年1月27日 下午10:26:58  
 * @explain :密码变更
 */
@SuppressWarnings({ "rawtypes" })
public class ModifyPasswordServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(ModifyPasswordServiceImpl.class);
	
	
	private UserInfoDao userInfoDao;// 入库接口

	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[密码修改]_开始逻辑处理...");
		Long startTime = System.currentTimeMillis();
		ResetPassword resetPassword = null;
		//数据校验
		try {
			resetPassword = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		//创建查询对象
		UserInfoQuery userInfoQuert = this.createuserInfoQuery(resetPassword);
		//查询修改对象
		UserInfo userInfo = null;
		try {
			userInfo = this.getUserInfo(userInfoQuert);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "查询修改对象失败_" + e.getMessage());
			return ResponseTool.returnException();
		}
		if(userInfo == null){
			logger.error("[" + reqID + "]@@" + "修改对象查询失败，无法修改密码！！！");
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE, ErrorCodeConstant.ERRORMSG_PARAMETERERROR_CODE);
		}
		
		
		//更改新密码
		UserInfo upUserInfo = new UserInfo();
		upUserInfo.setUserid(userInfo.getUserid());
		upUserInfo.setPassword(resetPassword.getNewPassword());
		upUserInfo.setReqId(reqID);
		try {
			userInfoDao.updateUserInfo(upUserInfo);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "密码修改失败_" , e);
			return ResponseTool.returnException();
		}
		logger.info("[" + reqID + "]@@"+ "[***密码修改成功***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(null);
	}
	
	/**
	 * 创建查询对象
	 * @param resetPassword
	 * @return
	 */
	private UserInfoQuery createuserInfoQuery(ResetPassword resetPassword){
		UserInfoQuery userInfoQuery = new UserInfoQuery();
		if(UcConstant.MDFPWD_UNAMEPAWD_CODE.equals(resetPassword.getType())){//userName+旧密码+新密码
			userInfoQuery.setUserName(resetPassword.getUserName());
			userInfoQuery.setPassword(resetPassword.getOldPassword());
		}else if(UcConstant.MDFPWD_UNAMENEWPWD_CODE.equals(resetPassword.getType())){//userName+新密码
			userInfoQuery.setUserName(resetPassword.getUserName());
		}else if(UcConstant.MDFPWD_MOBILENEWPWD_CODE.equals(resetPassword.getType())){//登录手机+新密码
			userInfoQuery.setLoginMobile(resetPassword.getPhone());
		}else if(UcConstant.MDFPWD_EMAILNEWPWD_CODE.equals(resetPassword.getType())){//登录邮箱+新密码
			userInfoQuery.setLoginEmail(resetPassword.getEmail());
		}
		return userInfoQuery;
	}
	
	/**
	 * 查询修改对象
	 * @param userInfoQuery
	 * @return
	 * @throws Exception
	 */
	private UserInfo getUserInfo(UserInfoQuery userInfoQuery) throws Exception{
		
		List<UserInfo> users = userInfoDao.selectListBySelectivePaging(userInfoQuery);
		if(users != null && users.size() == 1){
			UserInfo userInfo = users.get(0);
			return userInfo;
		}else{
			return null;
		}
	}
	

	/**
	 * 业务数据校验
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private ResetPassword checkData(Object object) throws Exception{
		ResetPassword resetPassword = (ResetPassword)object;
		if(resetPassword == null){
			throw new Exception("对象转换失败！！！");
		}
		if (resetPassword.getType() == null) {
			throw new Exception("[修改密码_修改类型 type 为空！！！]");
		}
		if(UcConstant.MDFPWD_UNAMEPAWD_CODE.equals(resetPassword.getType())){
			if(resetPassword.getUserName() == null || "".equals(resetPassword.getUserName())){
				throw new Exception("[修改密码_userName 为空！！！]");
			}
			if(resetPassword.getNewPassword() == null || "".equals(resetPassword.getNewPassword())){
				throw new Exception("[修改密码_新密码 newPassword 为空！！！]");
			}
			if(resetPassword.getOldPassword() == null || "".equals(resetPassword.getOldPassword())){
				throw new Exception("[修改密码_旧密码 password 为空！！！]");
			}
		}
		else if(UcConstant.MDFPWD_UNAMENEWPWD_CODE.equals(resetPassword.getType())){
			if(resetPassword.getUserName() == null || "".equals(resetPassword.getUserName())){
				throw new Exception("[找回密码__userName 为空！！！]");
			}
			if(resetPassword.getNewPassword() == null || "".equals(resetPassword.getNewPassword())){
				throw new Exception("[找回密码__新密码 newPassword 为空！！！]");
			}
		}
		else if(UcConstant.MDFPWD_MOBILENEWPWD_CODE.equals(resetPassword.getType())){
			if(resetPassword.getPhone() == null || "".equals(resetPassword.getPhone())){
				throw new Exception("[找回密码__phone 为空！！！]");
			}
			if(resetPassword.getNewPassword() == null || "".equals(resetPassword.getNewPassword())){
				throw new Exception("[找回密码__新密码 newPassword 为空！！！]");
			}
			
		}
		else if(UcConstant.MDFPWD_EMAILNEWPWD_CODE.equals(resetPassword.getType())){
			if(resetPassword.getEmail() == null || "".equals(resetPassword.getEmail())){
				throw new Exception("[找回密码_email 为空！！！]");
			}
			if(resetPassword.getNewPassword() == null || "".equals(resetPassword.getNewPassword())){
				throw new Exception("[找回密码__新密码 newPassword 为空！！！]");
			}
		}
		return resetPassword;
	}

	
	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

}
