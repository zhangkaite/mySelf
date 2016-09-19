package com.ttmv.datacenter.usercenter.service.facade.impl.overCallBack;

import java.math.BigInteger;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.protocol.LevelCallBack;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**
 * 主播、用户等级经验修改接口（统计系统回调专用）
 * @author Damon
 * 
announcerLevel	主播等级	number
announcerExp	主播经验	number
userLevel	用户等级	number
exp       用户经验   number
 *
 */
public class LevelCallBackServiceImpl extends AbstractUserBase {
	private static Logger logger = LogManager.getLogger(LevelCallBackServiceImpl.class);
	private UserInfoDao userInfoDao;

	@Override
	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[到期回调 主播、用户等级经验修改接口]_Start...");
		LevelCallBack levelCallBack  = null;
		// 数据验证
		try {
			levelCallBack = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		// 创建修改对象
		UserInfo userInfo = this.creatUserInfo(levelCallBack,reqID);
		try {
			userInfoDao.updateUserInfo(userInfo);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "修改失败_" + e.getMessage());
			return ResponseTool.returnException();
		}
		logger.info("[" + reqID + "]@@" + "到期回调 主播、用户等级经验修改接口 处理完成！！！" +"@@[userID]:"+ levelCallBack.getUserID());
		return ResponseTool.returnSuccess(null);
		
	}
	
	
	
	/**
	 * 创建修改对象
	 * @param levelCallBack
	 * @return
	 */
	private UserInfo creatUserInfo(LevelCallBack levelCallBack ,String reqID) {
		UserInfo userInfo = new UserInfo();
		BigInteger userID = new BigInteger(levelCallBack.getUserID().toString());
		userInfo.setUserid(userID);
		userInfo.setReqId(reqID);
		if(levelCallBack.getAnnouncerExp() != null){
			userInfo.setAnnouncerExp(levelCallBack.getAnnouncerExp());
		}
		if(levelCallBack.getExp() != null){
			userInfo.setExp(levelCallBack.getExp());
		}
		if(levelCallBack.getUserLevel() != null){
			userInfo.setUserLevel(levelCallBack.getUserLevel());
		}
		if(levelCallBack.getAnnouncerLevel() != null){
			userInfo.setAnnouncerLevel(levelCallBack.getAnnouncerLevel());
		}
		return userInfo;
	}
	
	/**
	 * 业务数据校验
	 * 
	 * @param validation
	 * @param reqID
	 * @return
	 * @throws Exception
	 */
	protected LevelCallBack checkData(Object object) throws Exception {
		LevelCallBack levelCallBack = (LevelCallBack) object;
		if (levelCallBack == null) {
			throw new Exception("[对象转换失败！！！]");
		}
		if (levelCallBack.getUserID() == null) {
			throw new Exception("[经验等级变更_userID 为空！！！]");
		}

		return levelCallBack;
	}



	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}



	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}
	
	
	
	

}
