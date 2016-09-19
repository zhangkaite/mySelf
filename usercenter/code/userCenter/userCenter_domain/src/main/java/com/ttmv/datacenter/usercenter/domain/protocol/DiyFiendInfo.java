package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;


/** 
 * 046_好友信息DIY
 **/

public class DiyFiendInfo {

private BigInteger userID; //用户ID
private Integer type; //类型
private BigInteger friendId; //好友ID
private String remarkName; //好友备注姓名
private BigInteger groupId; //好友所在组编号
public void setUserID (BigInteger userID) {
    this.userID = userID;
}
public BigInteger getUserID() { 
     return this.userID;
}
public void setType (Integer type) {
    this.type = type;
}
public Integer getType() { 
     return this.type;
}
public void setFriendId (BigInteger friendId) {
    this.friendId = friendId;
}
public BigInteger getFriendId() { 
     return this.friendId;
}
public void setRemarkName (String remarkName) {
    this.remarkName = remarkName;
}
public String getRemarkName() { 
     return this.remarkName;
}
public void setGroupId (BigInteger groupId) {
    this.groupId = groupId;
}
public BigInteger getGroupId() { 
     return this.groupId;
}
}
