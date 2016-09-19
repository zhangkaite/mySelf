package com.datacenter.dams.business.center.input;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.service.BillInter;
import com.datacenter.dams.business.service.FormInter;
import com.datacenter.dams.input.queue.entity.BrokerageConsumeInfo;
import com.google.common.base.Strings;

/**
 * 佣金 提现中心
 * @author wll
 *
 */
public class BrokerageConsumeSpendCenter {

	private static Logger logger = LogManager.getLogger(BrokerageConsumeSpendCenter.class);

	private FormInter brokerageConsumeFormService;
	private BillInter brokerageBillService;

	public void handler(Object object)throws Exception{
		if(object == null){
			return ;
		}
		BrokerageConsumeInfo info = (BrokerageConsumeInfo)object;
		if(!this.checkObject(info)){
			logger.info("[DAMS#BrokerageConsumeSpendCenter]数据非法。");
			return;
		}
		/* 数据存入da系统 */
		brokerageConsumeFormService.handler(info);
		/* 数据写入账单*/
		brokerageBillService.handler(info, "-1");
	}
	

	/**
	 * 检查对象的有效性
	 * @param info
	 * @return
	 * @throws Exception
	 */
	private boolean checkObject(BrokerageConsumeInfo info)throws Exception{
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
	

	public FormInter getBrokerageConsumeFormService() {
		return brokerageConsumeFormService;
	}

	public void setBrokerageConsumeFormService(FormInter brokerageConsumeFormService) {
		this.brokerageConsumeFormService = brokerageConsumeFormService;
	}
	public BillInter getBrokerageBillService() {
		return brokerageBillService;
	}
	
	public void setBrokerageBillService(BillInter brokerageBillService) {
		this.brokerageBillService = brokerageBillService;
	}
}
