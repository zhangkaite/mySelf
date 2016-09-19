package com.ttmv.monitoring.entity;

import java.math.BigInteger;
import java.util.Date;

public class MediaControlData {
	
    private BigInteger id;
    private String serverType;
    private String serverId;
    private String ip;
    private Integer port;
    private Date timestamp;
    private Integer createdRoomCount;//
    private String mediaTransmissionServers;
    private Integer inputMessages;//
    private Integer outputMessages;//
    private Integer cpu;//
    private Integer disk;//
    private Integer mem;//
    private Date createTime;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

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

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getCreatedRoomCount() {
        return createdRoomCount;
    }

    public void setCreatedRoomCount(Integer createdRoomCount) {
        this.createdRoomCount = createdRoomCount;
    }

    public String getMediaTransmissionServers() {
        return mediaTransmissionServers;
    }

    public void setMediaTransmissionServers(String mediaTransmissionServers) {
        this.mediaTransmissionServers = mediaTransmissionServers;
    }

    public Integer getInputMessages() {
        return inputMessages;
    }

    public void setInputMessages(Integer inputMessages) {
        this.inputMessages = inputMessages;
    }

    public Integer getOutputMessages() {
        return outputMessages;
    }

    public void setOutputMessages(Integer outputMessages) {
        this.outputMessages = outputMessages;
    }

    public Integer getCpu() {
        return cpu;
    }

    public void setCpu(Integer cpu) {
        this.cpu = cpu;
    }

    public Integer getDisk() {
        return disk;
    }

    public void setDisk(Integer disk) {
        this.disk = disk;
    }

    public Integer getMem() {
        return mem;
    }

    public void setMem(Integer mem) {
        this.mem = mem;
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}