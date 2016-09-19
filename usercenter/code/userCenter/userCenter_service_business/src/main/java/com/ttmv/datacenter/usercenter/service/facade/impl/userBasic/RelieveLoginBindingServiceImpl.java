package com.ttmv.datacenter.usercenter.service.facade.impl.userBasic;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.agent.control.Control;
import com.ttmv.datacenter.agent.control.ControlAgent;
import com.ttmv.datacenter.agent.lockcenter.Locker;
import com.ttmv.datacenter.sentry.SentryAgent;
import com.ttmv.datacenter.usercenter.dao.implement.util.JsonUtil;
import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.protocol.LoginBinding;
import com.ttmv.datacenter.usercenter.domain.protocol.RelieveLoginBinding;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ControlSwitchConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.LockConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.UcConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年4月17日 下午10:39:57  
 * @explain :055_登录号解绑
 */
@SuppressWarnings({ "rawtypes" })
public class RelieveLoginBindingServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(RelieveLoginBindingServiceImpl.class);
	private UserInfoDao userInfoDao;
	private Locker locker;
	private ControlAgent controlAgent;// 开关
	private SentryAgent overdueManageTTnum;
	
	

	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[登录号解绑]_开始逻辑处理...");
		
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
		
		long startTime = System.currentTimeMillis();
		//数据检查
		RelieveLoginBinding relieveLoginBinding = null;
		try {
			relieveLoginBinding = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		Map resMap = new HashMap();
		if(relieveLoginBinding.getType().equals(  UcConstant.OPENTYPE_GOODTTNUM_CODE)){//靓号开通登录
			resMap = this.closeGoodTTnumLogin(relieveLoginBinding, reqID);
		}else{
			logger.error("[" + reqID + "]@@" + "[登录号解绑失败_号码类型不存在！！！]");
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETVIOLATE_CODE);
		}
		
		//Damon_2015年12月10日18:53:49 
		String jsonData = "";
		try {
			jsonData = getJsonData(relieveLoginBinding);
		} catch (Exception e) {
			logger.warn("会员倒计时jsonData组装失败！！！",e);
		}
		String resCode = overdueManageTTnum.expressSendHttp("data="+jsonData);
		Map resMaap = null;
		try {
			resMaap = (Map) JsonUtil.getObjectFromJson(resCode, Map.class);
		} catch (Exception e) {
			logger.error("json转换失败！！！");
		}
		if(resMaap.get("resultCode").equals("AAAAAAA")){
			logger.info("[" + reqID + "]@@"+ "[登录号解绑业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
			return ResponseTool.returnSuccess(null);
		}else{
			logger.error("靓号绑定处理失败，错误代码:"+resCode);
			logger.error("[到期系统上报消息上报失败:]====>>>" + "data="+jsonData);
			return ResponseTool.returnException();
		}
	}
	
	/**
	 * 拼jsonData
	 * @param openingVIP
	 * @return
	 * @throws Exception
	 */
	private String getJsonData(RelieveLoginBinding relieveLoginBinding) throws Exception{
		Map openingVip = new HashMap<String,Object>();
		openingVip.put("userID", relieveLoginBinding.getUserID());
		openingVip.put("type","ON");
		openingVip.put("goodTTnum", relieveLoginBinding.getValue());
		return JsonUtil.getObjectToJson(openingVip);
		
	}
	
	/**
	 * 靓号登录开通
	 * @param loginBinding
	 * @param reqID
	 * @return
	 */
	private Map closeGoodTTnumLogin(RelieveLoginBinding relieveLoginBinding , String reqID){
		//解锁
		if(!locker.unlockUniqueFE(LockConstant.ONLYONE_GOODTTNUM_LOCK + relieveLoginBinding.getValue())){//解锁loginGoogTTnum
			logger.warn("[" + reqID + "]@@" + "[loginGoogTTnum解锁失败:" + relieveLoginBinding.getValue());
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setUserid(relieveLoginBinding.getUserID());
		userInfo.setLoginGoodTTnum("");//设置为空
		userInfo.setLoginGoodTTnumType(0);//无靓号默认状态
		userInfo.setReqId(reqID);
		try {
			userInfoDao.updateUserInfo(userInfo);
			return ResponseTool.returnSuccess(null);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@ [靓号解绑定失败！！！]" , e);
			//加锁登录靓号
			if (!locker.lockUniqueFE(LockConstant.ONLYONE_GOODTTNUM_LOCK + relieveLoginBinding.getValue())) {
				logger.warn("[" + reqID + "]@@" + "[靓号加锁登录失败_:" + relieveLoginBinding.getValue());
			}
			return ResponseTool.returnException();
		}
	}
	
	/**
	 * 数据检查
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	private RelieveLoginBinding checkData(Object object) throws Exception{
		RelieveLoginBinding relieveLoginBinding = (RelieveLoginBinding)object;
		if(relieveLoginBinding == null){
			throw new Exception("对象转换失败！！！");
		}
		if (relieveLoginBinding.getUserID() == null) {
			throw new Exception("[UserID 为空！！！]");
		}
		if (relieveLoginBinding.getType() == null) {
			throw new Exception("[类型 type 为空！！！]");
		}
		if (relieveLoginBinding.getValue() == null) {
			throw new Exception("[Value 为空！！！]");
		}
		return relieveLoginBinding;
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

	public SentryAgent getOverdueManageTTnum() {
		return overdueManageTTnum;
	}

	public void setOverdueManageTTnum(SentryAgent overdueManageTTnum) {
		this.overdueManageTTnum = overdueManageTTnum;
	}
	


	
}
