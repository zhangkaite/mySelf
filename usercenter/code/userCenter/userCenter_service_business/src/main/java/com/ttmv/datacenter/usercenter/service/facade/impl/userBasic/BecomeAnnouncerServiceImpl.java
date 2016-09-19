package com.ttmv.datacenter.usercenter.service.facade.impl.userBasic;

import java.math.BigInteger;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.agent.tokencenter.TokenCenterAgent;
import com.ttmv.datacenter.usercenter.dao.implement.util.JsonUtil;
import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.protocol.BecomeAnnouncer;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.facade.tools.jmstool.DamsUcResetStarEXPTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.UcConstant;

/**
 * 060_星级主播身份变更
 * @author Damon
 * 2015年12月16日20:12:413
 */
public class BecomeAnnouncerServiceImpl extends AbstractUserBase {
	private static Logger logger = LogManager.getLogger(BecomeAnnouncerServiceImpl.class);

	private UserInfoDao userInfoDao;
	private TokenCenterAgent tokenCenterAgent;
	//DAMON 2016年2月24日11:52:55（明星经验重置统计分析队列）
	private DamsUcResetStarEXPTool damsUcResetStarEXPTool ;

	@Override
	public Map handler(Object object, String reqID) {
		logger.debug("[" + reqID + "]@@" + "[星级主播身份变更]_Start...");
		Long startTime = System.currentTimeMillis();
		BecomeAnnouncer becomeAnnouncer = null;
		// 数据验证
		try {
			becomeAnnouncer = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}

		if (becomeAnnouncer.getToken() != null
				&& !UcConstant.SUPERTOKEN_CODE.equals(becomeAnnouncer.getToken())) {
			String uid = tokenCenterAgent.getUserId(becomeAnnouncer.getToken());
			if (!(becomeAnnouncer.getUserID().toString()).equals(uid)) {// 非法用户，token与锁修改用户id不对应
				logger.warn("[" + reqID + "]@@ [未授权的操作！！！]");
				return ResponseTool.returnError(
						ErrorCodeConstant.SYSTEM_BUSINESS_ERROR_CODE,
						ErrorCodeConstant.ERRORMSG_UNLICENSED_CODE);
			}
		}
		// userInfo 创建
		UserInfo userInfo = null;
		try {
			userInfo = createUserInfo(becomeAnnouncer, reqID);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "UserInfo对象创建失败_", e);
			return ResponseTool.returnException();
		}
		try {
			userInfoDao.updateUserInfo(userInfo);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "星级主播身份变更失败_", e);
			return ResponseTool.returnException();
		}
		
		//TODO 发送到统计分析系统  2016年2月24日11:55:21
		try {
			logger.debug("明星经验重置数据异步写入DAMS--->>>" + JsonUtil.getObjectToJson(object));
			damsUcResetStarEXPTool.sendMessage(JsonUtil.getObjectToJson(object));
		} catch (Exception e) {
			logger.warn("TB消费 请求数据写入DAMS失败！！！");
		}
		
		logger.info("[" + reqID + "]@@" + "[星级主播身份变更业务处理耗时(ms)]:"+ (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(null);
	}
	
	
	/**
	 * 创建修改对象
	 * 
	 * @param modifyUserExtend
	 * @return
	 * @throws Exception
	 */
	private UserInfo createUserInfo(BecomeAnnouncer becomeAnnouncer, String reqID)
			throws Exception {
		UserInfo userInfo = new UserInfo();
		BigInteger userID = new BigInteger(becomeAnnouncer.getUserID().toString());
		userInfo.setUserid(userID);
		userInfo.setReqId(reqID);
		if(UcConstant.ANNOUNCERTYPE_Y.equals(becomeAnnouncer.getType())){
			userInfo.setAnnouncerType(UcConstant.ANNOUNCERTYPE_Y);//成为星级主播
		}else if(UcConstant.ANNOUNCERTYPE_N.equals(becomeAnnouncer.getType())){
			userInfo.setAnnouncerType(UcConstant.ANNOUNCERTYPE_N);//取消星级主播
		}
		userInfo.setAnnouncerLevel(0);//初始化主播等级
		userInfo.setAnnouncerExp(new BigInteger("0"));//初始化主播经验
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
	protected BecomeAnnouncer checkData(Object object) throws Exception {
		BecomeAnnouncer becomeAnnouncer = (BecomeAnnouncer) object;
		if (becomeAnnouncer == null) {
			throw new Exception("[对象转换失败！！！]");
		}
		if (becomeAnnouncer.getUserID() == null) {
			throw new Exception("[星级主播身份变更_UserID 为空！！！]");
		}
		if (becomeAnnouncer.getType() == null) {
			throw new Exception("[星级主播身份变更_Type 为空！！！]");
		}
		if (becomeAnnouncer.getTime() == null) {
			throw new Exception("[星级主播身份变更_Time 为空！！！]");
		}
		if (becomeAnnouncer.getToken() == null || "".equals(becomeAnnouncer.getToken())) {
			throw new Exception("[星级主播身份变更_token 为空！！！]");
		}
		return becomeAnnouncer;
	}

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public TokenCenterAgent getTokenCenterAgent() {
		return tokenCenterAgent;
	}

	public void setTokenCenterAgent(TokenCenterAgent tokenCenterAgent) {
		this.tokenCenterAgent = tokenCenterAgent;
	}


	public DamsUcResetStarEXPTool getDamsUcResetStarEXPTool() {
		return damsUcResetStarEXPTool;
	}


	public void setDamsUcResetStarEXPTool(
			DamsUcResetStarEXPTool damsUcResetStarEXPTool) {
		this.damsUcResetStarEXPTool = damsUcResetStarEXPTool;
	}
	
	
	
	
}
