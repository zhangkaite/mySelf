package com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.group;

import java.math.BigInteger;
import java.util.Date;

public class MysqlGroup {
  
	private BigInteger id;
    private BigInteger userId;
    private String name;
    private Integer defaultType;
    private Integer gorder;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name ;
    }

    public Integer getDefaultType() {
        return defaultType;
    }

    public void setDefaultType(Integer defaultType) {
        this.defaultType = defaultType;
    }

    public Integer getGorder() {
        return gorder;
    }

    public void setGorder(Integer gorder) {
        this.gorder = gorder;
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