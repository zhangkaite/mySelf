package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;


/** 
 * 038_用户安全信息查询
 **/

public class QuerySecurityInfo {

private BigInteger userID; //用户ID
public void setUserID (BigInteger userID) {
    this.userID = userID;
}
public BigInteger getUserID() { 
     return this.userID;
}
}
