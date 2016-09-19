package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;
import java.util.List;

/** 
 * 047_用户好友信息列表获取
 **/

public class QueryFriendInfos {

private List<BigInteger> userIDs; //用户ID
public void setUserIDs (List<BigInteger> userIDs) {
    this.userIDs = userIDs;
}
public List<BigInteger> getUserIDs() { 
     return this.userIDs;
}
}
