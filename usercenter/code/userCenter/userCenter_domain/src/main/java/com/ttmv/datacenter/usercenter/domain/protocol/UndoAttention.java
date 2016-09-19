package com.ttmv.datacenter.usercenter.domain.protocol;

import java.math.BigInteger;


/** 
 * 040_用户取消关注
 **/

public class UndoAttention {

private BigInteger userID; //用户ID
private BigInteger attentionId; //关注ID
public void setUserID (BigInteger userID) {
    this.userID = userID;
}
public BigInteger getUserID() { 
     return this.userID;
}
public void setAttentionId (BigInteger attentionId) {
    this.attentionId = attentionId;
}
public BigInteger getAttentionId() { 
     return this.attentionId;
}
}
