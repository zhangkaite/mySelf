package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;

/** 
 * 2015年10月13日20:08:39
 * 是否存在密码(三方登录原密码是否输入标示)
 **/
public class PwdValidation {
	
	private BigInteger userID;//用户ID

	public BigInteger getUserID() {
		return userID;
	}

	public void setUserID(BigInteger userID) {
		this.userID = userID;
	}
	
	

}
