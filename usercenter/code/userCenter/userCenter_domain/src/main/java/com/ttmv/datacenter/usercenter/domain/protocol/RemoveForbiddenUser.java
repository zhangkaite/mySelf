package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;


/** 
 * 026_解冻、撤销删除用户
 **/

public class RemoveForbiddenUser {

private BigInteger userID; //用户ID
private Integer type; //操作类型
private String reason; //原因说明
private Integer handlerID; //操作者ID
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
public void setReason (String reason) {
    this.reason = reason;
}
public String getReason() { 
     return this.reason;
}
public void setHandlerID (Integer handlerID) {
    this.handlerID = handlerID;
}
public Integer getHandlerID() { 
     return this.handlerID;
}
}
