package com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.useronlineinfo;

import java.math.BigInteger;
import java.util.Date;

public class MysqlUserOnlineInfo {
    
	private BigInteger id;
    private BigInteger userId;
    private String ip;
    private String mac;
    private String harddiskSn;
    private String token;
    private Date created;
    private Date updated;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac ;
    }

    public String getHarddiskSn() {
        return harddiskSn;
    }

    public void setHarddiskSn(String harddiskSn) {
        this.harddiskSn = harddiskSn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token ;
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
}