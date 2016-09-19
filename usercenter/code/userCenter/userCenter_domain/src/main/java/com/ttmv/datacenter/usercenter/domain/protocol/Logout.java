package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;


/** 
 * 021_用户注销
 **/

public class Logout {

private BigInteger userID; //用户ID
private String token; //登录令牌
private Integer clientType; //客户端类型
private String ip; //ip地址
private String mac; //mac地址
private String hdNum; //硬盘号
public void setUserID (BigInteger userID) {
    this.userID = userID;
}
public BigInteger getUserID() { 
     return this.userID;
}
public void setToken (String token) {
    this.token = token;
}
public String getToken() { 
     return this.token;
}
public void setClientType (Integer clientType) {
    this.clientType = clientType;
}
public Integer getClientType() { 
     return this.clientType;
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
}
