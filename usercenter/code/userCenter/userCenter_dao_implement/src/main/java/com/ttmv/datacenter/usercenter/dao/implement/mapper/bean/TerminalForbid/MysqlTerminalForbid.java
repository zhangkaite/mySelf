package com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.TerminalForbid;

import java.math.BigInteger;
import java.util.Date;

public class MysqlTerminalForbid {
  
	private BigInteger id;
    private String ip;
    private String mac;
    private String disksn;
    private Date created;
    private Date updated;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip ;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDisksn() {
        return disksn;
    }

    public void setDisksn(String disksn) {
        this.disksn = disksn ;
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