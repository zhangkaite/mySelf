package com.datacenter.dams.business.center.input;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.service.BillInter;
import com.datacenter.dams.business.service.FormInter;
import com.datacenter.dams.input.queue.entity.BrokerageRechargeInfo;
import com.google.common.base.Strings;

/**
 * 佣金 兑换中心
 * @author wll
 *
 */
public class BrokerageRechargeServiceCenter {

	private static Logger logger = LogManager.getLogger(BrokerageRechargeServiceCenter.class);

	private FormInter brokerageRechargeFormService;
	private BillInter brokerageBillService;
	public void handler(Object object)throws Exception{
		if(object == null){
			return ;
		}
		BrokerageRechargeInfo info = (BrokerageRechargeInfo)object;
		if(!this.checkObject(info)){
			return;
		}
		/* 数据存入da系统队列 */
		brokerageRechargeFormService.handler(info);
		/* 数据写入账单*/
		brokerageBillService.handler(info, "1");
	}
	
	/**
	 * 检查对象的有效性
	 * @param info
	 * @return
	 * @throws Exception
	 */
	private boolean checkObject(BrokerageRechargeInfo info)throws Exception{
		boolean flag = true;
		if(info == null){
			return flag;
		}
		if(info.getUserID() == null){
			return flag = false;
		}
		if(info.getNumber() == null){
			return flag = false;
		}
		if(Strings.isNullOrEmpty(info.getClientType())){
			return flag = false;
		}
		if(Strings.isNullOrEmpty(info.getOrderId())){
			return flag = false;
		}
		if(Strings.isNullOrEmpty(info.getTime())){
			return flag = false;
		}
		if(Strings.isNullOrEmpty(info.getVersion())){
			return flag = false;
		}
		return flag;
	}

	public FormInter getBrokerageRechargeFormService() {
		return brokerageRechargeFormService;
	}

	public void setBrokerageRechargeFormService(FormInter brokerageRechargeFormService) {
		this.brokerageRechargeFormService = brokerageRechargeFormService;
	}

	public BillInter getBrokerageBillService() {
		return brokerageBillService;
	}

	public void setBrokerageBillService(BillInter brokerageBillService) {
		this.brokerageBillService = brokerageBillService;
	}
	
}
