package com.ttmv.datacenter.paycenter.service.processor.constant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年3月02日 下午4:55:16
 * @explain :支付中心常量
 */
public interface PcConstant {
	//********************账户类型****************
	/** T币账户*/
	Integer PC_ACCOUNTTYPE_TB = 0; 
	/** T券账户*/
	Integer PC_ACCOUNTTYPE_TQ = 1; 
	/** 佣金账户*/
	Integer PC_ACCOUNTTYPE_YJ = 2; 
	
	//********************交易类型****************
	/** 消费*/
	Integer PC_DEALTYPE_CU = -1; 
	/** 充值*/
	Integer PC_DEALTYPE_RG = 1; 
	
	
	
}
