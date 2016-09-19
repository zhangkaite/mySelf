package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;
/** 
 * 055_登录号解绑
 **/
public class RelieveLoginBinding {
	private Integer type; //解绑号码类型
	private BigInteger userID; //用户ID
	private String value; //解绑号码
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
