package com.ttmv.datacenter.paycenter.service.facade.impl.account;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.agent.control.Control;
import com.ttmv.datacenter.agent.control.ControlAgent;
import com.ttmv.datacenter.paycenter.dao.interfaces.TcoinInfoDao;
import com.ttmv.datacenter.paycenter.data.OperationInfo;
import com.ttmv.datacenter.paycenter.data.TcoinInfo;
import com.ttmv.datacenter.paycenter.domain.protocol.TBRecharge;
import com.ttmv.datacenter.paycenter.service.facade.template.AbstractPayCenter;
import com.ttmv.datacenter.paycenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.paycenter.service.facade.tools.jmstool.DamsPcTBRechargeTool;
import com.ttmv.datacenter.paycenter.service.facade.tools.util.JsonUtil;
import com.ttmv.datacenter.paycenter.service.processor.constant.ControlSwitchConstant;
import com.ttmv.datacenter.paycenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.paycenter.service.processor.constant.PcConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月9日 上午11:14:33  
 * @explain :T币充值
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TBRechargeServiceImpl  extends AbstractPayCenter{
	private static Logger logger = LogManager.getLogger(TBRechargeServiceImpl.class);
	private TcoinInfoDao tcoinInfoDao;
	private DamsPcTBRechargeTool damsPcTBRechargeTool;
	private ControlAgent controlAgent;//交易流水信息控制开关
	
	
	
	public ControlAgent getControlAgent() {
		return controlAgent;
	}
	public void setControlAgent(ControlAgent controlAgent) {
		this.controlAgent = controlAgent;
	}
	public DamsPcTBRechargeTool getDamsPcTBRechargeTool() {
		return damsPcTBRechargeTool;
	}
	public void setDamsPcTBRechargeTool(DamsPcTBRechargeTool damsPcTBRechargeTool) {
		this.damsPcTBRechargeTool = damsPcTBRechargeTool;
	}
	public TcoinInfoDao getTcoinInfoDao() {
		return tcoinInfoDao;
	}
	public void setTcoinInfoDao(TcoinInfoDao tcoinInfoDao) {
		this.tcoinInfoDao = tcoinInfoDao;
	}
	
	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[T币充值]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		// 数据检查
		TBRecharge tbRecharge = null;
		try {
			tbRecharge = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		//---------------------------------------------
		//临时关闭支付接口---Damon 2015年6月19日12:25:46
//		if(!("admin".equals(tbRecharge.getVersion()))){
//			logger.warn("[" + reqID + "]@@ [用户注册开关控制_服务关闭！！！]");
//			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_SERVERCLOSE_CODE,"支付接口临时关闭！！！");
//		}
		//----------------------------------------------
		
		//充值T币
		OperationInfo getOperationInfo = this.getOperationInfo(tbRecharge);
		getOperationInfo.setReqId(reqID);
		try {
			tcoinInfoDao.changeBalance(getOperationInfo);
			logger.debug("[" + reqID + "]@@" + "[T币充值成功！！！]");
		} catch (Exception e) {
			if(ErrorCodeConstant.ERRORMSG_ACCOUNTERR_CODE.equals(e.getMessage())){
				logger.error("[" + reqID + "]@@"+"[userID]" + tbRecharge.getUserID() + "账户不存在！！！",e);
				return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_BUSINESS_ERROR_CODE,ErrorCodeConstant.ERRORMSG_ACCOUNTERR_CODE);
			}
			logger.error("[" + reqID + "]@@"+"[userID]" + tbRecharge.getUserID() + "T币充值异常！！！" ,e);
			return ResponseTool.returnException();
		}
		//查询余额拼返回数据
		Map resMap = null;
		TcoinInfo tInfo = null;
		try {
			logger.debug("[" + reqID + "]@@" + "[开始T币余额查询...]userID:" + tbRecharge.getUserID());
			tInfo = tcoinInfoDao.selectTcoinInfo(tbRecharge.getUserID());
			if(tInfo != null){
				logger.debug("[" + reqID + "]@@" + "[T币余额查询成功！！！]余额:" + tInfo.getBalance());
				resMap = this.takeResData(tInfo);
			}else{
				logger.warn("[" + reqID + "]@@"+"[userID]" + tbRecharge.getUserID() + "余额查询失败！！！");
			}
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" +"余额查询失败！！！" , e);
		}
		
		//2015年12月30日12:42:13 增加开关
		String onOff = null;
		try {
			onOff = controlAgent.getInstruction(ControlSwitchConstant.SWITCH_PC_DAMS_CODE);
			logger.debug("获取开关--->>>" + onOff);
		} catch (Exception e) {
			logger.warn("[" + reqID + "]@@" +"开关读取失败！！！"+e.getMessage());
			onOff = Control.CONTROL_AGENT_START;
		}
		if(Control.CONTROL_AGENT_STOP.equals(onOff)){
			logger.warn("[" + reqID + "]@@ [支付中心流水写入dams_服务关闭！！！]");
		}else{
			//TOOD 2015年12月17日21:03:11
			try {
				logger.debug("T币充值请求数据异步写入DAMS--->>>" + JsonUtil.getObjectToJson(object));
				damsPcTBRechargeTool.sendMessage(JsonUtil.getObjectToJson(object));
			} catch (Exception e) {
				logger.warn("TB充值 请求数据写入DAMS失败！！！");
			}
		}
		

		
		logger.info("[" + reqID + "]订单编号:"+tbRecharge.getOrderId()+" T币充值记录[userID]:" + tbRecharge.getUserID() +"充值金额:" + tbRecharge.getNumber() +"充值后余额:" + tInfo.getBalance());
		logger.info("[" + reqID + "]@@"+"[userID]" + tbRecharge.getUserID() + "[***T币充值成功***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(resMap);
	}
	
	/**
	 * 创建对象
	 * @param brokerageConsume
	 * @return
	 */
	private OperationInfo getOperationInfo(TBRecharge tbRecharge){
		OperationInfo operationInfo = new OperationInfo();
		
		operationInfo.setUserId(tbRecharge.getUserID());//userID
		operationInfo.setNumber(tbRecharge.getNumber());//金额
		try {
			//订单提交时间
			operationInfo.setTime(unixTimeFmt(Long.parseLong(tbRecharge.getTime())));
		} catch (Exception e) {
			logger.warn("时间转换失败！！！",e);
		}
		operationInfo.setOrderId(tbRecharge.getOrderId());//订单编号
		operationInfo.setClientType(tbRecharge.getClientType());//设备类型
		operationInfo.setVersion(tbRecharge.getVersion());//版本
		
		operationInfo.setType(PcConstant.PC_DEALTYPE_RG);//交易类型
		operationInfo.setAccountType(PcConstant.PC_ACCOUNTTYPE_TB);//币种类型
		return operationInfo;
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
	 * @param userInfo
	 * @return
	 */
	private Map takeResData(TcoinInfo tInfo){
		Map resData = new HashMap();
		resData.put("userID", tInfo.getUserId());
		resData.put("laterNumber", tInfo.getBalance());
		resData.put("accountType","TBAccount");
		return resData;
	}
	
	
	
	
	/**
	 * 数据校验
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private TBRecharge checkData(Object object) throws Exception {
		TBRecharge tbRecharge = (TBRecharge) object;
		if (tbRecharge == null) {
			throw new Exception("[对象转换失败！！！]");
		}
		if(tbRecharge.getUserID() == null){
			throw new Exception("[UserID为空！！！]");
		}
		if(tbRecharge.getNumber() == null){
			throw new Exception("[充值金额_Number为空！！！]");
		}
		
		if(tbRecharge.getOrderId() == null){
			throw new Exception("[OrderId为空！！！]");
		}
		if(tbRecharge.getClientType() == null){
			throw new Exception("[ClientType为空！！！]");
		}
		if(tbRecharge.getVersion() == null){
			throw new Exception("[Version为空！！！]");
		}
		if(tbRecharge.getTime() == null){
			throw new Exception("[Time为空！！！]");
		}

		return tbRecharge;
	}

}
