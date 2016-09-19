package com.ttmv.datacenter.paycenter.domain.protocol;

import java.math.BigInteger;
import java.util.List;


/** 
 * 02001_用户账户余额查询
 **/

public class UserQueryAccountBalance {

private BigInteger userID; //用户ID
private String time; //请求时间
List<Integer> accountTypes; //查询账户类型
public void setUserID (BigInteger userID) {
    this.userID = userID;
}
public BigInteger getUserID() { 
     return this.userID;
}
public void setTime (String time) {
    this.time = time;
}
public String getTime() { 
     return this.time;
}
public List<Integer> getAccountTypes() {
	return accountTypes;
}
public void setAccountTypes(List<Integer> accountTypes) {
	this.accountTypes = accountTypes;
}

}
