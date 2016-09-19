package com.datacenter.dams.business.center.input;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.service.BillInter;
import com.datacenter.dams.business.service.FormInter;
import com.datacenter.dams.input.queue.entity.TcoinRechargeInfo;
import com.google.common.base.Strings;

/**
 * TB 充值中心
 * @author wll
 */
public class TcoinRechargeServiceCenter {

	private static Logger logger=LogManager.getLogger(TcoinRechargeServiceCenter.class);
	
	private FormInter tcoinRechargeFormService;
	private BillInter tcoinBillService;

	public void handler(Object object)throws Exception{
		if(object == null){
			return ;
		}
		TcoinRechargeInfo info = (TcoinRechargeInfo)object;
		if(!this.checkObject(info)){
			return ;
		}
		/* 数据写入报表队列 */
		tcoinRechargeFormService.handler(info);
		/* 数据写入账单*/
		tcoinBillService.handler(info, "1");
		
	}
	
	/**
	 * 判断充值数据是否有效
	 * @param tcoinRechargeInfo
	 * @return
	 */
	private boolean checkObject(TcoinRechargeInfo tcoinRechargeInfo){
		boolean flag = true;
		if(tcoinRechargeInfo == null){
			return flag = false;
		}
		if(tcoinRechargeInfo.getUserID() == null){
			return flag = false;
		}
		if(Strings.isNullOrEmpty(tcoinRechargeInfo.getClientType())){
			return flag = false;
		}
		if(Strings.isNullOrEmpty(tcoinRechargeInfo.getDataType())){
			return flag = false;
		}
		if(Strings.isNullOrEmpty(tcoinRechargeInfo.getOrderId())){
			return flag = false;
		}
		if(Strings.isNullOrEmpty(tcoinRechargeInfo.getTime())){
			return flag = false;
		}
		if(Strings.isNullOrEmpty(tcoinRechargeInfo.getVersion())){
			return flag = false;
		}
		if(tcoinRechargeInfo.getNumber() == null){
			return flag = false;
		}
		return flag;
	}
	
	
	public FormInter getTcoinRechargeFormService() {
		return tcoinRechargeFormService;
	}

	public void setTcoinRechargeFormService(FormInter tcoinRechargeFormService) {
		this.tcoinRechargeFormService = tcoinRechargeFormService;
	}

	public void setTcoinBillService(BillInter tcoinBillService) {
		this.tcoinBillService = tcoinBillService;
	}
}
