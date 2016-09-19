package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;


/** 
 * 041_用户添加好友
 **/

public class AddFriend {

private BigInteger userID; //用户ID
private BigInteger friendId; //好友ID
public void setUserID (BigInteger userID) {
    this.userID = userID;
}
public BigInteger getUserID() { 
     return this.userID;
}
public void setFriendId (BigInteger friendId) {
    this.friendId = friendId;
}
public BigInteger getFriendId() { 
     return this.friendId;
}
}
