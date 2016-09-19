package com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.usercrossrelation;

import java.math.BigInteger;
import java.util.Date;

public class MysqlUserCrossRelation {
    private BigInteger id;

    private BigInteger userIdA;

    private BigInteger userIdB;

    private BigInteger groupId;

    private Date created;

    private Date updated;

    private Integer type;

    private String remarkName;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getUserIdA() {
        return userIdA;
    }

    public void setUserIdA(BigInteger userIdA) {
        this.userIdA = userIdA;
    }

    public BigInteger getUserIdB() {
        return userIdB;
    }

    public void setUserIdB(BigInteger userIdB) {
        this.userIdB = userIdB;
    }

    public BigInteger getGroupId() {
        return groupId;
    }

    public void setGroupId(BigInteger groupId) {
        this.groupId = groupId;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName == null ? null : remarkName.trim();
    }
}