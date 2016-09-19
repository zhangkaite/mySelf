package com.ttmv.entity;


/** 
 * 01002_T卷充值
 **/

public class TQRecharge {

private String userID; //用户ID
private String time; //充值时间
private String number; //T劵增加数目

private Integer type; //充值类型---- 0:T豆结算零头转换 1：系统赠送

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
public void setUserID (String userID) {
    this.userID = userID;
}
public String getUserID() { 
     return this.userID;
}
public void setType (Integer type) {
    this.type = type;
}
public Integer getType() { 
     return this.type;
}
public void setTime (String time) {
    this.time = time;
}
public String getTime() { 
     return this.time;
}
public String getNumber() {
	return number;
}
public void setNumber(String number) {
	this.number = number;
}

}
