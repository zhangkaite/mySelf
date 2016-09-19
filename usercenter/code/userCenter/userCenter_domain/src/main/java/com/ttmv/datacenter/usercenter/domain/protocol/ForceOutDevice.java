package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;


/** 
 * 023_封终端设备
 **/

public class ForceOutDevice {

private String TTnum;
private String ip; //ip地址
private String mac; //mac地址
private String hdNum; //硬盘号
private String reason; //原因说明
private BigInteger handlerID; //操作者ID


public String getTTnum() {
	return TTnum;
}
public void setTTnum(String tTnum) {
	TTnum = tTnum;
}
public void setIp (String ip) {
    this.ip = ip;
}
public String getIp() { 
     return this.ip;
}
public void setMac (String mac) {
    this.mac = mac;
}
public String getMac() { 
     return this.mac;
}
public void setHdNum (String hdNum) {
    this.hdNum = hdNum;
}
public String getHdNum() { 
     return this.hdNum;
}
public void setReason (String reason) {
    this.reason = reason;
}
public String getReason() { 
     return this.reason;
}
public void setHandlerID (BigInteger handlerID) {
    this.handlerID = handlerID;
}
public BigInteger getHandlerID() { 
     return this.handlerID;
}
}
