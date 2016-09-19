package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;


/** 
 * 012_创建好友分组
 **/

public class CreateFriendGroup {

private BigInteger userID; //用户ID
private String groupName; //好友分组名称
private Integer order; //序号
public void setUserID (BigInteger userID) {
    this.userID = userID;
}
public BigInteger getUserID() { 
     return this.userID;
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
