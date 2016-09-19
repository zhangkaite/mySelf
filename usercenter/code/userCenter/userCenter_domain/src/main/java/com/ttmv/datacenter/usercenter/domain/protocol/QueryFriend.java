package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;


/** 
 * 036_用户好友列表查询
 **/

public class QueryFriend {

private BigInteger userID; //用户ID
private Integer type;//关系类型


public Integer getType() {
	return type;
}
public void setType(Integer type) {
	this.type = type;
}
public void setUserID (BigInteger userID) {
    this.userID = userID;
}
public BigInteger getUserID() { 
     return this.userID;
}
}
