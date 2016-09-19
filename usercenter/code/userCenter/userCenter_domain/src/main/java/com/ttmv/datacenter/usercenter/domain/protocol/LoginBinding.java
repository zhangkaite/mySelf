package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;


/** 
 * 004_登录方式开通
 **/

public class LoginBinding {

private Integer type; //登陆方式绑定类型
private BigInteger userID; //用户ID
private String value; //开通号码
private String token;//token




public String getToken() {
	return token;
}
public void setToken(String token) {
	this.token = token;
}
public void setType (Integer type) {
    this.type = type;
}
public Integer getType() { 
     return this.type;
}
public void setUserID (BigInteger userID) {
    this.userID = userID;
}
public BigInteger getUserID() { 
     return this.userID;
}
public void setValue (String value) {
    this.value = value;
}
public String getValue() { 
     return this.value;
}
}
