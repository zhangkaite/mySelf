package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;


/** 
 * 013_修改好友分组
 **/

public class ModifyFriendGroup {

private BigInteger userID; //用户ID
private BigInteger groupId; //好友分组ID
private String groupName; //好友分组名称
private Integer order; //序号
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
public void setGroupName (String groupName) {
    this.groupName = groupName;
}
public String getGroupName() { 
     return this.groupName;
}
public void setOrder (Integer order) {
    this.order = order;
}
public Integer getOrder() { 
     return this.order;
}
}
