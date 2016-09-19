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
import com.ttmv.datacenter.paycenter.dao.interfaces.BrokerageInfoDao;
import com.ttmv.datacenter.paycenter.data.BrokerageInfo;
import com.ttmv.datacenter.paycenter.data.OperationInfo;
import com.ttmv.datacenter.paycenter.domain.protocol.BrokerageRecharge;
import com.ttmv.datacenter.paycenter.service.facade.template.AbstractPayCenter;
import com.ttmv.datacenter.paycenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.paycenter.service.facade.tools.jmstool.DamsPcYJRechargeTool;
import com.ttmv.datacenter.paycenter.service.facade.tools.util.JsonUtil;
import com.ttmv.datacenter.paycenter.service.processor.constant.ControlSwitchConstant;
import com.ttmv.datacenter.paycenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.paycenter.service.processor.constant.PcConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月9日 上午11:31:00  
 * @explain :佣金兑换（充值）
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BrokerageRechargeServiceImpl extends AbstractPayCenter{
	private static Logger logger = LogManager.getLogger(BrokerageRechargeServiceImpl.class);
	private BrokerageInfoDao brokerageInfoDao;
	//2015年12月17日21:00:22 Damon
	private DamsPcYJRechargeTool damsPcYJRechargeTool;
	private ControlAgent controlAgent;//交易流水信息控制开关
	
	public DamsPcYJRechargeTool getDamsPcYJRechargeTool() {
		return damsPcYJRechargeTool;
	}
	public void setDamsPcYJRechargeTool(DamsPcYJRechargeTool damsPcYJRechargeTool) {
		this.damsPcYJRechargeTool = damsPcYJRechargeTool;
	}
	public BrokerageInfoDao getBrokerageInfoDao() {
		return brokerageInfoDao;
	}
	public void setBrokerageInfoDao(BrokerageInfoDao brokerageInfoDao) {
		this.brokerageInfoDao = brokerageInfoDao;
	}

	@Override
	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[佣金兑换（充值）]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		// 数据检查
		BrokerageRecharge brokerageRecharge = null;
		try {
			brokerageRecharge = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		OperationInfo getOperationInfo = this.getOperationInfo(brokerageRecharge);
		getOperationInfo.setReqId(reqID);
		logger.debug("[" + reqID + "]@@" + "[佣金充值对象创建成功！！！]");
		//佣金兑换
		try {
			brokerageInfoDao.changeBalance(getOperationInfo);
			logger.debug("[" + reqID + "]@@" + "[佣金充值成功！！！]");
		} catch (Exception e) {
			if(ErrorCodeConstant.ERRORMSG_ACCOUNTERR_CODE.equals(e.getMessage())){
				logger.error("[" + reqID + "]@@"+"[userID]" + brokerageRecharge.getUserID() + "账户不存在！！！",e);
				return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_BUSINESS_ERROR_CODE,ErrorCodeConstant.ERRORMSG_ACCOUNTERR_CODE);
			}
			logger.error("[" + reqID + "]@@" +"佣金兑换异常！！！" , e);
			return ResponseTool.returnException();
		}
		Map resMap = null;
		BrokerageInfo brokerageInfo = null;
		//余额查询
		try {
			logger.debug("[" + reqID + "]@@" + "[开始查询佣金余额...]");
			brokerageInfo = brokerageInfoDao.selectBrokerageInfo(brokerageRecharge.getUserID());
			if(brokerageInfo != null){
				resMap = this.takeResData(brokerageInfo);
				logger.debug("[" + reqID + "]@@" + "[佣金余额查询成功！！！]");
			}else{
				logger.warn("[" + reqID + "]@@"+ "余额查询为空！！！");
			}
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@"+ "余额查询失败！！！" + e.getMessage());
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
			//TOOD 2015年12月18日09:51:53
			try {
				logger.debug("佣金兑换请求数据异步写入DAMS--->>>" + JsonUtil.getObjectToJson(object));
				damsPcYJRechargeTool.sendMessage(JsonUtil.getObjectToJson(object));
			} catch (Exception e) {
				logger.warn("佣金兑换 请求数据写入DAMS失败！！！");
			}
		}
		
	
		
		logger.info("[" + reqID + "]订单编号:"+brokerageRecharge.getOrderId()+" 佣金充值记录[userID]:" + brokerageRecharge.getUserID() +"充值金额:" + brokerageRecharge.getNumber() +"充值后余额:" + brokerageInfo.getBalance());
		logger.info("[" + reqID + "]@@"+ "[***佣金兑换成功***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(resMap);
	}
	
	/**
	 * 创建充值对象
	 * @param brokerageConsume
	 * @return
	 */
	private OperationInfo getOperationInfo(BrokerageRecharge brokerageRecharge){
		OperationInfo operationInfo = new OperationInfo();
		
		operationInfo.setUserId(brokerageRecharge.getUserID());//userID
		operationInfo.setNumber(brokerageRecharge.getNumber());//金额
		try {
			//订单提交时间
			operationInfo.setTime(unixTimeFmt(Long.parseLong(brokerageRecharge.getTime())));
		} catch (Exception e) {
			logger.warn("时间转换失败！！！",e);
		}
		
		operationInfo.setOrderId(brokerageRecharge.getOrderId());//订单编号
		operationInfo.setClientType(brokerageRecharge.getClientType());//设备类型
		operationInfo.setVersion(brokerageRecharge.getVersion());//版本
		
		operationInfo.setType(PcConstant.PC_DEALTYPE_RG);//交易类型
		operationInfo.setAccountType(PcConstant.PC_ACCOUNTTYPE_YJ);//币种类型
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
	
	private Map takeResData(BrokerageInfo brokerageInfo){
		Map resData = new HashMap();
		resData.put("userID", brokerageInfo.getUserId());
		resData.put("laterNumber", brokerageInfo.getBalance());
		resData.put("accountType","BrokerageAccount");
		return resData;
	}
	
	/**
	 * 数据校验
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private BrokerageRecharge checkData(Object object) throws Exception {
		BrokerageRecharge brokerageRecharge = (BrokerageRecharge) object;
		if (brokerageRecharge == null) {
			throw new Exception("[对象转换失败！！！]");
		}
		if(brokerageRecharge.getUserID() == null){
			throw new Exception("[UserID为空！！！]");
		}
		if(brokerageRecharge.getNumber() == null){
			throw new Exception("[金额_Number为空！！！]");
		}
		
		if(brokerageRecharge.getOrderId() == null){
			throw new Exception("[OrderId为空！！！]");
		}
		if(brokerageRecharge.getClientType() == null){
			throw new Exception("[ClientType为空！！！]");
		}
		if(brokerageRecharge.getVersion() == null){
			throw new Exception("[Version为空！！！]");
		}
		
		return brokerageRecharge;
	}
	public ControlAgent getControlAgent() {
		return controlAgent;
	}
	public void setControlAgent(ControlAgent controlAgent) {
		this.controlAgent = controlAgent;
	}
	
	


}
