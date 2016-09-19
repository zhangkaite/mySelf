package com.ttmv.datacenter.paycenter.domain.protocol;


/** 
 * 通用域
 **/

public class GeneralPro {

private String platfrom; //请求业务系统
private String service; //请求服务名
private String timeStamp; //请求时间戳
private String tradeType; //交易类型
private Object reqData; //请求业务数据
private String reqID; //请求标示



public String getPlatfrom() {
	return platfrom;
}
public void setPlatfrom(String platfrom) {
	this.platfrom = platfrom;
}
public void setService (String service) {
    this.service = service;
}
public String getService() { 
     return this.service;
}
public void setTimeStamp (String timeStamp) {
    this.timeStamp = timeStamp;
}
public String getTimeStamp() { 
     return this.timeStamp;
}
public void setTradeType (String tradeType) {
    this.tradeType = tradeType;
}
public String getTradeType() { 
     return this.tradeType;
}

public Object getReqData() {
	return reqData;
}
public void setReqData(Object reqData) {
	this.reqData = reqData;
}
public void setReqID (String reqID) {
    this.reqID = reqID;
}
public String getReqID() { 
     return this.reqID;
}
}
