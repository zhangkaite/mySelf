
package com.ttmv.datacenter.usercenter.service.facade.impl.userBehavior;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.agent.control.Control;
import com.ttmv.datacenter.agent.control.ControlAgent;
import com.ttmv.datacenter.agent.tokencenter.TokenCenterAgent;
import com.ttmv.datacenter.usercenter.domain.protocol.Logout;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ControlSwitchConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月9日 上午10:54:11  
 * @explain :用户退出
 */
@SuppressWarnings({"rawtypes"})
public class LogoutServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(LogoutServiceImpl.class);
	private TokenCenterAgent tokenCenterAgent;
	private ControlAgent controlAgent;//开关
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
	SimpleDateFormat sdfs=new SimpleDateFormat("yyyyMMddHH");
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

	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[用户退出]_开始逻辑处理...");
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
		Long startTime = System.currentTimeMillis();
		Logout logout = null;
		try {
			logout = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		//组装查询对象（得到id）
		try {
			tokenCenterAgent.killToken(logout.getUserID(), logout.getClientType(), logout.getToken());
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@"+"用户退出异常！！！" + e.getMessage());
		}
		//添加用户登出行为日志信息  操作类型+操作时间+用户USERID+操作具体时间
		logger.info("userActionLog@@userLogout@@"+sdfs.format(new Date())+"@@"+logout.getClientType()+"@@"+logout.getUserID()+"@@"+sdf.format(new Date()));
		
		logger.info("[" + reqID + "]@@"+ "[***用户退出完成***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(null);
	}
	
	/**
	 * 数据验证
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private Logout checkData(Object object) throws Exception{
		Logout logout = (Logout)object;
		if(logout == null){
			throw new Exception("对象转换异常！！！");
		}
		if(logout.getUserID() == null){
			throw new Exception("UserID 为空！！！");
		}
		if(logout.getToken() == null || "".equals(logout.getToken())){
			throw new Exception("Token 为空！！！");
		}
		if(logout.getClientType() == null){
			throw new Exception("ClientType 为空！！！");
		}
		return logout;
	}
	
	

}
