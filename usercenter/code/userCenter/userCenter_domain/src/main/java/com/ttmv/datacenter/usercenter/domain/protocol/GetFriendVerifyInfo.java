package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年3月13日 上午10:50:51  
 * @explain :获取好友验证信息
 */
public class GetFriendVerifyInfo {
	private BigInteger userID;//用户ID

	public BigInteger getUserID() {
		return userID;
	}

	public void setUserID(BigInteger userID) {
		this.userID = userID;
	}
	
	
	
}
