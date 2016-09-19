package com.ttmv.datacenter.paycenter.domain.protocol;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/** 
 * 01003_佣金兑换
 **/

public class BrokerageRecharge {

private BigInteger userID; //用户ID
private String time; //兑换时间
private BigDecimal number; //兑换金额
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
public void setUserID (BigInteger userID) {
    this.userID = userID;
}
public BigInteger getUserID() { 
     return this.userID;
}
public void setTime (String time) {
    this.time = time;
}
public String getTime() { 
     return this.time;
}
public BigDecimal getNumber() {
	return number;
}
public void setNumber(BigDecimal number) {
	this.number = number;
}

}
