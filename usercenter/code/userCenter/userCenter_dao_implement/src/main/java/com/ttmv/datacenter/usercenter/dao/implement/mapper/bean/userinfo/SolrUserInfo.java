package com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.userinfo;

import java.util.Date;
import java.util.Map;

import org.apache.solr.client.solrj.beans.Field;

public class SolrUserInfo {

	@Field
	private String id;
	@Field
	private String dataSourceKey;
	@Field
	private String userName;
	@Field
	private String password;
	@Field
	private String loginMobile;
	@Field
	private String bindingMobile;
	@Field
	private String loginEmail;
	@Field
	private String bindingEmail;
	@Field
	private String adminId;
	@Field
	private String TTnum;
	@Field
	private String nickName;
	@Field
	private Integer sex;
	@Field
	private Date time;
	@Field
	private String city;
	@Field
	private String address;
	@Field
	private Integer enrollterminal;
	@Field
	private String loginGoodTTnum;
	@Field
	private Integer loginGoodTTnumType;
	@Field
	private String openId;
	@Field
	private Integer utype;
	@Field
	private Integer state;
	@Field
	private Integer vipType;
	
	private Map<String,Boolean> sorfFields ;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDataSourceKey() {
		return dataSourceKey;
	}
	public void setDataSourceKey(String dataSourceKey) {
		this.dataSourceKey = dataSourceKey;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginMobile() {
		return loginMobile;
	}
	public void setLoginMobile(String loginMobile) {
		this.loginMobile = loginMobile;
	}
	public String getBindingMobile() {
		return bindingMobile;
	}
	public void setBindingMobile(String bindingMobile) {
		this.bindingMobile = bindingMobile;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getTTnum() {
		return TTnum;
	}
	public void setTTnum(String tTnum) {
		TTnum = tTnum;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getLoginEmail() {
		return loginEmail;
	}
	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}
	public String getBindingEmail() {
		return bindingEmail;
	}
	public void setBindingEmail(String bindingEmail) {
		this.bindingEmail = bindingEmail;
	}
	public Map<String, Boolean> getSorfFields() {
		return sorfFields;
	}
	public void setSorfFields(Map<String, Boolean> sorfFields) {
		this.sorfFields = sorfFields;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getEnrollterminal() {
		return enrollterminal;
	}
	public void setEnrollterminal(Integer enrollterminal) {
		this.enrollterminal = enrollterminal;
	}
	public String getLoginGoodTTnum() {
		return loginGoodTTnum;
	}
	public void setLoginGoodTTnum(String loginGoodTTnum) {
		this.loginGoodTTnum = loginGoodTTnum;
	}
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Integer getUtype() {
		return utype;
	}
	public void setUtype(Integer utype) {
		this.utype = utype;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getVipType() {
		return vipType;
	}
	public void setVipType(Integer vipType) {
		this.vipType = vipType;
	}
	public Integer getLoginGoodTTnumType() {
		return loginGoodTTnumType;
	}
	public void setLoginGoodTTnumType(Integer loginGoodTTnumType) {
		this.loginGoodTTnumType = loginGoodTTnumType;
	}
}
