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
import com.ttmv.datacenter.paycenter.domain.protocol.TBConsume;
import com.ttmv.datacenter.paycenter.service.facade.template.AbstractPayCenter;
import com.ttmv.datacenter.paycenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.paycenter.service.facade.tools.jmstool.DamsPcTBConsumeTool;
import com.ttmv.datacenter.paycenter.service.facade.tools.util.JsonUtil;
import com.ttmv.datacenter.paycenter.service.processor.constant.ControlSwitchConstant;
import com.ttmv.datacenter.paycenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.paycenter.service.processor.constant.PcConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月9日 上午11:31:36  
 * @explain :T币消费
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TBConsumeServiceImpl extends AbstractPayCenter{
	private static Logger logger = LogManager.getLogger(TBConsumeServiceImpl.class);
	private TcoinInfoDao tcoinInfoDao;
	//DAMON 2015年12月17日19:47:33（TB消费统计分析队列）
	private DamsPcTBConsumeTool damsPcTBConsumeTool;
	private ControlAgent controlAgent;//交易流水信息控制开关
	
	
	public ControlAgent getControlAgent() {
		return controlAgent;
	}
	public void setControlAgent(ControlAgent controlAgent) {
		this.controlAgent = controlAgent;
	}
	public DamsPcTBConsumeTool getDamsPcTBConsumeTool() {
		return damsPcTBConsumeTool;
	}
	public void setDamsPcTBConsumeTool(DamsPcTBConsumeTool damsPcTBConsumeTool) {
		this.damsPcTBConsumeTool = damsPcTBConsumeTool;
	}
	
	public TcoinInfoDao getTcoinInfoDao() {
		return tcoinInfoDao;
	}
	public void setTcoinInfoDao(TcoinInfoDao tcoinInfoDao) {
		this.tcoinInfoDao = tcoinInfoDao;
	}

	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[T币消费]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		// 数据检查
		TBConsume tbConsume = null;
		try {
			tbConsume = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		OperationInfo getOperationInfo = this.getOperationInfo(tbConsume);
		getOperationInfo.setReqId(reqID);
		//T币消费（扣币）
		try {
			tcoinInfoDao.changeBalance(getOperationInfo);
			logger.debug("[" + reqID + "]@@" + "[T币扣费成功！！！]");
		} catch (Exception e) {
			if(ErrorCodeConstant.ERRORMSG_ACCOUNTNSF_CODE.equals(e.getMessage())){
				logger.error("[" + reqID + "]@@"+"[userID]" + tbConsume.getUserID() + "账户余额不足！！！",e);
				return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_BUSINESS_ERROR_CODE,ErrorCodeConstant.ERRORMSG_ACCOUNTNSF_CODE);
			}else if(ErrorCodeConstant.ERRORMSG_ACCOUNTERR_CODE.equals(e.getMessage())){
				logger.error("[" + reqID + "]@@"+"[userID]" + tbConsume.getUserID() + "账户不存在！！！",e);
				return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_BUSINESS_ERROR_CODE,ErrorCodeConstant.ERRORMSG_ACCOUNTERR_CODE);
			}
			logger.error("[" + reqID + "]@@" + "扣币异常！！！" , e);
			return ResponseTool.returnException();
		}
		
		//查询余额拼返回数据
		Map resMap = null;
		TcoinInfo tInfo = null;
		try {
			logger.debug("[" + reqID + "]@@" + "[开始T币余额查询...]userID:" + tbConsume.getUserID());
			tInfo = tcoinInfoDao.selectTcoinInfo(tbConsume.getUserID());
			if(tInfo!=null){
				logger.debug("[" + reqID + "]@@" + "[T币余额查询成功！！！]余额:" + tInfo.getBalance());
				resMap = this.takeResData(tInfo);
			}else{
				logger.warn("[" + reqID + "]@@"+"[userID]" + tbConsume.getUserID() + "余额查询为空！！！");
			}
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@"+"[userID]" + tbConsume.getUserID() + "余额查询失败！！！" , e);
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
			//TODO 发送到统计分析系统  2015年12月18日09:51:173
			try {
				logger.debug("T币消费数据异步写入DAMS--->>>" + JsonUtil.getObjectToJson(object));
				damsPcTBConsumeTool.sendMessage(JsonUtil.getObjectToJson(object));
			} catch (Exception e) {
				logger.warn("TB消费 请求数据写入DAMS失败！！！");
			}
		}
		
	
		
		logger.info("[" + reqID + "]订单编号:"+tbConsume.getOrderId()+" T币消费记录[userID]:" + tbConsume.getUserID() +"消费金额:" + tbConsume.getNumber() +"消费后余额:" + tInfo.getBalance());
		logger.info("[" + reqID + "]@@"+"[userID]" + tbConsume.getUserID() + "[***T币消费成功***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(resMap);
	}
	
	/**
	 * 创建对象
	 * @param brokerageConsume
	 * @return
	 */
	private OperationInfo getOperationInfo(TBConsume tbConsume){
		OperationInfo operationInfo = new OperationInfo();
		
		operationInfo.setUserId(tbConsume.getUserID());//userID
		operationInfo.setNumber(tbConsume.getNumber());//金额
		try {
			//订单提交时间
			operationInfo.setTime(unixTimeFmt(Long.parseLong(tbConsume.getTime())));
		} catch (Exception e) {
			logger.warn("时间转换失败！！！",e);
		}
		
		operationInfo.setDestinationUserID(tbConsume.getDestinationUserID());//消费对象
		operationInfo.setProductID(tbConsume.getProductID());//商品编号
		operationInfo.setProductCount(tbConsume.getProductCount());//商品数量
		operationInfo.setProductPrice(tbConsume.getProductPrice()); //商品单价
		
		operationInfo.setOrderId(tbConsume.getOrderId());//订单编号
		operationInfo.setClientType(tbConsume.getClientType());//设备类型
		operationInfo.setVersion(tbConsume.getVersion());//版本
		
		operationInfo.setType(PcConstant.PC_DEALTYPE_CU);//交易类型
		operationInfo.setAccountType(PcConstant.PC_ACCOUNTTYPE_TB);//币种类型
		if(tbConsume.getRoomID()!=null){
			operationInfo.setRoomID(tbConsume.getRoomID());//频道号
		}
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
	private TBConsume checkData(Object object) throws Exception {
		TBConsume tbConsume = (TBConsume) object;
		if (tbConsume == null) {
			throw new Exception("[对象转换失败！！！]");
		}
		if(tbConsume.getUserID() == null){
			throw new Exception("[UserID为空！！！]");
		}
		if(tbConsume.getNumber() == null){
			throw new Exception("[消费金额_Number为空！！！]");
		}
		if(tbConsume.getDestinationUserID() == null){
			throw new Exception("[消费对象_DestinationUserID为空！！！]");
		}
		if(tbConsume.getProductID() == null){
			throw new Exception("[商品编号_ProductID为空！！！]");
		}
		if(tbConsume.getProductPrice() == null){
			throw new Exception("[商品单价_ProductPrice为空！！！]");
		}
		if(tbConsume.getProductCount() == null){
			throw new Exception("[消费数量_ProductCount为空！！！]");
		}
		
		if(tbConsume.getOrderId() == null){
			throw new Exception("[OrderId为空！！！]");
		}
		if(tbConsume.getClientType() == null){
			throw new Exception("[ClientType为空！！！]");
		}
		if(tbConsume.getVersion() == null){
			throw new Exception("[Version为空！！！]");
		}

		return tbConsume;
	}
	

}
