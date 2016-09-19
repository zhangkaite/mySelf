package com.ttmv.dao.bean;

import java.math.BigInteger;

/**
 * 金色弹窗信息
 * @author wulinli
 *
 */
public class Cmp {
   
	private BigInteger id;
    private BigInteger userId;
    private String type;
    private String tag;
    private String warntime;
    private String remark;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getWarntime() {
        return warntime;
    }

    public void setWarntime(String warntime) {
        this.warntime = warntime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}