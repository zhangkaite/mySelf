package com.ttmv.datacenter.usercenter.service.facade.impl.userBasic;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.agent.control.Control;
import com.ttmv.datacenter.agent.control.ControlAgent;
import com.ttmv.datacenter.agent.lockcenter.Locker;
import com.ttmv.datacenter.agent.tokencenter.TokenCenterAgent;
import com.ttmv.datacenter.sentry.SentryAgent;
import com.ttmv.datacenter.usercenter.dao.implement.util.JsonUtil;
import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.protocol.LoginBinding;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ControlSwitchConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.LockConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.UcConstant;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年2月2日 上午10:44:08
 * @explain :用户登录方式开通
 */
@SuppressWarnings({ "rawtypes" })
public class LoginBindingServiceImpl extends AbstractUserBase {
	private static Logger logger = LogManager
			.getLogger(LoginBindingServiceImpl.class);
	private UserInfoDao userInfoDao;
	private Locker locker;
	private ControlAgent controlAgent;// 开关
	private TokenCenterAgent tokenCenterAgent;
	private SentryAgent overdueManageTTnum;
	
	public SentryAgent getOverdueManageTTnum() {
		return overdueManageTTnum;
	}

	public void setOverdueManageTTnum(SentryAgent overdueManageTTnum) {
		this.overdueManageTTnum = overdueManageTTnum;
	}

	public TokenCenterAgent getTokenCenterAgent() {
		return tokenCenterAgent;
	}

	public void setTokenCenterAgent(TokenCenterAgent tokenCenterAgent) {
		this.tokenCenterAgent = tokenCenterAgent;
	}

	public Locker getLocker() {
		return locker;
	}

	public void setLocker(Locker locker) {
		this.locker = locker;
	}

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public ControlAgent getControlAgent() {
		return controlAgent;
	}

	public void setControlAgent(ControlAgent controlAgent) {
		this.controlAgent = controlAgent;
	}

	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[登录方式开通]_开始逻辑处理...");
		// 分布式锁开关检测
		String onOff2 = null;
		try {
			onOff2 = controlAgent
					.getInstruction(ControlSwitchConstant.SWITCH_LOCK_CODE);
		} catch (Exception e) {
			logger.warn("[" + reqID + "]@@" + "开关读取失败！！！" + e.getMessage());
			onOff2 = Control.CONTROL_AGENT_START;
		}
		
		if (Control.CONTROL_AGENT_STOP.equals(onOff2)) {
			logger.warn("[" + reqID + "]@@ [分布式锁开关控制_服务关闭！！！]");
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_SERVERCLOSE_CODE,ErrorCodeConstant.ERRORMSG_SERVERCLOSE_CODE);
		}
		
		long startTime = System.currentTimeMillis();
		// 数据检查
		LoginBinding loginBinding = null;
		try {
			loginBinding = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		// #################################################################
		// 通过token验证用户合法性 @Damon 2015年8月11日14:23:22
		if (loginBinding.getToken() != null && !"whatthefucking".equals(loginBinding.getToken())) {
			String uid = tokenCenterAgent.getUserId(loginBinding.getToken());
			if (!(loginBinding.getUserID().toString()).equals(uid)) {// 非法用户，token与锁修改用户id不对应
				logger.warn("[" + reqID + "]@@ [未授权的操作！！！]");
				return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_BUSINESS_ERROR_CODE,ErrorCodeConstant.ERRORMSG_UNLICENSED_CODE);
			}
		}
		// ##############################################################

		Map resMap = new HashMap();
		// 开通登录
		if (loginBinding.getType().equals(UcConstant.OPENTYPE_MOBILE_CODE)) {// 手机登录开通
			resMap = this.openMobileLogin(loginBinding, reqID);
		} else if (loginBinding.getType().equals(UcConstant.OPENTYPE_EMAIL_CODE)) {// 邮箱登录开通
			resMap = this.openEmailLogin(loginBinding, reqID);
		} else if (loginBinding.getType().equals(UcConstant.OPENTYPE_GOODTTNUM_CODE)) {// 靓号开通登录
			resMap = this.openGoodTTnumLogin(loginBinding, reqID);
		} else {
			logger.error("[" + reqID + "]@@" + "[用户登录方式开通失败_号码类型不存在！！！]");
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETVIOLATE_CODE);
		}
		
		if(resMap.get("resultCode").equals("AAAAAAA")){
			//Damon_2015年12月10日17:54:03 绑定靓号成功，通知到期业务系统，处理到期时间状态修改
			String jsonData = "";
			try {
				jsonData = getJsonData(loginBinding);
			} catch (Exception e) {
				logger.warn("会员倒计时jsonData组装失败！！！",e);
			}
			String resData = overdueManageTTnum.expressSendHttp("data="+jsonData);
			Map resMaap = null;
			try {
				resMaap = (Map) JsonUtil.getObjectFromJson(resData, Map.class);
			} catch (Exception e) {
				logger.error("json转换失败！！！");
			}
			if(resMaap.get("resultCode").equals("AAAAAAA")){
				logger.info("[" + reqID + "]@@" + "[用户登录方式开通业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
			}else{
				logger.error("靓号绑定处理失败，错误代码:"+resMaap.get("resultCode"));
				logger.error("[到期系统上报消息上报失败:]====>>>" + "data="+jsonData);
			}
		}else{
			logger.error("开通登录失败！！！");
		}
		return resMap;
	}
	/**
	 * 拼jsonData
	 * @param openingVIP
	 * @return
	 * @throws Exception
	 */
	private String getJsonData(LoginBinding loginBinding) throws Exception{
		Map openingVip = new HashMap<String,Object>();
		openingVip.put("userID", loginBinding.getUserID());
		openingVip.put("type","OFF");
		openingVip.put("goodTTnum", loginBinding.getValue());
		return JsonUtil.getObjectToJson(openingVip);
		
	}

	/**
	 * 开通登录
	 * 
	 * @param loginBinding
	 * @param reqID
	 * @return
	 */
	private Map openMobileLogin(LoginBinding loginBinding, String reqID) {
		// 加锁登录手机
		if (!locker.lockUniqueFE(LockConstant.ONLYONE_MOBILE_LOCK+ loginBinding.getValue())) {
			logger.info("[" + reqID + "]@@" + "[开通登录失败_手机号:"+ loginBinding.getValue() + "已经注册！！！]");
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_LOGINMOBILE_CODE);
		}
		BigInteger userID = new BigInteger(loginBinding.getUserID().toString());
		// 查询原来对象
		UserInfo oldUserInfo = null;
		try {
			oldUserInfo = userInfoDao.selectUserInfoByUserId(userID);
		} catch (Exception e) {
			// 解锁
			if (!locker.unlockUniqueFE(LockConstant.ONLYONE_MOBILE_LOCK+ loginBinding.getValue())) {// 解锁LoginMobile
				logger.warn("[" + reqID + "]@@" + "[loginMobile解锁失败:"+ loginBinding.getValue());
			}
			logger.error("[" + reqID + "]@@" + "数据查询失败！！！", e);
			return ResponseTool.returnException();
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setUserid(userID);
		userInfo.setLoginMobile(loginBinding.getValue());
		userInfo.setReqId(reqID);// 业务流水号
		try {
			userInfoDao.updateUserInfo(userInfo);
		} catch (Exception e) {
			// 解锁
			if (!locker.unlockUniqueFE(LockConstant.ONLYONE_MOBILE_LOCK+ loginBinding.getValue())) {// 解锁LoginMobile
				logger.warn("[" + reqID + "]@@" + "[loginMobile解锁失败:"+ loginBinding.getValue());
			}
			logger.error("[" + reqID + "]@@" + "[用户信息修改失败！！！]", e);
			return ResponseTool.returnException();
		}
		// 查询原登录手机，解锁
		if (!locker.unlockUniqueFE(LockConstant.ONLYONE_MOBILE_LOCK + oldUserInfo.getLoginMobile())) {// 解锁LoginMobile
			logger.warn("[" + reqID + "]@@" + "[loginMobile解锁失败:"+ oldUserInfo.getLoginMobile());
		}
		return ResponseTool.returnSuccess(null);
	}

	/**
	 * 邮箱登陆开通
	 * 
	 * @param loginBinding
	 * @param reqID
	 * @return
	 */
	private Map openEmailLogin(LoginBinding loginBinding, String reqID) {
		// 加锁登录手机
		if (!locker.lockUniqueFE(LockConstant.ONLYONE_EMAIL_LOCK
				+ loginBinding.getValue())) {
			logger.info("[" + reqID + "]@@" + "[开通登录失败_邮箱:"
					+ loginBinding.getValue() + "已经注册！！！]");
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_LOGINEMAIL_CODE);
		}
		BigInteger userID = new BigInteger(loginBinding.getUserID().toString());
		// 查询原来对象
		UserInfo oldUserInfo = null;
		try {
			oldUserInfo = userInfoDao.selectUserInfoByUserId(userID);
		} catch (Exception e) {
			// 解锁
			if (!locker.unlockUniqueFE(LockConstant.ONLYONE_EMAIL_LOCK
					+ loginBinding.getValue())) {
				logger.warn("[" + reqID + "]@@" + "[loginMobile解锁失败:"
						+ loginBinding.getValue());
			}
			logger.error("[" + reqID + "]@@" + "数据查询失败！！！", e);
			return ResponseTool.returnException();
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setUserid(userID);
		userInfo.setLoginEmail(loginBinding.getValue());
		userInfo.setReqId(reqID);// 业务流水号
		try {
			userInfoDao.updateUserInfo(userInfo);
		} catch (Exception e) {
			// 解锁
			if (!locker.unlockUniqueFE(LockConstant.ONLYONE_EMAIL_LOCK
					+ loginBinding.getValue())) {
				logger.warn("[" + reqID + "]@@" + "[loginEmail解锁失败:"
						+ loginBinding.getValue());
			}
			logger.error("[" + reqID + "]@@" + "[用户信息修改失败！！！]", e);
			return ResponseTool.returnException();
		}
		// 查询原登录邮箱，解锁
		if (!locker.unlockUniqueFE(LockConstant.ONLYONE_EMAIL_LOCK
				+ oldUserInfo.getLoginEmail())) {// 解锁LoginMobile
			logger.warn("[" + reqID + "]@@" + "[loginEmail解锁失败:"
					+ oldUserInfo.getLoginEmail());
		}
		return ResponseTool.returnSuccess(null);
	}

	/**
	 * 靓号登录开通
	 * 
	 * @param loginBinding
	 * @param reqID
	 * @return
	 */
	private Map openGoodTTnumLogin(LoginBinding loginBinding, String reqID) {
		// 加锁登录靓号
		if (!locker.lockUniqueFE(LockConstant.ONLYONE_GOODTTNUM_LOCK
				+ loginBinding.getValue())) {
			logger.info("[" + reqID + "]@@" + "[靓号开通登录失败_:"
					+ loginBinding.getValue() + "该靓号已经被使用！！！]");
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_LOGINGOODTTNUM_CODE);
		}
		BigInteger userID = new BigInteger(loginBinding.getUserID().toString());
		// 查询原来对象
		UserInfo oldUserInfo = null;
		try {
			oldUserInfo = userInfoDao.selectUserInfoByUserId(userID);
		} catch (Exception e) {
			// 解锁

			if (!locker.unlockUniqueFE(LockConstant.ONLYONE_GOODTTNUM_LOCK
					+ loginBinding.getValue())) {// 解锁loginGoogTTnum
				logger.warn("[" + reqID + "]@@" + "[loginGoogTTnum解锁失败:"
						+ loginBinding.getValue());
			}
			logger.error("[" + reqID + "]@@" + "原始靓号号数据查询失败！！！", e);
			return ResponseTool.returnException();
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setUserid(userID);
		userInfo.setLoginGoodTTnum(loginBinding.getValue());
		userInfo.setLoginGoodTTnumType(UcConstant.GOODTTNUMTYPE_Y);// 靓号默认状态
		userInfo.setReqId(reqID);// 业务流水号
		try {
			userInfoDao.updateUserInfo(userInfo);
		} catch (Exception e) {
			// 解锁
			if (!locker.unlockUniqueFE(LockConstant.ONLYONE_GOODTTNUM_LOCK
					+ loginBinding.getValue())) {// 解锁loginGoogTTnum
				logger.warn("[" + reqID + "]@@" + "[loginGoogTTnum解锁失败:"
						+ loginBinding.getValue());
			}
			logger.error("[" + reqID + "]@@ [靓号绑定失败！！！]", e);
			return ResponseTool.returnException();
		}
		// 查询原绑定靓号，解锁
		if (!locker.unlockUniqueFE(LockConstant.ONLYONE_GOODTTNUM_LOCK
				+ oldUserInfo.getLoginGoodTTnum())) {// 解锁loginGoogTTnum
			logger.warn("[" + reqID + "]@@" + "[loginGoogTTnum解锁失败:"
					+ oldUserInfo.getLoginGoodTTnum());
		}
		return ResponseTool.returnSuccess(null);
	}

	/**
	 * 数据检查
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private LoginBinding checkData(Object object) throws Exception {
		LoginBinding loginBinding = (LoginBinding) object;
		if (loginBinding == null) {
			throw new Exception("对象转换失败！！！");
		}
		if (loginBinding.getUserID() == null) {
			throw new Exception("[UserID 为空！！！]");
		}
		if (loginBinding.getType() == null) {
			throw new Exception("[类型 type 为空！！！]");
		}
		if (loginBinding.getValue() == null) {
			throw new Exception("[Value 为空！！！]");
		}
		if (loginBinding.getToken() == null
				|| "".equals(loginBinding.getToken())) {
			logger.warn("[登录方式开通_token 为空！！！]");
			throw new Exception("[登录方式开通_token 为空！！！]");
		}
		
		return loginBinding;
	}

}
