package com.ttmv.datacenter.paycenter.domain.protocol;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;


/** 
 * 01006_佣金提现
 **/

public class BrokerageConsume {

private BigInteger userID; //用户ID
private String handlingID; //受理单编号
private BigDecimal number; //提现金额
private String time; //订单提交时间
private String orderId;//订单编号
private String clientType;//设备类型 
private String version;//版本号








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
public BigInteger getUserID() {
	return userID;
}
public void setUserID(BigInteger userID) {
	this.userID = userID;
}
public void setHandlingID (String handlingID) {
    this.handlingID = handlingID;
}
public String getHandlingID() { 
     return this.handlingID;
}

public BigDecimal getNumber() {
	return number;
}
public void setNumber(BigDecimal number) {
	this.number = number;
}
public void setTime (String time) {
    this.time = time;
}
public String getTime() { 
     return this.time;
}
}
