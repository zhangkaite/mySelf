package com.ttmv.datacenter.usercenter.domain.protocol;


/** 
 * 018_账户信息校验
 **/

public class UserValidation {

private String type; //校验类型
private String value; //校验值
public void setType (String type) {
    this.type = type;
}
public String getType() { 
     return this.type;
}
public void setValue (String value) {
    this.value = value;
}
public String getValue() { 
     return this.value;
}
}
