package com.ttmv.monitoring.alerterService.bean;

/**
 * Created by zbs on 15/9/25.
 */
public class AlerterLogMessage {
    public String serverType;//服务器类型
    public String serverId;//服务器ID
    public String ip;//ip
    public String port;//端口

    //报警的类型 cpu 还是 mam
    public String thresholdName;
    //触动的报警的阀值
    public String thresholdValue;
    //真实的值
    public String actualValue;

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getThresholdName() {
        return thresholdName;
    }

    public void setThresholdName(String thresholdName) {
        this.thresholdName = thresholdName;
    }

    public String getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(String thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    public String getActualValue() {
        return actualValue;
    }

    public void setActualValue(String actualValue) {
        this.actualValue = actualValue;
    }
}
