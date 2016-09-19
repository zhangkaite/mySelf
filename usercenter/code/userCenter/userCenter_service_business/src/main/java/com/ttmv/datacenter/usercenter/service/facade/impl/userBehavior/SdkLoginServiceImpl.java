package com.ttmv.datacenter.usercenter.service.facade.impl.userBehavior;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.ttmv.datacenter.agent.tokencenter.TokenCenterAgent;
import com.ttmv.datacenter.generator.ttnum.TTnumGenerator;
import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.operation.query.UserInfoQuery;
import com.ttmv.datacenter.usercenter.domain.protocol.SdkLogin;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.InitUserAccountTool;
import com.ttmv.datacenter.usercenter.service.facade.tools.MsgSenderTool;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.UcConstant;
import com.ttmv.datacenter.usercenter.service.processor.tools.UserIdGenerate;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年3月3日 下午4:43:42  
 * @explain :第三方登录
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SdkLoginServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(SdkLoginServiceImpl.class);
	private UserInfoDao userInfoDao;
	private UserIdGenerate userIdGenerate;// userID生成接口
	private TTnumGenerator tTnumGenerator;//TT号生成接口
	private TokenCenterAgent tokenCenterAgent;
	private MsgSenderTool msgSenderTool;//MQ消息发送（初始化好友分组）
	private InitUserAccountTool initUserAccountTool;//MQ消息发送（初始化资金账户）
	
	
	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[用户第三方登录]_开始逻辑处理...");
		Long startTime = System.currentTimeMillis();
		//数据验证
		SdkLogin sdkLogin = null;
		try {
			sdkLogin = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		//查询该openID是否有对应的用户（如果有直接查询用户信息，没有则生成用户，并绑定）
		UserInfoQuery userInfoQuery = new UserInfoQuery();
		userInfoQuery.setOpenId(sdkLogin.getOpenID());
		List<UserInfo> ls = null;
		try {
			ls = userInfoDao.selectListBySelectivePaging(userInfoQuery);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "openID查询用户异常_" + e.getMessage());
			return ResponseTool.returnException();
		}
		UserInfo userInfo = null;
		if(ls == null || ls.size() == 0){
			//注册生成用户（绑定第三方openID）
			try {
				userInfo = this.createUserInfo(sdkLogin, reqID);
				//TODO 添加异步队列， 初始化好友分组、资金账户
				int in =userInfoDao.addUserInfo(userInfo);
				if(in == 0){//返回不直接成功
					logger.warn("[" + reqID + "]@@" + "[警告:注册被受理，但未直接返回成功！！！]");
				}
				//TODO  异步队列记录注册(初始化用户好友分组.)
				try {
					msgSenderTool.sendMessage(this.createMsg(userInfo));
				} catch (Exception e) {
					logger.warn("[" + reqID + "]@@"+"队列发送数据失败！！！" + e.getMessage());
				}
				
				//TODO 资金账户初始化队列
				logger.debug("[" + reqID + "]@@" + "[sdk首次登录...创建用户_资金账户队列初始化_userID:]"+userInfo.getUserid());
				try {
					initUserAccountTool.sendMessage(this.createMsginitAccount(userInfo));
				} catch (Exception e) {
					logger.warn("[" + reqID + "]@@" +"初始化资金账户添加队列失败！！！" + e.getMessage());
				}
			} catch (Exception e) {
				logger.error("[" + reqID + "]@@" + "绑定第三方用户异常_" , e);
				return ResponseTool.returnException();
			}
		}else{
			userInfo = ls.get(0);
		} 
		//返回登录参数
		String token = tokenCenterAgent.registToken(userInfo.getUserid(), userInfo.getUserName(), sdkLogin.getClientType());
		if(token == null || "".equals(token)){//生成token失败，返回登录失败
			logger.error("[" + reqID + "]@@" + "token生成失败,登录失败！！！");
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_ERROR_CODE, ErrorCodeConstant.ERRORMSG_APIERROR_CODE);
		}
		
		Map resMap = ResponseTool.returnSuccess(this.takeResData(userInfo,token));
		
		
		logger.info("[" + reqID + "]@@"+ "[***第三方登录成功***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		//返回返回数据map
		return resMap;
	}
	
	/**
	 * 创建异步队列（用户资金账户初始化）
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	private String createMsginitAccount(UserInfo userInfo) throws Exception{
		Map resMap = new HashMap();
		resMap.put("msgType", "initAccount");
		resMap.put("userID", userInfo.getUserid());
		resMap.put("reqId", userInfo.getReqId());
		return new ObjectMapper().writeValueAsString(resMap);
		
	}
	
	/**
	 * 拼装队列消息
	 * @param userInfo
	 * @param login
	 * @return
	 * @throws Exception
	 */
	private String createMsg(UserInfo userInfo) throws Exception{
		Map resMap = new HashMap();
		resMap.put("msgType", "addUser");
		resMap.put("userID", userInfo.getUserid());
		resMap.put("reqId", userInfo.getReqId());
		return new ObjectMapper().writeValueAsString(resMap);
		
	}
	
	/**
	 * 登录返回信息组装
	 * @param userInfo
	 * @return
	 */
	private Map takeResData(UserInfo userInfo ,String token){
		Map resData = new HashMap();
		resData.put("userID", userInfo.getUserid());
		resData.put("userName", userInfo.getUserName());
		resData.put("TTnum", userInfo.getTTnum());
		resData.put("nickName", userInfo.getNickName());
		resData.put("token", token);
		return resData;
	}
	
	/**
	 * 注册用户对象赋值
	 * @param userInfo
	 * @param reqID
	 * @param addUser
	 * @return
	 * @throws Exception 
	 */
	private UserInfo createUserInfo(SdkLogin sdkLogin,String reqID) throws Exception{
		UserInfo userInfo = new UserInfo();
		BigInteger TTnum = null;
		BigInteger userID = null;
		/**
		 * 生成userID
		 */
		userID = userIdGenerate.getUserId();
		if (userID == null) {
			throw new Exception("[userID生成失败!!!]");
		}
		userInfo.setUserid(userID);// userID
		/**
		 * 生成TTnum
		 */
		TTnum = tTnumGenerator.generateSegmentTTnum();
		if(TTnum == null || "".equals(TTnum)){
			throw new Exception("[TT号生成失败!!!]");
		}
		userInfo.setTTnum(TTnum);// TT号
		userInfo.setUserName(TTnum + "tt");
		userInfo.setUserPhoto(sdkLogin.getUserPhoto());
		userInfo.setVipType(UcConstant.VIPTYPE_NO);//不是vip
		userInfo.setEnrollterminal(sdkLogin.getClientType());//首次三方登录（注册）端类型
		userInfo.setNickName(sdkLogin.getNickName());
		userInfo.setSex(0);// 性别
		userInfo.setExp(new BigInteger("0"));// 经验值
		//Damon 2015年12月16日20:02:04
		userInfo.setUserLevel(0);
		userInfo.setAnnouncerType(UcConstant.ANNOUNCERTYPE_N);
		
		userInfo.setState(UcConstant.USTATE_NORMAL);// 状态(0正常)
		userInfo.setUtype(UcConstant.UTYPE_GENERAL_CODE);// 类型
		userInfo.setDndType(UcConstant.DNDTYPE_ALL);//默认所有人添加（防骚扰）
		userInfo.setOpenId(sdkLogin.getOpenID());
		userInfo.setSdkType(sdkLogin.getSdkType());//第三方平台类型
		userInfo.setReqId(reqID);
		//Damon 2016年2月29日12:05:09
		userInfo.setTime(unixTimeFmt(System.currentTimeMillis()/1000));// 注册时间
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
	 * 数据验证
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	private SdkLogin checkData(Object object) throws Exception{
		SdkLogin sdkLogin = (SdkLogin) object;
		if(sdkLogin == null){
			throw new Exception("[对象转换失败！！！]");
		}
		if(sdkLogin.getOpenID() == null || "".equals(sdkLogin.getOpenID())){
			throw new Exception("[第三方登录_openID 为空！！！]");
		}
		if(sdkLogin.getNickName() == null || "".equals(sdkLogin.getNickName())){
			throw new Exception("[第三方登录_NickName 为空！！！]");
		}
		if(sdkLogin.getUserPhoto() == null || "".equals(sdkLogin.getUserPhoto())){
			throw new Exception("[第三方登录_UserPhoto 为空！！！]");
		}
		if(sdkLogin.getClientType() == null){
			throw new Exception("[第三方登录_ClientType 为空！！！]");
		}
		return sdkLogin;
	}

	public TokenCenterAgent getTokenCenterAgent() {
		return tokenCenterAgent;
	}
	public void setTokenCenterAgent(TokenCenterAgent tokenCenterAgent) {
		this.tokenCenterAgent = tokenCenterAgent;
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

	
}
