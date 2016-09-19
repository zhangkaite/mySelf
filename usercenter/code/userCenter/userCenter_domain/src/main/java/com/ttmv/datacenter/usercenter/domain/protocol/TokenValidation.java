package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年3月10日 下午2:22:32  
 * @explain :token验证
 */
public class TokenValidation {

	private BigInteger userID;//用户ID
	
	private String token;//TOKEN
	
	private Integer clientType;//客户端类型

	public BigInteger getUserID() {
		return userID;
	}

	public void setUserID(BigInteger userID) {
		this.userID = userID;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}
	
	
	
}
