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
import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.protocol.UserInfoBinding;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ControlSwitchConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.LockConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.UcConstant;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年2月2日 上午11:14:45
 * @explain :用户账号绑定
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserInfoBindingServiceImpl extends AbstractUserBase {
	private static Logger logger = LogManager
			.getLogger(UserInfoBindingServiceImpl.class);
	private UserInfoDao userInfoDao;
	private Locker locker;
	private ControlAgent controlAgent;// 开关
	private TokenCenterAgent tokenCenterAgent;

	public TokenCenterAgent getTokenCenterAgent() {
		return tokenCenterAgent;
	}
	public void setTokenCenterAgent(TokenCenterAgent tokenCenterAgent) {
		this.tokenCenterAgent = tokenCenterAgent;
	}

	public ControlAgent getControlAgent() {
		return controlAgent;
	}

	public void setControlAgent(ControlAgent controlAgent) {
		this.controlAgent = controlAgent;
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

	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[用户安全信息绑定]_开始逻辑处理...");
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
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_SERVERCLOSE_CODE,
					ErrorCodeConstant.ERRORMSG_SERVERCLOSE_CODE);
		}
		long startTime = System.currentTimeMillis();
		UserInfoBinding userInfoBinding = null;
		// 数据检查
		try {
			userInfoBinding = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		// ##########################################################
		// 通过token验证用户合法性 @Damon 2015年8月11日15:42:59
		if (userInfoBinding.getToken() != null && !"whatthefucking".equals(userInfoBinding.getToken())) {
			String uid = tokenCenterAgent
					.getUserId(userInfoBinding.getToken());
			if (!(userInfoBinding.getUserID().toString()).equals(uid)) {// 非法用户，token与锁修改用户id不对应
				logger.warn("[" + reqID + "]@@ [未授权的操作！！！]");
				return ResponseTool.returnError(
						ErrorCodeConstant.SYSTEM_BUSINESS_ERROR_CODE,
						ErrorCodeConstant.ERRORMSG_UNLICENSED_CODE);
			}
		}
		// ##############################################################

		Map resMap = null;
		// 绑定新号码，解锁原号码
		if (userInfoBinding.getType()
				.equals(UcConstant.BINDINGTYPE_MOBILE_CODE)) {// 手机绑定
			resMap = this.bindingMobile(userInfoBinding, reqID);
		} else if (userInfoBinding.getType().equals(
				UcConstant.BINDINGTYPE_EMAIL_CODE)) {// 邮箱
			resMap = this.bindingEmail(userInfoBinding, reqID);
		} /*
		 * else if
		 * (userInfoBinding.getType().equals(UcConstant.BINDINGTYPE_GOODTTNUM_CODE
		 * )) {// 靓号 resMap = this.bindingGoodTTnum(userInfoBinding, reqID); }
		 */else {
			logger.info("[" + reqID + "]@@" + "[绑定失败_注册类型不存在！！！]");
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_PARAMETVIOLATE_CODE);
		}
		logger.info("[" + reqID + "]@@" + "[业务处理耗时(ms)]:"
				+ (System.currentTimeMillis() - startTime));
		return resMap;
	}

	/**
	 * 绑定手机
	 * 
	 * @param userInfoBinding
	 * @param reqID
	 * @return
	 */
	private Map bindingMobile(UserInfoBinding userInfoBinding, String reqID) {
		// 判断新手机是否可用（加锁）
		if (!locker.lockUntilFE(
				LockConstant.FIVE_MOBILE_LOCK + userInfoBinding.getValue(), 5)) {
			logger.info("[" + reqID + "]@@" + "[绑定失败_手机号:"
					+ userInfoBinding.getValue() + "已绑定5次！！！]");
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_BINDINGMOBILE_CODE);
		}
		// 查询原手机号码，看是否开通了登录功能
		BigInteger userID = new BigInteger(userInfoBinding.getUserID()
				.toString());
		UserInfo oldUserInfo = null;
		try {
			oldUserInfo = userInfoDao.selectUserInfoByUserId(userID);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据查询失败！！！" + e.getMessage());
			// TODO　解锁新手机（取消上面操作）
			if (!locker.releaseOneFE(LockConstant.FIVE_MOBILE_LOCK
					+ userInfoBinding.getValue())) {// 解锁bindingMobile
				logger.warn("[" + reqID + "]@@" + "[bindingMobile解锁失败:"
						+ userInfoBinding.getValue());
			}
		}
		// 创建修改对象
		UserInfo userInfo = new UserInfo();
		userInfo.setUserid(userID);
		userInfo.setBindingMobile(userInfoBinding.getValue());
		userInfo.setLoginMobile("");// 取消手机登录功能
		userInfo.setReqId(reqID);
		try {
			userInfoDao.updateUserInfo(userInfo);
		} catch (Exception e) {
			// TODO　解锁新手机（取消上面操作）、
			if (!locker.releaseOneFE(LockConstant.FIVE_MOBILE_LOCK
					+ userInfoBinding.getValue())) {// 解锁bindingMobile
				logger.warn("[" + reqID + "]@@" + "[bindingMobile解锁失败:"
						+ userInfoBinding.getValue());
			}
			logger.error("[" + reqID + "]@@" + "[绑定手机修改失败！！！]" + e.getMessage());
			return ResponseTool.returnException();
		}
		// 解锁原始绑定手机
		if (userInfo.getBindingMobile() != null) {
			if (!locker.releaseOneFE(LockConstant.FIVE_MOBILE_LOCK
					+ oldUserInfo.getBindingMobile())) {
				logger.warn("[" + reqID + "]@@" + "[bindingMobile解锁失败:"
						+ oldUserInfo.getBindingMobile());
			}
		}
		// 解锁登录手机
		if (userInfo.getLoginMobile() != null) {
			if (!locker.unlockUniqueFE(LockConstant.ONLYONE_MOBILE_LOCK
					+ oldUserInfo.getLoginMobile())) {
				logger.warn("[" + reqID + "]@@" + "[loginMobile解锁失败:"
						+ oldUserInfo.getLoginMobile());
			}
		}
		/**
		 * 返回结果
		 */
		Map resData = new HashMap();
		resData.put("result", 1);
		return ResponseTool.returnSuccess(resData);
	}

	/**
	 * 绑定email
	 * 
	 * @param userInfoBinding
	 * @param reqID
	 * @return
	 */
	private Map bindingEmail(UserInfoBinding userInfoBinding, String reqID) {
		// 判断新邮箱是否可用（加锁）
		if (!locker.lockUniqueFE(LockConstant.ONE_EMAIL_LOCK
				+ userInfoBinding.getValue())) {
			logger.info("[" + reqID + "]@@" + "[绑定失败_邮箱:"
					+ userInfoBinding.getValue() + "已绑定1次！！！]");
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_BINDINGEMAIL_CODE);
		}
		// 查询原手机号码，看是否开通了登录功能
		BigInteger userID = new BigInteger(userInfoBinding.getUserID()
				.toString());
		UserInfo oldUserInfo = null;
		try {
			oldUserInfo = userInfoDao.selectUserInfoByUserId(userID);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据查询失败！！！" + e.getMessage());
		}
		// 创建修改对象
		UserInfo userInfo = new UserInfo();
		userInfo.setUserid(userID);
		userInfo.setBindingEmail(userInfoBinding.getValue());
		userInfo.setLoginEmail("");// 取消邮箱登录功能
		userInfo.setReqId(reqID);
		try {
			userInfoDao.updateUserInfo(userInfo);
		} catch (Exception e) {
			// 解锁新手机（取消上面操作）
			logger.error("[" + reqID + "]@@" + "[绑定邮箱修改失败！！！]", e);
			return ResponseTool.returnException();
		}
		// 解锁绑定邮箱
		if (userInfo.getBindingEmail() != null) {
			if (!locker.releaseOneFE(LockConstant.ONE_EMAIL_LOCK
					+ oldUserInfo.getBindingEmail())) {
				logger.warn("[" + reqID + "]@@" + "[bindingEmail解锁失败:"
						+ oldUserInfo.getBindingEmail());
			}
		}
		// 解锁登录邮箱
		if (userInfo.getLoginEmail() != null) {
			if (!locker.unlockUniqueFE(LockConstant.ONLYONE_EMAIL_LOCK
					+ oldUserInfo.getLoginEmail())) {
				logger.warn("[" + reqID + "]@@" + "[loginEmail解锁失败:"
						+ oldUserInfo.getLoginEmail());
			}
		}
		/**
		 * 返回结果
		 */
		Map resData = new HashMap();
		resData.put("result", 1);
		return ResponseTool.returnSuccess(resData);

	}

	/**
	 * 数据检查
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private UserInfoBinding checkData(Object object) throws Exception {
		UserInfoBinding userInfoBinding = (UserInfoBinding) object;
		if (userInfoBinding == null) {
			throw new Exception("[对象转换失败！！！]");
		}
		if (userInfoBinding.getUserID() == null) {
			throw new Exception("[用户账号绑定_userID为空！！！]");
		}
		if (userInfoBinding.getType() == null) {
			throw new Exception("[用户账号绑定_绑定类型 type为空！！！]");
		}
		if (userInfoBinding.getValue() == null
				|| "".equals(userInfoBinding.getValue())) {
			throw new Exception("[用户账号绑定_绑定值 value为空！！！]");
		}
		if (userInfoBinding.getToken() == null
				|| "".equals(userInfoBinding.getToken())) {
			logger.warn("[用户账号绑定_token 为空！！！]");
			throw new Exception("[用户账号绑定_token 为空！！！]");
		}

		return userInfoBinding;
	}

}
