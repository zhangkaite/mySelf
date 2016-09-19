package com.ttmv.datacenter.usercenter.service.facade.impl.userBehavior;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;

import com.ttmv.datacenter.agent.control.Control;
import com.ttmv.datacenter.agent.control.ControlAgent;
import com.ttmv.datacenter.agent.tokencenter.TokenCenterAgent;
import com.ttmv.datacenter.usercenter.dao.interfaces.TerminalForbidDao;
import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.TerminalForbid;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.operation.query.UserInfoQuery;
import com.ttmv.datacenter.usercenter.domain.protocol.Login;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.LogMsgSenderTool;
import com.ttmv.datacenter.usercenter.service.facade.tools.MsgSenderTool;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ControlSwitchConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.UcConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年1月24日 上午11:38:38  
 * @explain :用户登录
 */
@SuppressWarnings({"rawtypes" ,"unchecked"})
public class LoginServiceImpl extends AbstractUserBase{

	private static Logger logger = LogManager.getLogger(LoginServiceImpl.class);
	
	private TerminalForbidDao terminalForbidDao;//终端禁用
	private UserInfoDao userInfoDao;//用户信息
	private ControlAgent controlAgent;//开关
	private LogMsgSenderTool logMsgSenderTool;//MQ消息发送
	
	private TokenCenterAgent tokenCenterAgent;
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdfs=new SimpleDateFormat("yyyyMMddHH");

	public LogMsgSenderTool getLogMsgSenderTool() {
		return logMsgSenderTool;
	}

	public void setLogMsgSenderTool(LogMsgSenderTool logMsgSenderTool) {
		this.logMsgSenderTool = logMsgSenderTool;
	}

	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[用户登录]_开始逻辑处理...");
		Long startTime = System.currentTimeMillis();
		//开关检测
		String onOff = null;
		try {
			onOff = controlAgent.getInstruction(ControlSwitchConstant.SWITCH_LOGIN_CODE);
		} catch (Exception e) {
			logger.warn("[" + reqID + "]@@" +"开关读取失败！！！"+e.getMessage());
			onOff = Control.CONTROL_AGENT_START;
		}
		if(Control.CONTROL_AGENT_STOP.equals(onOff)){
			logger.warn("[" + reqID + "]@@ [用户登录开关控制_服务关闭！！！]");
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_SERVERCLOSE_CODE, ErrorCodeConstant.ERRORMSG_SERVERCLOSE_CODE);
		}
		//token开关检测
		String onOff2 = null;
		try {
			onOff2= controlAgent.getInstruction(ControlSwitchConstant.SWITCH_TOKEN_CODE);
		} catch (Exception e) {
			logger.warn("[" + reqID + "]@@" +"开关读取失败！！！"+e.getMessage());
			onOff2 = Control.CONTROL_AGENT_START;
		}
		if(Control.CONTROL_AGENT_STOP.equals(onOff2)){
			logger.warn("[" + reqID + "]@@ [token生成API开关控制_服务关闭！！！]");
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_SERVERCLOSE_CODE, ErrorCodeConstant.ERRORMSG_SERVERCLOSE_CODE);
		}
		
		//数据检查
		Login login = null;
		try {
			login = checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		//创建UserInfoQuery
		UserInfoQuery loginUserInfoQuery = this.createUserInfoQuery(login,reqID);
		loginUserInfoQuery.setReqId(reqID);
		//TODO 1、终端禁用过滤
		boolean tag = false;
		try {
			tag = terminalForbidDao.isExistKey(this.creatTerminal(login));
		} catch (Exception e1) {
			logger.error("[" + reqID + "]@@" +"[终端禁用列表查询失败！！！]" + e1.getMessage());
			return ResponseTool.returnException();
		}
		if(tag){
			logger.info("[" + reqID + "]@@" + "终端被禁用！！！");
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_BUSINESS_ERROR_CODE, ErrorCodeConstant.ERRORMSG_FORBID_CODE);
		}
		
		//2、登录密码验证，返回对象
		UserInfo userInfo = null;
		try {
			userInfo = LoginCheck(loginUserInfoQuery, reqID);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据查询异常！！！" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_BUSINESS_ERROR_CODE, ErrorCodeConstant.ERRORMSG_LOGIN_CODE);
		}
		if(userInfo == null){
			logger.debug("[" + reqID + "]@@ 登录失败_用户密名或密码不正确！！！");
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_BUSINESS_ERROR_CODE, ErrorCodeConstant.ERRORMSG_LOGIN_CODE);
		}
		
		//TODO 3、用户状态判断
		Integer status = userInfo.getState();
		if(status .equals( UcConstant.USTATE_BLOCKING)){
			logger.info("[" + reqID + "]@@" + "[用户被冻结无法登录！！！]");
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_BUSINESS_ERROR_CODE, ErrorCodeConstant.ERRORMSG_USERFREEZE_CODE);
		}
		//TODO token
		String token = "";
		if(login.getType() .equals( UcConstant.LOGIN_TOKEN_CODE )){
			token = login.getToken();
		}else {
			token = tokenCenterAgent.registToken(userInfo.getUserid(), login.getLoginName(), login.getClientType());
			if(token == null || "".equals(token)){//生成token失败，返回登录失败
				logger.error("[" + reqID + "]@@" + "token生成失败,登录失败！！！");
				return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_ERROR_CODE, ErrorCodeConstant.ERRORMSG_APIERROR_CODE);
			}
		}
		//添加登录用户的行为日志
		logger.info("userActionLog@@userLogin@@"+sdfs.format(new Date())+"@@"+userInfo.getEnrollterminal()+"@@"+userInfo.getUserid()+"@@"+sdf.format(new Date()));
		
		//异步队列消息
		long mqTime = System.currentTimeMillis();
		try {
			logMsgSenderTool.sendMessage(this.createMsg(userInfo,login,reqID));
		} catch (Exception e) {
			logger.warn("[" + reqID + "]@@" + "队列发送消息失败_" + e.getMessage());
		}
		logger.info("[" + reqID + "]@@"+ " && [登录记录异步消息mq耗时(ms)]:" + (System.currentTimeMillis() - mqTime));
		//返回
		logger.info("[" + reqID + "]@@"+ "[***用户登录成功***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(takeResData(userInfo,token));
	}
	
	/**
	 * 创建队列消息
	 * @param login
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	private String createMsg(UserInfo userInfo,Login login,String reqID) throws Exception{
		Map resMap = new HashMap();
		resMap.put("reqId", reqID);
		resMap.put("msgType", "loginLog");
		resMap.put("TTnum", userInfo.getTTnum());
		resMap.put("IP", login.getIp());
		resMap.put("MAC", login.getMac());
		resMap.put("HdNum", login.getHdNum());
		resMap.put("clientType", login.getClientType());
		resMap.put("loginTime", new Date());
		resMap.put("type", UcConstant.BEHAVIOR_LOGIN);//登录
		return new ObjectMapper().writeValueAsString(resMap);
		
	}
	
	/**
	 * 登录返回信息组装
	 * @param userInfo
	 * @return
	 */
	private Map takeResData(UserInfo userInfo,String token){
		Map resData = new HashMap();
		resData.put("userID", userInfo.getUserid());
		resData.put("userName", userInfo.getUserName());
		resData.put("TTnum", userInfo.getTTnum());
		resData.put("nickName", userInfo.getNickName());
		resData.put("token", token);
		return resData;
	}
	
	/**
	 * 登录检查
	 * @param userInfoQuery
	 * @param reqID
	 * @return
	 * @throws Exception 
	 */
	private UserInfo LoginCheck(UserInfoQuery loginUserInfoQuery,String reqID) throws Exception{
		UserInfo userInfo = null;
		List<UserInfo> users = userInfoDao.userLogin(loginUserInfoQuery);
		if(users != null && users.size() > 0){
			userInfo = users.get(0);
		}else{
			logger.debug("[" + reqID + "]@@" + "用户或密码不正确!!!");
		}
		return userInfo;
	}
	
	/**
	 * UserInfoQuery对象创建
	 * @param addUser
	 * @param reqID
	 * @return
	 * @throws Exception
	 */
	private UserInfoQuery createUserInfoQuery(Login login,String reqID){
		UserInfoQuery userInfoQuery = new UserInfoQuery();
		if(login.getType() .equals( UcConstant.LOGIN_USER_CODE)){//userName登录
			userInfoQuery.setUserName(login.getLoginName());
		}else if(login.getType() .equals(UcConstant.LOGIN_TTNUM_CODE)){//TT号码
			userInfoQuery.setTTnum(new BigInteger(login.getLoginName()));
		}else if(login.getType() .equals(UcConstant.LOGIN_PHONE_CODE)){//手机
			userInfoQuery.setLoginMobile(login.getLoginName());
		}else if(login.getType() .equals( UcConstant.LOGIN_EMAIL_CODE)){//邮箱
			userInfoQuery.setLoginEmail(login.getLoginName());
		}else if(login.getType() .equals( UcConstant.LOGIN_GOODTTNUM_CODE)){//靓号
			userInfoQuery.setLoginGoodTTnumType(UcConstant.GOODTTNUMTYPE_Y);//靓号状态正常
			userInfoQuery.setLoginGoodTTnum(login.getLoginName());//靓号
			//DAMON 2015年6月24日10:43:03
			userInfoQuery.setTTnum(new BigInteger(login.getLoginName()));//TTnum
		}else if(login.getType() .equals( UcConstant.LOGIN_TOKEN_CODE)){//TOKEN
			boolean mack = tokenCenterAgent.checkTokenForUserId(login.getUserID(), login.getClientType(), login.getToken());
			if(mack){//token验证正常
				userInfoQuery.setUserid(login.getUserID());
			}
		}
		userInfoQuery.setPassword(login.getPassword());
		return userInfoQuery;
	}

	/**
	 * 终端禁用对象创建
	 * @param login
	 * @return
	 */
	public TerminalForbid creatTerminal(Login login) {
		TerminalForbid forbid = new  TerminalForbid();
		forbid.setIp(login.getIp());
		forbid.setMac(login.getMac());
		forbid.setDisksn(login.getHdNum());
		return forbid;
	}

	/**
	 * 登录数据验证
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private Login checkData(Object object) throws Exception {
		Login login = (Login) object;
		if(login == null){
			throw new Exception("[对象转换失败！！！]");
		}
		if (login.getType() == null) {
			throw new Exception("[用户登录_登录类型 type 为空！！！]");
		}
		if (login.getClientType() == null) {
			throw new Exception("[用户登录_客户端类型  为空！！！]");
		}
		if(login.getType() .equals( UcConstant.LOGIN_TOKEN_CODE )){//快速登录
			if (login.getUserID() == null) {
				throw new Exception("[用户快速登录_userID为空！！！]");
			}
			if (login.getToken() == null || "".equals(login.getToken())) {
				throw new Exception("[快速登录_ token 为空！！！]");
			}
		}else{
			if (login.getLoginName() == null || "".equals(login.getLoginName())) {
				throw new Exception("[用户登录_登录名 logname 为空！！！]");
			}
			if (login.getPassword() == null || "".equals(login.getPassword())) {
				throw new Exception("[用户登录_密码 password 为空！！！]");
			}
		}
		return login;
	}
	


	public TerminalForbidDao getTerminalForbidDao() {
		return terminalForbidDao;
	}

	public void setTerminalForbidDao(TerminalForbidDao terminalForbidDao) {
		this.terminalForbidDao = terminalForbidDao;
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

	public TokenCenterAgent getTokenCenterAgent() {
		return tokenCenterAgent;
	}

	public void setTokenCenterAgent(TokenCenterAgent tokenCenterAgent) {
		this.tokenCenterAgent = tokenCenterAgent;
	}
	
	
}
