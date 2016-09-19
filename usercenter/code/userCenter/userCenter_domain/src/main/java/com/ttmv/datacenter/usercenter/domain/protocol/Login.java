package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;


/** 
 * 020_用户登录
 **/

public class Login {

private Integer type; //登录类型
private String loginName; //用户名称
private String password; //密码
private Integer clientType; //客户端类型
private String ip; //ip地址
private String mac; //mac地址
private String hdNum; //硬盘号
private String seat; //登录地点
private String key; //第三方登录令牌
private String token; //快速登录传递的标识
private BigInteger userID; //用户ID
public void setType (Integer type) {
    this.type = type;
}
public Integer getType() { 
     return this.type;
}
public void setLoginName (String loginName) {
    this.loginName = loginName;
}
public String getLoginName() { 
     return this.loginName;
}
public void setPassword (String password) {
    this.password = password;
}
public String getPassword() { 
     return this.password;
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
public void setSeat (String seat) {
    this.seat = seat;
}
public String getSeat() { 
     return this.seat;
}
public void setKey (String key) {
    this.key = key;
}
public String getKey() { 
     return this.key;
}
public void setToken (String token) {
    this.token = token;
}
public String getToken() { 
     return this.token;
}
public void setUserID (BigInteger userID) {
    this.userID = userID;
}
public BigInteger getUserID() { 
     return this.userID;
}
}
