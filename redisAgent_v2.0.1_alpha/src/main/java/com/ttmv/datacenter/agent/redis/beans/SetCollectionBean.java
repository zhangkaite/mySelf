package com.ttmv.datacenter.agent.redis.beans;

/**
 * Created by zbs on 15/11/13.
 */
public class SetCollectionBean {

    public String collectionName;
    public String key;
    public double value;

    public SetCollectionBean(String collectionName, String key, double value) {
        this.collectionName = collectionName;
        this.key = key;
        this.value = value;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
