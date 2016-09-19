package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;


/** 
 * 017_用户信息绑定
 **/

public class UserInfoBinding {

private BigInteger userID; //用户ID
private Integer type; //绑定类型
private String value; //绑定号码
private String token;


public String getToken() {
	return token;
}
public void setToken(String token) {
	this.token = token;
}
public void setUserID (BigInteger userID) {
    this.userID = userID;
}
public BigInteger getUserID() { 
     return this.userID;
}
public void setType (Integer type) {
    this.type = type;
}
public Integer getType() { 
     return this.type;
}
public String getValue() {
	return value;
}
public void setValue(String value) {
	this.value = value;
}

}
