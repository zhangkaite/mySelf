package com.ttmv.datacenter.usercenter.service.facade.impl.userBasic;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.protocol.SetFriendVerifyInfo;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年3月13日 上午10:43:55  
 * @explain :好友验证信息设置
 */
@SuppressWarnings({ "rawtypes" })
public class SetFriendVerifyInfoServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(SetFriendVerifyInfoServiceImpl.class);
	
	private UserInfoDao userInfoDao;
	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[好友验证信息设置]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		//数据检查
		SetFriendVerifyInfo friendVerifyInfo = null;
		try {
			friendVerifyInfo = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		//创建修改对象
		UserInfo userInfo = this.createUserInfo(friendVerifyInfo,reqID);
		//修改数据
		try {
			userInfoDao.updateUserInfo(userInfo);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "[好友验证信息设置失败！！！]" ,e);
			return ResponseTool.returnException();
		}
		logger.info("[" + reqID + "]@@"+ "[业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(null);
	}
	
	/**
	 * 修改对象创建
	 * @param friendVerifyInfo
	 * @return
	 */
	private UserInfo createUserInfo(SetFriendVerifyInfo friendVerifyInfo ,String reqID){
		UserInfo userInfo = new UserInfo();
		userInfo.setUserid(friendVerifyInfo.getUserID());
		userInfo.setDndType(friendVerifyInfo.getDndType());
		userInfo.setProblemType(friendVerifyInfo.getProblemType());
		userInfo.setAnswer(friendVerifyInfo.getAnswer());
		userInfo.setReqId(reqID);
		return userInfo;
		
	}
	
	/**
	 * 数据验证
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	private SetFriendVerifyInfo checkData(Object object) throws Exception{
		SetFriendVerifyInfo friendVerifyInfo = (SetFriendVerifyInfo)object;
		if(friendVerifyInfo == null){
			throw new Exception("[对象转换失败！！！]");
		}
		if(friendVerifyInfo.getUserID() == null){
			throw new Exception("[UserID为空！！！]");
		}
		if(friendVerifyInfo.getDndType() == null){
			throw new Exception("[DndType为空！！！]");
		}
		if(friendVerifyInfo.getProblemType() == null){
			throw new Exception("[ProblemType为空！！！]");
		}
		if(friendVerifyInfo.getAnswer() == null){
			throw new Exception("[Answer为空！！！]");
		}
		return friendVerifyInfo;
	}
	
	
}
