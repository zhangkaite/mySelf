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
import com.ttmv.datacenter.paycenter.dao.interfaces.TquanInfoDao;
import com.ttmv.datacenter.paycenter.data.OperationInfo;
import com.ttmv.datacenter.paycenter.data.TquanInfo;
import com.ttmv.datacenter.paycenter.domain.protocol.TQRecharge;
import com.ttmv.datacenter.paycenter.service.facade.template.AbstractPayCenter;
import com.ttmv.datacenter.paycenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.paycenter.service.facade.tools.jmstool.DamsPcTQRechargeTool;
import com.ttmv.datacenter.paycenter.service.facade.tools.util.JsonUtil;
import com.ttmv.datacenter.paycenter.service.processor.constant.ControlSwitchConstant;
import com.ttmv.datacenter.paycenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.paycenter.service.processor.constant.PcConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月9日 上午11:24:33  
 * @explain :T券充值
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TQRechargeServiceImpl extends AbstractPayCenter{
	private static Logger logger = LogManager.getLogger(TQRechargeServiceImpl.class);
	private TquanInfoDao tquanInfoDao;
	private DamsPcTQRechargeTool damsPcTQRechargeTool;
	private ControlAgent controlAgent;//交易流水信息控制开关
	
	
	public ControlAgent getControlAgent() {
		return controlAgent;
	}
	public void setControlAgent(ControlAgent controlAgent) {
		this.controlAgent = controlAgent;
	}
	public DamsPcTQRechargeTool getDamsPcTQRechargeTool() {
		return damsPcTQRechargeTool;
	}
	public void setDamsPcTQRechargeTool(DamsPcTQRechargeTool damsPcTQRechargeTool) {
		this.damsPcTQRechargeTool = damsPcTQRechargeTool;
	}
	public TquanInfoDao getTquanInfoDao() {
		return tquanInfoDao;
	}
	public void setTquanInfoDao(TquanInfoDao tquanInfoDao) {
		this.tquanInfoDao = tquanInfoDao;
	}

	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[T券充值]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		// 数据检查
		TQRecharge tqRecharge = null;
		try {
			tqRecharge = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		OperationInfo operationInfo = this.getOperationInfo(tqRecharge);
		operationInfo.setReqId(reqID);
		//充值
		try {
			tquanInfoDao.changeBalance(operationInfo);
			logger.debug("[" + reqID + "]@@" + "[T券充值成功！！！]");
		} catch (Exception e) {
			if(ErrorCodeConstant.ERRORMSG_ACCOUNTERR_CODE.equals(e.getMessage())){
				logger.error("[" + reqID + "]@@"+"[userID]" + tqRecharge.getUserID() + "账户不存在！！！",e);
				return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_BUSINESS_ERROR_CODE,ErrorCodeConstant.ERRORMSG_ACCOUNTERR_CODE);
			}
			logger.error("[" + reqID + "]@@" + "TQ充值异常！！！" , e);
			return ResponseTool.returnException();
		}
		Map resMap = null;
		TquanInfo tInfo = null;
		try {
			logger.debug("[" + reqID + "]@@" + "[开始T券余额查询...]");
			tInfo = tquanInfoDao.selectTquanInfo(tqRecharge.getUserID());
			if(tInfo != null){
				logger.debug("[" + reqID + "]@@" + "[T券余额查询成功！！！]");
				resMap = this.takeResData(tInfo);
			}else{
				logger.warn("[" + reqID + "]@@" + "余额查询为空！！！");
			}
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "余额查询异常！！！" + e.getMessage());
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
			//TODO 2015年12月17日21:05:16
			try {
				logger.debug("T券充值请求数据异步写入DAMS--->>>" + JsonUtil.getObjectToJson(object));
				damsPcTQRechargeTool.sendMessage(JsonUtil.getObjectToJson(object));
			} catch (Exception e) {
				logger.warn("TQ充值 请求数据写入DAMS失败！！！");
			}
		}
		

		
		logger.info("[" + reqID + "]订单编号:"+tqRecharge.getOrderId()+" T券充值记录[userID]:" + tqRecharge.getUserID() +"充值金额:" + tqRecharge.getNumber() +"充值后余额:" + tInfo.getBalance());
		logger.info("[" + reqID + "]@@"+ "[***T券充值***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(resMap);
	}
	
	/**
	 * 创建对象
	 * @param brokerageConsume
	 * @return
	 */
	private OperationInfo getOperationInfo(TQRecharge tqRecharge){
		OperationInfo operationInfo = new OperationInfo();
		
		operationInfo.setUserId(tqRecharge.getUserID());//userID
		operationInfo.setNumber(tqRecharge.getNumber());//金额
		try {
			//订单提交时间
			operationInfo.setTime(unixTimeFmt(Long.parseLong(tqRecharge.getTime())));
		}  catch (Exception e) {
			logger.warn("时间转换失败！！！",e);
		}
		
		operationInfo.setType(tqRecharge.getType());
		operationInfo.setTDNumber(tqRecharge.getTDNumber());
		
		operationInfo.setOrderId(tqRecharge.getOrderId());//订单编号
		operationInfo.setClientType(tqRecharge.getClientType());//设备类型
		operationInfo.setVersion(tqRecharge.getVersion());//版本
		
		operationInfo.setType(PcConstant.PC_DEALTYPE_RG);//交易类型
		operationInfo.setAccountType(PcConstant.PC_ACCOUNTTYPE_TQ);//币种类型
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
	private Map takeResData(TquanInfo tInfo){
		Map resData = new HashMap();
		resData.put("userID", tInfo.getUserId());
		resData.put("laterNumber", tInfo.getBalance());
		resData.put("accountType","TQAccount");
		return resData;
	}
	
	
	/**
	 * 数据校验
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private TQRecharge checkData(Object object) throws Exception {
		TQRecharge tqRecharge = (TQRecharge) object;
		if (tqRecharge == null) {
			throw new Exception("[对象转换失败！！！]");
		}
		if(tqRecharge.getUserID() == null){
			throw new Exception("[UserID为空！！！]");
		}
		if(tqRecharge.getNumber() == null){
			throw new Exception("[Number为空！！！]");
		}
		if(tqRecharge.getType() == null){
			throw new Exception("[充值类型_Type为空！！！]");
		}
		
		if(tqRecharge.getOrderId() == null){
			throw new Exception("[OrderId为空！！！]");
		}
		if(tqRecharge.getClientType() == null){
			throw new Exception("[ClientType为空！！！]");
		}
		if(tqRecharge.getVersion() == null){
			throw new Exception("[Version为空！！！]");
		}

		return tqRecharge;
	}

}
