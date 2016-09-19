package com.ttmv.datacenter.usercenter.service.facade.impl.userBasic;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.ttmv.datacenter.agent.control.Control;
import com.ttmv.datacenter.agent.control.ControlAgent;
import com.ttmv.datacenter.agent.lockcenter.Locker;
import com.ttmv.datacenter.usercenter.dao.implement.util.TableIdGenerate;
import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.protocol.AdminAddUser;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
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
 * @version 创建时间：2015年1月26日 上午10:24:51  
 * @explain :管控系统添加用户
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AdminAddUserServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(AdminAddUserServiceImpl.class);
	
	private UserInfoDao userInfoDao;//入库接口
	private UserIdGenerate userIdGenerate;//userID生成接口
	private Locker locker;//锁
	private MsgSenderTool msgSenderTool;//MQ消息发送（初始化好友分组）
	private InitUserAccountTool initUserAccountTool;//MQ消息发送（初始化资金账户）
	private ControlAgent controlAgent;// 开关
	private TableIdGenerate tableIdGenerate;
	
	
	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[管控系统添加用户]_开始逻辑处理...");
		
		//开关检测
		String onOff = null;
		try {
			onOff = controlAgent.getInstruction(ControlSwitchConstant.SWITCH_ADDUSER_CODE);
		} catch (Exception e) {
			logger.warn("[" + reqID + "]@@" +"开关读取失败！！！"+e.getMessage());
			onOff = Control.CONTROL_AGENT_START;
		}
		if(Control.CONTROL_AGENT_STOP.equals(onOff)){
			logger.warn("[" + reqID + "]@@ [用户注册开关控制_服务关闭！！！]");
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_SERVERCLOSE_CODE, ErrorCodeConstant.ERRORMSG_SERVERCLOSE_CODE);
		}
		//分布式锁开关检测
		String onOff2 = null;
		try {
			onOff2= controlAgent.getInstruction(ControlSwitchConstant.SWITCH_LOCK_CODE);
		} catch (Exception e) {
			logger.warn("[" + reqID + "]@@" +"开关读取失败！！！"+e.getMessage());
			onOff2 = Control.CONTROL_AGENT_START;
		}
		if(Control.CONTROL_AGENT_STOP.equals(onOff2)){
			logger.warn("[" + reqID + "]@@ [分布式锁开关控制_服务关闭！！！]");
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_SERVERCLOSE_CODE, ErrorCodeConstant.ERRORMSG_SERVERCLOSE_CODE);
		}
		
		Long startTime = System.currentTimeMillis();
		//数据检查
		AdminAddUser addUser = null;
		try {
			addUser = checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		//TODO ===========Damon_ZS---2015年3月31日16:41:23==============
		String userName = addUser.getUserName();
		if (!(userName.matches(RegexConstant.REGEX_USERNAME_0) &&
			  userName.matches(RegexConstant.REGEX_USERNAME_1) &&
			  userName.matches(RegexConstant.REGEX_USERNAME_2) &&
		      userName.matches(RegexConstant.REGEX_USERNAME_3))) {// 正则校验userName
			logger.error("[账号注册_userName 非法,用户名规则为 首字母小写，其他字符数字组合，长度6-25！！！]");
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERROR_UNAME_VALIDATION_CODE);
		}
		String phone = addUser.getMobile();
		if (!phone.matches(RegexConstant.REGEX_MOBILE)) {// 正则表达式验证手机格式
			logger.error("[手机号码格式非法！！！]");
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERROR_PHONE_VALIDATION_CODE);
		}
		String email = addUser.getEmail();
		if (!email.matches(RegexConstant.REGEX_EMAIL)) {// 正则表达式验证邮箱格式
			logger.error("[邮箱格式非法！！！]");
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERROR_EMAIL_VALIDATION_CODE);
		}
		
		//创建userInfo对象
		UserInfo userInfo = null;
		try {
			userInfo = createUserInfo(addUser , reqID); 
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "UserInfo对象创建失败_" + e.getMessage());
			return ResponseTool.returnException();
		}
		/**
		 * 生成userID
		 */
		try {
			BigInteger userID = userIdGenerate.getUserId();
			if (userID == null) {
				throw new Exception("[生成的userID为NULL!!!]");
			}
			userInfo.setUserid(userID);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "[userID生成异常!!!]" + e.getMessage());
		}
		//注册数据入库
		Map resMap =  adminAddUSer(userInfo,reqID);
		logger.info("[" + reqID + "]@@"+ "[注册业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return resMap;
		
	}
	/**
	 * 注册信息入库
	 * @param userInfo
	 * @param startTime
	 * @param reqID
	 * @return
	 */
	private Map adminAddUSer(UserInfo userInfo, String reqID){
		//唯一参数加锁
		Map lockMap = locks(userInfo, reqID);
		if(lockMap.get("resultCode") != null){
			return ResponseTool.returnError(lockMap.get("resultCode").toString(), lockMap.get("errorMsg").toString());
		}
		//注册信息入库
		try {
			if(userInfo.getUtype().equals(UcConstant.UTYPE_BOT_CODE)){//机器人需要在此生成TT号码
				String tempid = tableIdGenerate.getTableId("bot")+"";
				while (tempid.length() < UcConstant.BOT_TTNUM_LENG) {//8位号段
					tempid = "0" + tempid;
			    }
				userInfo.setTTnum(new BigInteger(UcConstant.BOT_TTNUM_TOP + tempid));
				logger.info("[" + reqID + "]@@" + "生成机器人TTnum:" + userInfo.getTTnum());
			}
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
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@"+"数据添加失败！！！" , e);
			//入库失败，解锁
			this.unLocks(lockMap, reqID);
			return ResponseTool.returnException();
		}
		//TODO 资金账户初始化队列
		Map resMap = ResponseTool.returnSuccess(this.takeResData(userInfo));
		if(resMap.get("resultCode").equals(ErrorCodeConstant.SYSTEM_SUCCESS_CODE)){
			try {
				initUserAccountTool.sendMessage(this.createMsginitAccount(userInfo));
			} catch (Exception e) {
				logger.warn("[" + reqID + "]@@" +"初始化资金账户添加队列失败！！！" + e.getMessage());
			}
		}
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
	 * 拼装返回信息
	 * @param userInfo
	 * @return
	 */
	private Map takeResData(UserInfo userInfo){
		Map resData = new HashMap();
		resData.put("userName", userInfo.getUserName());
		resData.put("TTnum", userInfo.getTTnum());
		resData.put("userID", userInfo.getUserid());
		return resData;
	}
	
	/**
	 * 数据加锁
	 * @param userInfo
	 * @param reqID
	 * @return
	 * @throws Exception
	 */
	public Map locks(UserInfo userInfo,String reqID){
		Map lockMap = new HashMap();
		//1、userName
		if(!locker.lockUniqueFE(LockConstant.ONLYONE_USERNAME_LOCK + userInfo.getUserName())){//userName唯一性验证
			logger.info("[" + reqID + "]@@" + "[用户添加失败_用户名:"+ userInfo.getUserName() +"已经注册！！！]");
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE, ErrorCodeConstant.ERRORMSG_USERNAME_CODE);
		}
		lockMap.put(LockConstant.ONLYONE_USERNAME_LOCK, userInfo.getUserName());
		
		// @Damon 2015年8月14日15:08:29
		if(!userInfo.getUtype().equals(UcConstant.UTYPE_BOT_CODE)){//机器人注册，不用加锁tt号，因为tt号为系统生成，保证唯一性
			//2、TTnum	
			if(!locker.lockUniqueFE(LockConstant.ONLYONE_TTNUM_LOCK + userInfo.getTTnum())){//TT号唯一性验证
				logger.info("[" + reqID + "]@@" + "[用户添加失败_TT号码:"+ userInfo.getTTnum() +"已经注册！！！]");
				//解锁
				this.unLocks(lockMap, reqID);
				return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE, ErrorCodeConstant.ERRORMSG_TTNUM_CODE);
			}
			lockMap.put(LockConstant.ONLYONE_TTNUM_LOCK, userInfo.getTTnum());
		}
		
		//3、loginMobile
		if(!locker.lockUniqueFE(LockConstant.ONLYONE_MOBILE_LOCK + userInfo.getLoginMobile())){//手机号唯一性验证
			logger.info("[" + reqID + "]@@" + "[用户添加失败_手机号码:"+ userInfo.getLoginMobile() +"已经注册！！！]");
			//解锁
			this.unLocks(lockMap, reqID);
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE, ErrorCodeConstant.ERRORMSG_LOGINMOBILE_CODE);
		}
		lockMap.put(LockConstant.ONLYONE_MOBILE_LOCK, userInfo.getLoginMobile());
		//4、bindingMobile
		if (!locker.lockUntilFE(LockConstant.FIVE_MOBILE_LOCK + userInfo.getBindingMobile(), 5)) {
			logger.info("[" + reqID + "]@@" + "[注册处理失败_手机号:" + userInfo.getBindingMobile() + "已绑定5次！！！]");
			//解锁
			this.unLocks(lockMap, reqID);
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE, ErrorCodeConstant.ERRORMSG_BINDINGMOBILE_CODE);
		}
		lockMap.put(LockConstant.FIVE_MOBILE_LOCK, userInfo.getBindingMobile());
		//5、loginEmail
		if(!locker.lockUniqueFE(LockConstant.ONLYONE_EMAIL_LOCK + userInfo.getLoginEmail())){
			logger.info("[" + reqID + "]@@" + "[用户添加失败_邮箱:"+ userInfo.getLoginEmail() +"已经注册！！！]");
			//解锁
			this.unLocks(lockMap, reqID);
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE, ErrorCodeConstant.ERRORMSG_LOGINEMAIL_CODE);
		}
		lockMap.put(LockConstant.ONLYONE_EMAIL_LOCK, userInfo.getLoginEmail());
		
		//6、bindingEmail
		if (!locker.lockUntilFE(LockConstant.ONE_EMAIL_LOCK + userInfo.getBindingEmail(), 1)) {
			logger.info("[" + reqID + "]@@" + "[注册处理失败_邮箱:" + userInfo.getBindingEmail() + "已绑定1次！！！]");
			//解锁
			this.unLocks(lockMap, reqID);
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE, ErrorCodeConstant.ERRORMSG_BINDINGEMAIL_CODE);
		}
		lockMap.put(LockConstant.ONE_EMAIL_LOCK, userInfo.getBindingEmail());
		
		return lockMap;
		
	}
	
	
	public void unLocks(Map lockMap ,String reqID){
	    Set key = lockMap.keySet();        
	    for (Iterator it = key.iterator(); it.hasNext();) 
	    {            
	    	String lockKey = (String) it.next();
	    	
	    	if(lockKey.equals(LockConstant.ONLYONE_MOBILE_LOCK)){//注册手机解锁
	    		if(!locker.unlockUniqueFE(LockConstant.ONLYONE_MOBILE_LOCK + lockMap.get(lockKey))){//解锁LoginMobile
					logger.warn("[" + reqID + "]@@" + "[loginMobile解锁失败:" + lockMap.get(lockKey));
				}
	    	}else if(lockKey.equals(LockConstant.ONLYONE_EMAIL_LOCK)){//注册邮箱解锁
	    		if(!locker.unlockUniqueFE(LockConstant.ONLYONE_MOBILE_LOCK + lockMap.get(lockKey))){//解锁LoginEmail
					logger.warn("[" + reqID + "]@@" + "[loginEmail解锁失败:" + lockMap.get(lockKey));
				}
	    	}else if(lockKey.equals(LockConstant.ONLYONE_TTNUM_LOCK)){//TT号解锁
	    		if(!locker.unlockUniqueFE(LockConstant.ONLYONE_TTNUM_LOCK + lockMap.get(lockKey))){//解锁userName
					logger.warn("[" + reqID + "]@@" + "[TTnum解锁失败:" + lockMap.get(lockKey));
				}
	    	}else if(lockKey.equals(LockConstant.ONLYONE_USERNAME_LOCK)){//userName解锁
	    		if(!locker.unlockUniqueFE(LockConstant.ONLYONE_USERNAME_LOCK + lockMap.get(lockKey))){//解锁userName
					logger.warn("[" + reqID + "]@@" + "[userName解锁失败:" + lockMap.get(lockKey));
				}
	    	}else if(lockKey.equals(LockConstant.FIVE_MOBILE_LOCK)){//绑定手机
	    		if(!locker.releaseOneFE(LockConstant.FIVE_MOBILE_LOCK + lockMap.get(lockKey))){//解锁bindingMobile
					logger.warn("[" + reqID + "]@@" + "[bindingMobile解锁失败:" + lockMap.get(lockKey));
				}
	    	}else if(lockKey.equals(LockConstant.ONE_EMAIL_LOCK)){//绑定邮箱
	    		if(!locker.releaseOneFE(LockConstant.ONE_EMAIL_LOCK + lockMap.get(lockKey))){//解锁BindingEmail
					logger.warn("[" + reqID + "]@@" + "[bindingEmail解锁失败:" + lockMap.get(lockKey));
				}
	    	}
	    }
	}
	
	
	
	/**
	 * 数据检查
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private AdminAddUser checkData(Object object) throws Exception {
		AdminAddUser addUser = (AdminAddUser) object;
		if(addUser == null){
			throw new Exception("[对象转换失败！！！]");
		}
		if (addUser.getType() == null) {
			throw new Exception("[用户添加_用户类型 type 为空!!!]");
		}
		//@Damon 2015年8月14日15:11:52
		if(!addUser.getType().equals(UcConstant.UTYPE_BOT_CODE)){//添加机器人，TT号不用传过来
			if (addUser.getTTnum() == null) {
				throw new Exception("[用户添加_TT号 TTnum 为空!!!]");
			}
		}
		
		if (addUser.getNickName() == null || "".equals(addUser.getNickName())) {
			throw new Exception("[用户添加_用户昵称 nickName 为空！！！]");
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
		if (addUser.getUserName() == null || "".equals(addUser.getUserName())) {
			throw new Exception("[账号注册_userName 为空！！！]");
		}
		if (addUser.getMobile() == null || "".equals(addUser.getMobile())) {
			throw new Exception("[userName注册_mobile 为空！！！]");
		}
		if (addUser.getEmail() == null || "".equals(addUser.getEmail())) {
			throw new Exception("[邮箱注册_email 为空！！！]");
		}
		return addUser;
		
	}
	
	/**
	 * 创建UserInfo
	 * @param addUser
	 * @param reqID
	 * @return
	 * @throws Exception
	 */
	private UserInfo createUserInfo(AdminAddUser addUser,String reqID) throws Exception{
		UserInfo userInfo = new UserInfo();
		//TODO Damon 2015年8月14日15:42:39
		if(!addUser.getType().equals(UcConstant.UTYPE_BOT_CODE)){//机器人此时还没有TT号
			userInfo.setTTnum(addUser.getTTnum());
		}
		userInfo.setUserName(addUser.getUserName());
		userInfo.setNickName(addUser.getNickName());
		userInfo.setUserPhoto(addUser.getUserPhoto());
		userInfo.setSex(addUser.getSex());
		userInfo.setMobile(addUser.getMobile());
		userInfo.setBindingMobile(addUser.getMobile());
		userInfo.setLoginMobile(addUser.getMobile());
		userInfo.setEmail(addUser.getEmail());
		userInfo.setBindingEmail(addUser.getEmail());
		userInfo.setLoginEmail(addUser.getEmail());
		userInfo.setPassword(addUser.getPassword());
		userInfo.setUtype(addUser.getType());//用户类型
		userInfo.setVipType(UcConstant.VIPTYPE_NO);//非会员
		userInfo.setState(UcConstant.USTATE_NORMAL);// 状态(0正常)
		userInfo.setAdminId(addUser.getAdminId());//管理员ID
		userInfo.setReason(addUser.getReason());//添加原因
		userInfo.setDndType(UcConstant.DNDTYPE_ALL);//默认所有人添加（防骚扰）
		//@Damon 2015年6月16日15:27:58
		userInfo.setTime(unixTimeFmt(Long.parseLong(addUser.getTime())));
		userInfo.setReqId(reqID);//业务流水号
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
	
	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}
	public UserIdGenerate getUserIdGenerate() {
		return userIdGenerate;
	}
	public void setUserIdGenerate(UserIdGenerate userIdGenerate) {
		this.userIdGenerate = userIdGenerate;
	}
	public Locker getLocker() {
		return locker;
	}
	public void setLocker(Locker locker) {
		this.locker = locker;
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
	public ControlAgent getControlAgent() {
		return controlAgent;
	}
	public void setControlAgent(ControlAgent controlAgent) {
		this.controlAgent = controlAgent;
	}
	public TableIdGenerate getTableIdGenerate() {
		return tableIdGenerate;
	}
	public void setTableIdGenerate(TableIdGenerate tableIdGenerate) {
		this.tableIdGenerate = tableIdGenerate;
	}
	
	
}
