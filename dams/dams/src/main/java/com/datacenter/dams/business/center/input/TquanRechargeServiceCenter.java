package com.datacenter.dams.business.center.input;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.service.BillInter;
import com.datacenter.dams.business.service.FormInter;
import com.datacenter.dams.input.queue.entity.TquanRechargeInfo;
import com.google.common.base.Strings;

/**
 * T券 充值中心
 * @author wll
 */
public class TquanRechargeServiceCenter {

	private static Logger logger=LogManager.getLogger(TquanRechargeServiceCenter.class);
	
	private FormInter tquanRechargeFormService;
    private BillInter tquanBillService;
	public void handler(Object object)throws Exception{
		if(object == null){
			return ;
		}
		TquanRechargeInfo info = (TquanRechargeInfo)object;
		if(!this.checkObject(info)){
			return ;
		}
		/* 数据写入报表队列 */
		tquanRechargeFormService.handler(info);
		/* 数据写入账单*/
		tquanBillService.handler(info, "1");
	}
	

	/**
	 * 判断充值数据是否有效
	 * 隐含的业务：tq的充值可以通过T豆转换，也可以直接充值。
	 * 所以：tDnumber字段都可以为空
	 * @param tquanRechargeInfo
	 * @return
	 */
	private boolean checkObject(TquanRechargeInfo tquanRechargeInfo){
		boolean flag = true;
		if(tquanRechargeInfo == null){
			return flag = false;
		}
		if(tquanRechargeInfo.getUserID() == null){
			return flag = false;
		}
		if(tquanRechargeInfo.getType() == null){
			return flag = false;
		}
		if(Strings.isNullOrEmpty(tquanRechargeInfo.getClientType())){
			return flag = false;
		}
		if(Strings.isNullOrEmpty(tquanRechargeInfo.getDataType())){
			return flag = false;
		}
		if(Strings.isNullOrEmpty(tquanRechargeInfo.getOrderId())){
			return flag = false;
		}
		if(Strings.isNullOrEmpty(tquanRechargeInfo.getTime())){
			return flag = false;
		}
		if(Strings.isNullOrEmpty(tquanRechargeInfo.getVersion())){
			return flag = false;
		}
		if(tquanRechargeInfo.getNumber() == null){
			return flag = false;
		}
		return flag;
	}

	public FormInter getTquanRechargeFormService() {
		return tquanRechargeFormService;
	}

	public void setTquanRechargeFormService(FormInter tquanRechargeFormService) {
		this.tquanRechargeFormService = tquanRechargeFormService;
	}
	public BillInter getTquanBillService() {
		return tquanBillService;
	}
	
	public void setTquanBillService(BillInter tquanBillService) {
		this.tquanBillService = tquanBillService;
	}
}
