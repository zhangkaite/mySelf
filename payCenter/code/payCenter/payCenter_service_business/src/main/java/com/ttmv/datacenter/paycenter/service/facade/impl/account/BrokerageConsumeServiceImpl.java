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
import com.ttmv.datacenter.paycenter.domain.protocol.BrokerageConsume;
import com.ttmv.datacenter.paycenter.service.facade.template.AbstractPayCenter;
import com.ttmv.datacenter.paycenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.paycenter.service.facade.tools.jmstool.DamsPcYJConsumeTool;
import com.ttmv.datacenter.paycenter.service.facade.tools.util.JsonUtil;
import com.ttmv.datacenter.paycenter.service.processor.constant.ControlSwitchConstant;
import com.ttmv.datacenter.paycenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.paycenter.service.processor.constant.PcConstant;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年2月9日 上午11:32:23
 * @explain :佣金提现
 */
@SuppressWarnings({ "rawtypes", "unchecked"})
public class BrokerageConsumeServiceImpl extends AbstractPayCenter {
	private static Logger logger = LogManager.getLogger(BrokerageConsumeServiceImpl.class);
	private BrokerageInfoDao brokerageInfoDao;
	//2015年12月17日21:00:22 Damon
	private DamsPcYJConsumeTool damsPcYJConsumeTool;
	
	private ControlAgent controlAgent;//交易流水信息控制开关
	
	
	public DamsPcYJConsumeTool getDamsPcYJConsumeTool() {
		return damsPcYJConsumeTool;
	}
	public void setDamsPcYJConsumeTool(DamsPcYJConsumeTool damsPcYJConsumeTool) {
		this.damsPcYJConsumeTool = damsPcYJConsumeTool;
	}
	public BrokerageInfoDao getBrokerageInfoDao() {
		return brokerageInfoDao;
	}
	public void setBrokerageInfoDao(BrokerageInfoDao brokerageInfoDao) {
		this.brokerageInfoDao = brokerageInfoDao;
	}

	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[佣金提现]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		// 数据检查
		BrokerageConsume brokerageConsume = null;
		try {
			brokerageConsume = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		//创建消费对象
		OperationInfo operationInfo = this.getOperationInfo(brokerageConsume);
		operationInfo.setReqId(reqID);
		logger.debug("[" + reqID + "]@@" + "[消费对象创建成功！！！]");
		try {
			brokerageInfoDao.changeBalance(operationInfo);
			logger.debug("[" + reqID + "]@@" + "[佣金消费成功！！！]");
		} catch (Exception e) {
			if("OP30001".equals(e.getMessage())){
				logger.error("[" + reqID + "]@@"+"[userID]" + brokerageConsume.getUserID() + "账户余额不足！！！",e);
				return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_BUSINESS_ERROR_CODE,ErrorCodeConstant.ERRORMSG_ACCOUNTNSF_CODE);
			}else if(ErrorCodeConstant.ERRORMSG_ACCOUNTERR_CODE.equals(e.getMessage())){
				logger.error("[" + reqID + "]@@"+"[userID]" + brokerageConsume.getUserID() + "账户不存在！！！",e);
				return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_BUSINESS_ERROR_CODE,ErrorCodeConstant.ERRORMSG_ACCOUNTERR_CODE);

			}
			logger.error("[" + reqID + "]@@" +"佣金提现异常！！！" ,e);
			return ResponseTool.returnException();
		}
		Map resMap = null;
		//余额查询
		BrokerageInfo brokerageInfo = null;
		try {
			logger.debug("[" + reqID + "]@@" + "[开始查询佣金余额...]");
			brokerageInfo = brokerageInfoDao.selectBrokerageInfo(brokerageConsume.getUserID());
			if(brokerageInfo!=null){
				resMap = this.takeResData(brokerageInfo);
				logger.debug("[" + reqID + "]@@" + "[佣金余额查询成功！！！]");
			}else{
				logger.warn("[" + reqID + "]@@" + "余额查询为空！！！");
			}
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "余额查询失败！！！" + e.getMessage());
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
			//TODO 2015年12月18日09:49:36
			try {
				logger.debug("佣金提现请求数据异步写入DAMS---->>>" + JsonUtil.getObjectToJson(object) );
				damsPcYJConsumeTool.sendMessage(JsonUtil.getObjectToJson(object));
			} catch (Exception e) {
				logger.warn("佣金提现请求数据写入DAMS失败！！！");
			}
		}
		logger.info("[" + reqID + "]订单编号:"+brokerageConsume.getOrderId()+" 佣金提现记录[userID]:" + brokerageConsume.getUserID() +"提现金额:" + brokerageConsume.getNumber() +"提现后余额:" + brokerageInfo.getBalance());
		logger.info("[" + reqID + "]@@"+"[userID]" + brokerageConsume.getUserID() + "[***佣金提现成功***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(resMap);
	}
	
	/**
	 * 创建消费对象
	 * @param brokerageConsume
	 * @return
	 */
	private OperationInfo getOperationInfo(BrokerageConsume brokerageConsume){
		OperationInfo operationInfo = new OperationInfo();
		
		operationInfo.setUserId(brokerageConsume.getUserID());//userID
		operationInfo.setNumber(brokerageConsume.getNumber());//金额
		try {
			//订单提交时间
			operationInfo.setTime(unixTimeFmt(Long.parseLong(brokerageConsume.getTime())));
		} catch (Exception e) {
			logger.warn("时间转换失败！！！",e);
		}
		operationInfo.setOrderId(brokerageConsume.getOrderId());//订单编号
		operationInfo.setClientType(brokerageConsume.getClientType());//设备类型
		operationInfo.setVersion(brokerageConsume.getVersion());//版本
		
		operationInfo.setType(PcConstant.PC_DEALTYPE_CU);//消费类型
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
	private BrokerageConsume checkData(Object object) throws Exception {
		BrokerageConsume brokerageConsume = (BrokerageConsume) object;
		if (brokerageConsume == null) {
			throw new Exception("[对象转换失败！！！]");
		}
		if(brokerageConsume.getUserID() == null){
			throw new Exception("[UserID为空！！！]");
		}
		if(brokerageConsume.getNumber() == null){
			throw new Exception("[提现金额_Number为空！！！]");
		}
//		if(brokerageConsume.getHandlingID() == null){
//			throw new Exception("[受理编号_HandlingID为空！！！]");
//		}
		
		if(brokerageConsume.getOrderId() == null){
			throw new Exception("[OrderId为空！！！]");
		}
		if(brokerageConsume.getClientType() == null){
			throw new Exception("[ClientType为空！！！]");
		}
		if(brokerageConsume.getVersion() == null){
			throw new Exception("[Version为空！！！]");
		}

		return brokerageConsume;
	}
	
	public ControlAgent getControlAgent() {
		return controlAgent;
	}
	public void setControlAgent(ControlAgent controlAgent) {
		this.controlAgent = controlAgent;
	}
	
	
	

}
