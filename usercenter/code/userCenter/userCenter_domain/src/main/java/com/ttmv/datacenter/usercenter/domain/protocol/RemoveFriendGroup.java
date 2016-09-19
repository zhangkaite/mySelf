package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;


/** 
 * 014_删除好友分组
 **/

public class RemoveFriendGroup {

private BigInteger userID; //用户ID
private BigInteger groupId; //好友分组ID
public void setUserID (BigInteger userID) {
    this.userID = userID;
}
public BigInteger getUserID() { 
     return this.userID;
}
public void setGroupId (BigInteger groupId) {
    this.groupId = groupId;
}
public BigInteger getGroupId() { 
     return this.groupId;
}
}
