package com.ttmv.datacenter.usercenter.service.facade.impl.userManage;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.protocol.RemoveForbiddenUser;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.UcConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月2日 下午10:19:34  
 * @explain :解冻、撤销删除
 */
@SuppressWarnings({ "rawtypes"})
public class RemoveForbiddenUserServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(RemoveForbiddenUserServiceImpl.class);
	private UserInfoDao userInfoDao;
	
	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[解冻/撤销删除]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		RemoveForbiddenUser removeForbiddenUser = null;
		//数据校验
		try {
			removeForbiddenUser = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnException();
		}
		//创建userInfo对象
		UserInfo userInfo = this.createUserInfo(removeForbiddenUser,reqID);
		//修改
		try {
			userInfoDao.updateUserInfo(userInfo);
		} catch (Exception e) {
			return ResponseTool.returnException();
		}
		logger.info("[" + reqID + "]@@"+ "[***撤删、解冻用户成功***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(null);
	}
	
	private RemoveForbiddenUser checkData(Object object) throws Exception{
		RemoveForbiddenUser removeForbiddenUser = (RemoveForbiddenUser)object;
		if(removeForbiddenUser == null){
			throw new Exception("对象转换失败！！！");
		}
		if(removeForbiddenUser.getUserID() == null){
			throw new Exception("userID为空！！！");
		}
		
		return removeForbiddenUser;
	}
	
	/**
	 * 创建UserInfo对象
	 * @param forbiddenUser
	 * @return
	 */
	private UserInfo createUserInfo(RemoveForbiddenUser removeForbiddenUser ,String reqId){
		UserInfo userInfo = new UserInfo();
		userInfo.setUserid(removeForbiddenUser.getUserID());
		if(removeForbiddenUser.getType() .equals(0)){//解冻用户
			userInfo.setState(UcConstant.USTATE_NORMAL);//正常
		}else if(removeForbiddenUser.getType().equals(9)){//撤销删除
			userInfo.setState(UcConstant.USTATE_NORMAL);//正常
		}
		userInfo.setReqId(reqId);
		return userInfo;
	}

}
