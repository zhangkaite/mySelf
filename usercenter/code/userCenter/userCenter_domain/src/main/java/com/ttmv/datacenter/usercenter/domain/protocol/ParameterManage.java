package com.ttmv.datacenter.usercenter.domain.protocol;


/** 
 * 019_参数管理
 **/

public class ParameterManage {

private Integer reqType; //参数管理类型
private Integer id; //参数ID
private Integer key; //参数编号
private Integer type; //参数类型
private String value; //参数值
public void setReqType (Integer reqType) {
    this.reqType = reqType;
}
public Integer getReqType() { 
     return this.reqType;
}
public void setId (Integer id) {
    this.id = id;
}
public Integer getId() { 
     return this.id;
}
public void setKey (Integer key) {
    this.key = key;
}
public Integer getKey() { 
     return this.key;
}
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
