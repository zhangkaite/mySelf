package com.ttmv.datacenter.usercenter.service.facade.impl.userBasic;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.ttmv.datacenter.agent.control.Control;
import com.ttmv.datacenter.agent.control.ControlAgent;
import com.ttmv.datacenter.agent.lockcenter.Locker;
import com.ttmv.datacenter.generator.ttnum.TTnumGenerator;
import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.protocol.AddUser;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.HadoopAddUserTool;
import com.ttmv.datacenter.usercenter.service.facade.tools.InitUserAccountTool;
import com.ttmv.datacenter.usercenter.service.facade.tools.MsgSenderTool;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ControlSwitchConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.LockConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.RegexConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.UcConstant;
import com.ttmv.datacenter.usercenter.service.processor.tools.UserIdGenerate;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年1月20日 下午3:22:55
 * @explain : 用户注册
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AddUserServiceImpl extends AbstractUserBase {
	private static Logger logger = LogManager
			.getLogger(AddUserServiceImpl.class);

	private UserIdGenerate userIdGenerate;// userID生成接口
	private TTnumGenerator tTnumGenerator;// TT号生成接口
	private ControlAgent controlAgent;// 开关
	private Locker locker;// 锁
	private UserInfoDao userInfoDao;// 入库接口
	private MsgSenderTool msgSenderTool;// MQ消息发送（初始化好友分组）
	private InitUserAccountTool initUserAccountTool;// MQ消息发送（初始化资金账户）
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMddHH");
	//Damon_2015年7月29日09:28:58 (hadoop统计分析队列)
	private HadoopAddUserTool hadoopAddUserTool; 

	/**
	 * 用户注册业务逻辑处理
	 * 
	 * @param object
	 * @param reqID
	 * @return Map
	 * @exception Exception
	 */
	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[注册]_开始逻辑处理...");
		// 接口服务开关检测
		String onOff = null;
		try {
			onOff = controlAgent
					.getInstruction(ControlSwitchConstant.SWITCH_ADDUSER_CODE);
		} catch (Exception e) {
			logger.warn("[" + reqID + "]@@" + "开关读取失败！！！" + e.getMessage());
			onOff = Control.CONTROL_AGENT_START;
		}
		if (Control.CONTROL_AGENT_STOP.equals(onOff)) {
			logger.warn("[" + reqID + "]@@ [用户注册开关控制_服务关闭！！！]");
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_SERVERCLOSE_CODE,
					ErrorCodeConstant.ERRORMSG_SERVERCLOSE_CODE);
		}
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
		// 数据检查
		AddUser addUser = null;
		try {
			addUser = checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_", e);
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		// TODO ===========Damon_2015年3月27日16:47:18==============
		if (addUser.getUserName() != null) {
			String userName = addUser.getUserName();
			if (!(userName.matches(RegexConstant.REGEX_USERNAME_0)
					&& userName.matches(RegexConstant.REGEX_USERNAME_1)
					&& userName.matches(RegexConstant.REGEX_USERNAME_2) && userName
						.matches(RegexConstant.REGEX_USERNAME_3))) {// 正则校验userName
				logger.error("[账号注册_userName 非法,用户名规则为 首字母小写，其他字符数字组合，长度6-25！！！]");
				return ResponseTool.returnError(
						ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
						ErrorCodeConstant.ERROR_UNAME_VALIDATION_CODE);
			}
		}
		if (addUser.getMobile() != null) {
			String phone = addUser.getMobile();
			if (!phone.matches(RegexConstant.REGEX_MOBILE)) {// 正则表达式验证手机格式
				logger.error("[手机号码格式非法！！！]");
				return ResponseTool.returnError(
						ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
						ErrorCodeConstant.ERROR_PHONE_VALIDATION_CODE);
			}
		}
		if (addUser.getEmail() != null) {
			String email = addUser.getEmail();
			if (!email.matches(RegexConstant.REGEX_EMAIL)) {// 正则表达式验证邮箱格式
				logger.error("[邮箱格式非法！！！]");
				return ResponseTool.returnError(
						ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
						ErrorCodeConstant.ERROR_EMAIL_VALIDATION_CODE);
			}
		}
		// =====================

		// userInfo 创建
		UserInfo userInfo = null;
		try {
			userInfo = createUserInfo(addUser, reqID);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "UserInfo对象创建失败_", e);
			return ResponseTool.returnException();
		}
		// 根据注册类型注册
		Map resMap = new HashMap();
		if (addUser.getType().equals(UcConstant.ADDUSER_USERNAME_CODE))// 账号注册
			resMap = userNameRegister(userInfo, reqID);
		else if (addUser.getType().equals(UcConstant.ADDUSER_PHONE_CODE))// 手机注册
			resMap = mobileRegister(userInfo, reqID);
		else if (addUser.getType().equals(UcConstant.ADDUSER_EMAIL_CODE))// 邮箱注册
			resMap = emailRegister(userInfo, reqID);
		else {// 注册类型不存在
			logger.error("[" + reqID + "]@@" + "[注册处理失败_注册类型不存在！！！]");
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_PARAMETVIOLATE_CODE);
		}
		// TODO 资金账户初始化队列
		if (resMap.get("resultCode").equals(ErrorCodeConstant.SYSTEM_SUCCESS_CODE)) {
			try {
				long mqTime = System.currentTimeMillis();
				initUserAccountTool.sendMessage(this.createMsginitAccount(userInfo));
				logger.debug("[" + reqID + "]@@"+ " && [init_Account ---->>> mq耗时(ms)]:" + (System.currentTimeMillis() - mqTime));
			} catch (Exception e) {
				logger.warn("[" + reqID + "]@@" + "初始化资金账户添加队列失败！！！", e);
			}
			//hadoop统计分析队列 2015年8月17日10:23:48 线上版本先屏蔽
//			try {
//				Map aUserMap = new HashMap<String,Object>();
//				aUserMap.put("service", "hadoopAddUser");
//				aUserMap.put("reqID", reqID);
//				aUserMap.put("data",addUser );
//				long mqTime = System.currentTimeMillis();
//				hadoopAddUserTool.sendMessage(JsonUtil.getObjectToJson(aUserMap));
//				logger.debug("[" + reqID + "]@@"+ " && [hadoop_AddUser ---->>> mq耗时(ms)]:" + (System.currentTimeMillis() - mqTime));
//			} catch (Exception e) {
//				logger.warn("[" + reqID + "]@@" + "hadoopAddUser添加队列失败！！！", e);
//			}
		}
		
		// 用户行为统计统计新注册的用户
		logger.info("userActionLog@@userAdd@@"+sdfs.format(new Date())+"@@"+userInfo.getEnrollterminal()+"@@"+userInfo.getUserid()+"@@"+sdf.format(new Date()));

		logger.info("[" + reqID + "]@@" + "[注册业务处理耗时(ms)]:"
				+ (System.currentTimeMillis() - startTime));
		return resMap;
	}

	/**
	 * 注册用户对象赋值
	 * 
	 * @param userInfo
	 * @param reqID
	 * @param addUser
	 * @return
	 * @throws Exception
	 */
	private UserInfo createUserInfo(AddUser addUser, String reqID)
			throws Exception {
		UserInfo userInfo = new UserInfo();
		BigInteger TTnum = null;
		/**
		 * 生成TTnum
		 */
		TTnum = tTnumGenerator.generateSegmentTTnum();
		if (TTnum == null || "".equals(TTnum)) {
			throw new Exception("[TT号生成失败!!!]");
		}
		userInfo.setTTnum(TTnum);// TT号
		if (addUser.getType().equals(UcConstant.ADDUSER_USERNAME_CODE)) {
			userInfo.setUserName(addUser.getUserName());
			userInfo.setBindingMobile(addUser.getMobile());
			if (addUser.getNickName() == null) {
				userInfo.setNickName("user" + TTnum);
			} else {
				userInfo.setNickName(addUser.getNickName());
			}
		} else if (addUser.getType().equals(UcConstant.ADDUSER_PHONE_CODE)) {
			userInfo.setBindingMobile(addUser.getMobile());
			userInfo.setLoginMobile(addUser.getMobile());
			userInfo.setUserName(TTnum + "tt");
			userInfo.setNickName("user" + TTnum);
		} else if (addUser.getType().equals(UcConstant.ADDUSER_EMAIL_CODE)) {
			userInfo.setLoginEmail(addUser.getEmail());
			userInfo.setBindingEmail(addUser.getEmail());
			userInfo.setUserName(TTnum + "tt");
			userInfo.setNickName("user" + TTnum);
		}
		userInfo.setVipType(UcConstant.VIPTYPE_NO);// 非会员
		userInfo.setPassword(addUser.getPassword());// 密码
		userInfo.setSex(addUser.getSex());// 性别
		userInfo.setUserPhoto(addUser.getUserPhoto());// 头像
		userInfo.setExp(new BigInteger("0"));// 经验值
		
		//Damon 2015年12月16日19:59:06
		userInfo.setUserLevel(0);
		userInfo.setAnnouncerType(UcConstant.ANNOUNCERTYPE_N);
		
		
		userInfo.setState(UcConstant.USTATE_NORMAL);// 状态(0正常)
		userInfo.setUtype(UcConstant.UTYPE_GENERAL_CODE);// 类型
		//Damon 2015年6月16日15:26:12
		userInfo.setTime(unixTimeFmt(Long.parseLong(addUser.getTime())));// 注册时间
		userInfo.setEnrollterminal(addUser.getEnrollTerminal());// 注册端
		userInfo.setDndType(UcConstant.DNDTYPE_ALL);// 默认所有人添加（防骚扰）

		userInfo.setReqId(reqID);// 业务流水号
		return userInfo;
	}

	/**
	 * Unix时间戳转java date
	 * @param 2015年6月16日15:25:12 Damon
	 * @return
	 * @throws ParseException
	 */
	public static Date unixTimeFmt(long time) throws ParseException {
		String dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(time * 1000));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(dt);

	}

	/**
	 * 拼装返回信息
	 * 
	 * @param userInfo
	 * @return
	 */
	private Map takeResData(UserInfo userInfo) {
		Map resData = new HashMap();
		resData.put("userName", userInfo.getUserName());
		resData.put("TTnum", userInfo.getTTnum());
		resData.put("userID", userInfo.getUserid());
		return resData;
	}

	/**
	 * 拼装队列消息
	 * 
	 * @param userInfo
	 * @param login
	 * @return
	 * @throws Exception
	 */
	private String createMsg(UserInfo userInfo) throws Exception {
		Map resMap = new HashMap();
		resMap.put("msgType", "addUser");
		resMap.put("userID", userInfo.getUserid());
		resMap.put("reqId", userInfo.getReqId());
		return new ObjectMapper().writeValueAsString(resMap);
	}

	/**
	 * 创建异步队列（用户资金账户初始化）
	 * 
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	private String createMsginitAccount(UserInfo userInfo) throws Exception {
		Map resMap = new HashMap();
		resMap.put("msgType", "initAccount");
		resMap.put("userID", userInfo.getUserid());
		resMap.put("reqId", userInfo.getReqId());
		return new ObjectMapper().writeValueAsString(resMap);
	}

	/**
	 * 用户名注册
	 * 
	 * @param userInfo
	 * @param reqID
	 * @param addUser
	 * @return
	 * @throws Exception
	 */
	private Map userNameRegister(UserInfo userInfo, String reqID) {
		logger.debug("[" + reqID + "]@@" + "===用户名注册===");
		// 用户名加锁
		logger.debug("[" + reqID + "]@@" + "[userName加锁...]"
				+ userInfo.getUserName());
		if (!locker.lockUniqueFE(LockConstant.ONLYONE_USERNAME_LOCK
				+ userInfo.getUserName())) {// 用户名已注册(永久锁)
			logger.info("[" + reqID + "]@@" + "[注册处理失败_用户名:"
					+ userInfo.getUserName() + "已经注册！！！]");
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_USERNAME_CODE);
		}
		// 绑定手机加锁
		logger.debug("[" + reqID + "]@@" + "[绑定手机加锁...]"
				+ userInfo.getBindingMobile());
		if (!locker.lockUntilFE(
				LockConstant.FIVE_MOBILE_LOCK + userInfo.getBindingMobile(), 5)) {
			logger.info("[" + reqID + "]@@" + "[注册处理失败_手机号:"
					+ userInfo.getBindingMobile() + "已绑定5次！！！]");
			logger.debug("[" + reqID + "]@@" + "[userName撤销加锁...]"
					+ userInfo.getUserName());
			if (!locker.unlockUniqueFE(LockConstant.ONLYONE_USERNAME_LOCK
					+ userInfo.getUserName())) {// 解锁userName
				logger.warn("[" + reqID + "]@@" + "[userName解锁失败:"
						+ userInfo.getUserName());
			}
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_BINDINGMOBILE_CODE);
		}

		/**
		 * 生成userID
		 */
		try {
			logger.debug("[" + reqID + "]@@" + "[开始生成userID...]");
			BigInteger userID = userIdGenerate.getUserId();
			logger.debug("[" + reqID + "]@@" + "[生成userID]:" + userID);
			if (userID == null) {
				throw new Exception("[生成的userID为NULL!!!]");
			}
			userInfo.setUserid(userID);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "[userID生成异常!!!]", e);
		}
		// 注册信息入库
		try {
			logger.debug("[" + reqID + "]@@" + "[注册信息入库（调用dao层）...]");
			int in = userInfoDao.addUserInfo(userInfo);
			if (in == 0) {// 返回不直接成功
				logger.warn("[" + reqID + "]@@" + "[警告:注册被受理，但未直接返回成功！！！]");
			}
		} catch (Exception e) {
			// 入库失败，解锁
			logger.error("[" + reqID + "]@@" + "[注册失败]", e);
			logger.debug("[" + reqID + "]@@" + "[入库失败，解锁userName...]"
					+ userInfo.getUserName());
			if (!locker.unlockUniqueFE(LockConstant.ONLYONE_USERNAME_LOCK
					+ userInfo.getUserName())) {// 解锁userName
				logger.warn("[" + reqID + "]@@" + "[userName解锁失败:"
						+ userInfo.getUserName());
			}
			logger.debug("[" + reqID + "]@@" + "[入库失败，解锁bindingMobile...]"
					+ userInfo.getBindingMobile());
			if (!locker.releaseOneFE(LockConstant.FIVE_MOBILE_LOCK
					+ userInfo.getBindingMobile())) {// 解锁bindingMobile
				logger.warn("[" + reqID + "]@@" + "[bindingMobile解锁失败:"
						+ userInfo.getBindingMobile());
			}
			return ResponseTool.returnException();
		}
		// TODO 异步队列记录注册(初始化用户好友分组.)
		try {
			logger.debug("[" + reqID + "]@@" + "[异步队列(初始化用户好友分组.)...]");
			long mqTime = System.currentTimeMillis();
			msgSenderTool.sendMessage(this.createMsg(userInfo));
			logger.debug("[" + reqID + "]@@"+ " && [init_好友组 ---->>> mq耗时(ms)]:" + (System.currentTimeMillis() - mqTime));
		} catch (Exception e) {
			logger.warn("[" + reqID + "]@@" + "队列发送数据失败！！！", e);
		}
		// 注册完成,拼装返回信息
		return ResponseTool.returnSuccess(takeResData(userInfo));

	}

	/**
	 * 手机注册
	 * 
	 * @param userInfo
	 * @param reqID
	 * @param addUser
	 * @return
	 * @throws Exception
	 */
	private Map mobileRegister(UserInfo userInfo, String reqID) {
		logger.debug("[" + reqID + "]@@" + "===手机注册===");
		// 注册手机加锁
		logger.debug("[" + reqID + "]@@" + "[注册手机加锁...]"
				+ userInfo.getLoginMobile());
		if (!locker.lockUniqueFE(LockConstant.ONLYONE_MOBILE_LOCK
				+ userInfo.getLoginMobile())) {
			logger.info("[" + reqID + "]@@" + "[注册处理失败_手机号:"
					+ userInfo.getLoginMobile() + "已经注册！！！]");
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_LOGINMOBILE_CODE);
		}
		// 绑定手机加锁
		logger.debug("[" + reqID + "]@@" + "[绑定手机加锁...]"
				+ userInfo.getBindingMobile());
		if (!locker.lockUntilFE(
				LockConstant.FIVE_MOBILE_LOCK + userInfo.getBindingMobile(), 5)) {
			logger.info("[" + reqID + "]@@" + "[注册处理失败_手机号:"
					+ userInfo.getBindingMobile() + "已绑定5次！！！]");
			logger.debug("[" + reqID + "]@@" + "[注册手机撤销加锁...]"
					+ userInfo.getBindingMobile());
			if (!locker.unlockUniqueFE(LockConstant.ONLYONE_MOBILE_LOCK
					+ userInfo.getBindingMobile())) {// 解锁LoginMobile
				logger.warn("[" + reqID + "]@@" + "[LoginMobile解锁失败:"
						+ userInfo.getBindingMobile());
			}
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_BINDINGMOBILE_CODE);
		}

		/**
		 * 生成userID
		 */
		try {
			logger.debug("[" + reqID + "]@@" + "[开始生成userID...]");
			BigInteger userID = userIdGenerate.getUserId();
			logger.debug("[" + reqID + "]@@" + "[生成userID]:" + userID);
			if (userID == null) {
				throw new Exception("[生成的userID为NULL!!!]");
			}
			userInfo.setUserid(userID);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "[userID生成异常!!!]", e);
		}
		// 注册信息入库
		try {
			logger.debug("[" + reqID + "]@@" + "[注册信息入库（调用dao层）...]");
			if (userInfoDao.addUserInfo(userInfo) == 0) {// 返回不直接成功
				logger.warn("[" + reqID + "]@@" + "[警告:注册被受理，但未直接返回成功！！！]");
			}
			// TODO 异步队列记录注册(初始化用户好友分组.)
			try {
				logger.debug("[" + reqID + "]@@" + "[异步队列(初始化用户好友分组.)...]");
				long mqTime = System.currentTimeMillis();
				msgSenderTool.sendMessage(this.createMsg(userInfo));
				logger.debug("[" + reqID + "]@@"+ " && [init_好友组 ---->>> mq耗时(ms)]:" + (System.currentTimeMillis() - mqTime));
			} catch (Exception e) {
				logger.warn("[" + reqID + "]@@" + "队列发送数据失败！！！", e);
			}
			// 注册完成,拼装返回信息
			return ResponseTool.returnSuccess(takeResData(userInfo));
		} catch (Exception e) {
			// 入库失败，解锁
			logger.error("[" + reqID + "]@@" + "[注册失败]", e);
			logger.debug("[" + reqID + "]@@" + "[入库失败，解锁LoginMobile...]"
					+ userInfo.getLoginMobile());
			if (!locker.unlockUniqueFE(LockConstant.ONLYONE_MOBILE_LOCK
					+ userInfo.getLoginMobile())) {// 解锁LoginMobile
				logger.warn("[" + reqID + "]@@" + "[LoginMobile解锁失败:"
						+ userInfo.getLoginMobile());
			}
			logger.debug("[" + reqID + "]@@" + "[入库失败，解锁bindingMobile...]"
					+ userInfo.getBindingMobile());
			if (!locker.releaseOneFE(LockConstant.FIVE_MOBILE_LOCK
					+ userInfo.getBindingMobile())) {// 解锁bindingMobile
				logger.warn("[" + reqID + "]@@" + "[bindingMobile解锁失败:"
						+ userInfo.getBindingMobile());
			}
			return ResponseTool.returnException();
		}
	}

	/**
	 * 邮箱注册
	 * 
	 * @param userInfo
	 * @param reqID
	 * @param addUser
	 * @return
	 * @throws Exception
	 */
	private Map emailRegister(UserInfo userInfo, String reqID) {
		logger.debug("[" + reqID + "]@@" + "===邮箱注册===");
		// 注册邮箱加锁
		logger.debug("[" + reqID + "]@@" + "[注册邮箱加锁...]"
				+ userInfo.getLoginEmail());
		if (!locker.lockUniqueFE(LockConstant.ONLYONE_EMAIL_LOCK
				+ userInfo.getLoginEmail())) {
			logger.info("[" + reqID + "]@@" + "[注册处理失败_邮箱:"
					+ userInfo.getLoginEmail() + "已经注册！！！]");
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_LOGINEMAIL_CODE);
		}
		// 绑定邮箱加锁
		logger.debug("[" + reqID + "]@@" + "[绑定邮箱加锁...]"
				+ userInfo.getBindingEmail());
		if (!locker.lockUntilFE(
				LockConstant.ONE_EMAIL_LOCK + userInfo.getBindingEmail(), 1)) {
			logger.info("[" + reqID + "]@@" + "[注册处理失败_邮箱:"
					+ userInfo.getBindingEmail() + "已绑定1次！！！]");
			logger.debug("[" + reqID + "]@@" + "[解锁LoginEmail...]"
					+ userInfo.getLoginEmail());
			if (!locker.unlockUniqueFE(LockConstant.ONLYONE_EMAIL_LOCK
					+ userInfo.getLoginEmail())) {// 解锁LoginEmail
				logger.warn("[" + reqID + "]@@" + "[LoginEmail解锁失败:"
						+ userInfo.getLoginEmail());
			}
			return ResponseTool.returnError(
					ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_BINDINGEMAIL_CODE);
		}
		/**
		 * 生成userID
		 */
		try {
			logger.debug("[" + reqID + "]@@" + "[开始生成userID...]");
			BigInteger userID = userIdGenerate.getUserId();
			logger.debug("[" + reqID + "]@@" + "[生成userID]:" + userID);
			if (userID == null) {
				throw new Exception("[生成的userID为NULL!!!]");
			}
			userInfo.setUserid(userID);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "[userID生成异常!!!]", e);
		}
		// 注册信息入库
		try {
			logger.debug("[" + reqID + "]@@" + "[注册信息入库（调用dao层）...]");
			if (userInfoDao.addUserInfo(userInfo) == 0) {// 返回不直接成功
				logger.warn("[" + reqID + "]@@" + "[警告:注册被受理，但未直接返回成功！！！]");
			}
			// TODO 异步队列记录注册(初始化用户好友分组.)
			try {
				logger.debug("[" + reqID + "]@@" + "[异步队列(初始化用户好友分组.)...]");
				long mqTime = System.currentTimeMillis();
				msgSenderTool.sendMessage(this.createMsg(userInfo));
				logger.debug("[" + reqID + "]@@"+ " && [init_好友组 ---->>> mq耗时(ms)]:" + (System.currentTimeMillis() - mqTime));
			} catch (Exception e) {
				logger.warn("[" + reqID + "]@@" + "队列数据失败！！！", e);
			}
			// 注册完成,拼装返回信息
			return ResponseTool.returnSuccess(takeResData(userInfo));
		} catch (Exception e) {
			// 入库失败，解锁
			logger.error("[" + reqID + "]@@" + "[注册失败]", e);
			logger.debug("[" + reqID + "]@@" + "[入库失败，解锁LoginEmail...]"
					+ userInfo.getLoginEmail());
			if (!locker.unlockUniqueFE(LockConstant.ONLYONE_MOBILE_LOCK
					+ userInfo.getLoginEmail())) {// 解锁LoginEmail
				logger.warn("[" + reqID + "]@@" + "[解锁失败:"
						+ userInfo.getLoginEmail());
			}
			logger.debug("[" + reqID + "]@@" + "[入库失败，解锁BindingEmail...]"
					+ userInfo.getBindingEmail());
			if (!locker.releaseOneFE(LockConstant.ONE_EMAIL_LOCK
					+ userInfo.getBindingEmail())) {// 解锁BindingEmail
				logger.warn("[" + reqID + "]@@" + "[解锁失败:"
						+ userInfo.getBindingEmail());
			}
			return ResponseTool.returnException();
		}
	}

	/**
	 * 业务数据校验(非空，数据规格)
	 * 
	 * @param addUser
	 * @param reqID
	 * @return boolean
	 * @exception Exception
	 */
	private AddUser checkData(Object object) throws Exception {
		AddUser addUser = (AddUser) object;
		if (addUser == null) {
			throw new Exception("[对象转换失败！！！]");
		}

		if (addUser.getType() == null) {
			throw new Exception("[用户注册_注册类型 type 为空!!!]");
		}
		if (addUser.getTime() == null) {
			throw new Exception("[用户注册_注册时间 time 为空!!!]");
		}

		if (addUser.getPassword() == null || "".equals(addUser.getPassword())) {
			throw new Exception("[用户注册_密码 password 为空！！！]");
		}
		if (addUser.getSex() == null) {
			throw new Exception("[用户注册_性别 sex 为空！！！]");
		}
		if (addUser.getUserPhoto() == null || "".equals(addUser.getUserPhoto())) {
			throw new Exception("[用户注册_头像 userPhoto 为空！！！]");
		}

		/**
		 * userName注册_校验userName规则
		 */
		if (addUser.getType().equals(UcConstant.ADDUSER_USERNAME_CODE)) {
			if (addUser.getUserName() == null) {
				throw new Exception("[账号注册_userName 为空！！！]");
			}
			if (addUser.getMobile() == null) {
				throw new Exception("[userName注册_mobile 为空！！！]");
			}
		}
		/**
		 * 手机注册_校验手机规则
		 */
		if (addUser.getType().equals(UcConstant.ADDUSER_PHONE_CODE)) {
			if (addUser.getMobile() == null) {
				throw new Exception("[手机注册_mobile 为空！！！]");
			}
		}

		/**
		 * 邮箱注册_校验邮箱规则
		 */
		if (addUser.getType().equals(UcConstant.ADDUSER_EMAIL_CODE)) {
			if (addUser.getEmail() == null) {
				throw new Exception("[邮箱注册_email 为空！！！]");
			}
		}
		return addUser;
	}

	public UserIdGenerate getUserIdGenerate() {
		return userIdGenerate;
	}

	public void setUserIdGenerate(UserIdGenerate userIdGenerate) {
		this.userIdGenerate = userIdGenerate;
	}

	public TTnumGenerator gettTnumGenerator() {
		return tTnumGenerator;
	}

	public void settTnumGenerator(TTnumGenerator tTnumGenerator) {
		this.tTnumGenerator = tTnumGenerator;
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

	public MsgSenderTool getMsgSenderTool() {
		return msgSenderTool;
	}

	public void setMsgSenderTool(MsgSenderTool msgSenderTool) {
		this.msgSenderTool = msgSenderTool;
	}

	public InitUserAccountTool getInitUserAccountTool() {
		return initUserAccountTool;
	}

	public void setInitUserAccountTool(InitUserAccountTool initUserAccountTool) {
		this.initUserAccountTool = initUserAccountTool;
	}
	
	public HadoopAddUserTool getHadoopAddUserTool() {
		return hadoopAddUserTool;
	}

	public void setHadoopAddUserTool(HadoopAddUserTool hadoopAddUserTool) {
		this.hadoopAddUserTool = hadoopAddUserTool;
	}

	public static void main(String[] args) throws ParseException {
		AddUserServiceImpl aa = new AddUserServiceImpl();
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(aa.unixTimeFmt(Long.parseLong(1447050078+""))));
		System.out.println(System.currentTimeMillis());
		 
		
	}

}
