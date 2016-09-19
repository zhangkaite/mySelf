package com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.userinfo;

import java.math.BigInteger;
import java.util.Date;

public class MysqlUserInfo {
    
	private BigInteger userId;
    private String username;
    private String TTnum;
    private String upassword;
    private String bindingemail;
    private String loginemail;
    private String bindingmobile;
    private String loginmobile;
    private byte[] mark = null;
    private String loginGoodTtnum;
    private Integer loginGoodTtnumType;
    private Date created;
    private Date updated;

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTTnum() {
		return TTnum;
	}

	public void setTTnum(String tTnum) {
		TTnum = tTnum;
	}

	public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword ;
    }

    public String getBindingemail() {
        return bindingemail;
    }

    public void setBindingemail(String bindingemail) {
        this.bindingemail = bindingemail ;
    }

    public String getLoginemail() {
        return loginemail;
    }

    public void setLoginemail(String loginemail) {
        this.loginemail = loginemail ;
    }

    public String getBindingmobile() {
        return bindingmobile;
    }

    public void setBindingmobile(String bindingmobile) {
        this.bindingmobile = bindingmobile;
    }

    public String getLoginmobile() {
        return loginmobile;
    }

    public void setLoginmobile(String loginmobile) {
        this.loginmobile = loginmobile ;
    }

    public  byte[] getMark() {
        return mark;
    }

    public void setMark(byte[] mark) {
        this.mark = mark;
    }

    public String getLoginGoodTtnum() {
        return loginGoodTtnum;
    }

    public void setLoginGoodTtnum(String loginGoodTtnum) {
        this.loginGoodTtnum = loginGoodTtnum ;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

	public Integer getLoginGoodTtnumType() {
		return loginGoodTtnumType;
	}

	public void setLoginGoodTtnumType(Integer loginGoodTtnumType) {
		this.loginGoodTtnumType = loginGoodTtnumType;
	}
}