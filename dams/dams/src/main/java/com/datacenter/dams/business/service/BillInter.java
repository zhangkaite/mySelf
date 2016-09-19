package com.datacenter.dams.business.service;

/**
 * TB/TQ/佣金 消费/充值账单
 * @author wulinli
 *
 */
public interface BillInter {

	public void handler(Object object,String type)throws Exception;
}
