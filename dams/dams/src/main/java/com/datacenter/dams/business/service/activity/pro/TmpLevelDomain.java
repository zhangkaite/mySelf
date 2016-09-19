package com.datacenter.dams.business.service.activity.pro;

import java.util.List;
import java.util.Map;

import com.datacenter.domain.live.activity.GiftRules;
import com.datacenter.domain.live.activity.Optionen;

/**
 * Created by zbs on 16/1/12.
 */
public class TmpLevelDomain {

    private int order;

    private Long levelid;

    private String levelname;

    private String mobilePhoto;

    private String pcPhoto;

    private List<Optionen> showoptionen;

    private List<GiftRules> showgifts;

    private Long activityid;

    private Double multiple;
    
    //2016年6月16日17:30:13  Damon 增加资源包
    private Map<String, String> activityPackage;

    public TmpLevelDomain(){}

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Long getLevelid() {
        return levelid;
    }

    public void setLevelid(Long levelid) {
        this.levelid = levelid;
    }

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
    }

    public String getMobilePhoto() {
        return mobilePhoto;
    }

    public void setMobilePhoto(String mobilePhoto) {
        this.mobilePhoto = mobilePhoto;
    }

    public String getPcPhoto() {
        return pcPhoto;
    }

    public void setPcPhoto(String pcPhoto) {
        this.pcPhoto = pcPhoto;
    }

    public List<Optionen> getShowoptionen() {
        return showoptionen;
    }

    public void setShowoptionen(List<Optionen> showoptionen) {
        this.showoptionen = showoptionen;
    }

    public List<GiftRules> getShowgifts() {
        return showgifts;
    }

    public void setShowgifts(List<GiftRules> showgifts) {
        this.showgifts = showgifts;
    }

    public Long getActivityid() {
        return activityid;
    }

    public void setActivityid(Long activityid) {
        this.activityid = activityid;
    }

    public Double getMultiple() {
        return multiple;
    }

    public void setMultiple(Double multiple) {
        this.multiple = multiple;
    }

	public Map<String, String> getActivityPackage() {
		return activityPackage;
	}

	public void setActivityPackage(Map<String, String> activityPackage) {
		this.activityPackage = activityPackage;
	}
}
