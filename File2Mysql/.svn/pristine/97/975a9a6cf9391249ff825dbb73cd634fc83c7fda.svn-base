package com.ttmv.entity;

import java.text.SimpleDateFormat;
import java.util.Date;


/** 
 * 01001_T币充值
 **/

public class TBRecharge {

private String userID; //用户ID
private String time; //充值时间
private String number; //充值金额
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
public void setTime (String time) {
	Long timeStamp = new Long(time);
	SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    this.time = sdfs.format(new Date(Long.parseLong(String.valueOf(timeStamp))));
}
public String getTime() { 
     return this.time;
}
public void setNumber (String number) {
    this.number = number;
}
public String getNumber() { 
     return this.number;
}
}
