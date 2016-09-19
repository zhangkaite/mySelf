package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;
import java.util.List;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月5日 下午11:42:40  
 * @explain :
 */
public class QueryFriendGroupInfo {
	private List<BigInteger> groupIDs; //用户ID

	public List<BigInteger> getGroupIDs() {
		return groupIDs;
	}

	public void setGroupIDs(List<BigInteger> groupIDs) {
		this.groupIDs = groupIDs;
	}
	
}
