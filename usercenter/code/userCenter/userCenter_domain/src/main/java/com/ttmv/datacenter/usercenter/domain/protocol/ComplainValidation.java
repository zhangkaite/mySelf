package com.ttmv.datacenter.usercenter.domain.protocol;


/** 
 * 045_申诉账号检测
 **/

public class ComplainValidation {

private Integer type; //申诉账号类型
private String value; //号码
public void setType (Integer type) {
    this.type = type;
}
public Integer getType() { 
     return this.type;
}
public void setValue (String value) {
    this.value = value;
}
public String getValue() { 
     return this.value;
}
}
