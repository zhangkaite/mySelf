package com.datacenter.dams.input.queue.entity;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BrokerageRechargeInfo {

	private BigInteger userID;							// 用户ID
	private String time;								//兑换时间
	private BigDecimal number;								//兑换总额
	private String orderId;								//订单编号
	private String clientType;							//设备类型
	private String version;								//版本号
	/* 数据类型 */
	private String dataType = "yj_recharge";
	
	public BigInteger getUserID() {
		return userID;
	}
	public void setUserID(BigInteger userID) {
		this.userID = userID;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public BigDecimal getNumber() {
		return number;
	}
	public void setNumber(BigDecimal number) {
		this.number = number;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}
