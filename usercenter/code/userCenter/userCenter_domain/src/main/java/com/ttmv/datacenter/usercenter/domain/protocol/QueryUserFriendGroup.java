package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月5日 下午11:39:57  
 * @explain :048_查询用户好友分组信息及好友关系
 */
public class QueryUserFriendGroup {

	private BigInteger userID;

	public BigInteger getUserID() {
		return userID;
	}

	public void setUserID(BigInteger userID) {
		this.userID = userID;
	}
	
}
