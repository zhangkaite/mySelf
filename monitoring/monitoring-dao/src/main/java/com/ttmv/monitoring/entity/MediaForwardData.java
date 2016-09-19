package com.ttmv.monitoring.entity;

import java.math.BigInteger;
import java.util.Date;

public class MediaForwardData {
    
	private BigInteger id;								//ID自增
    private String serverType;						//服务器类型
    private String serverId;							//服务器编号
    private String ip;										//服务器IP
    private Integer port;								//服务端口
    private Date timestamp;							//采样时间
    private Integer udxConnectionLength;//	//当前的连接列表长度
    private Integer roomCount;//					//房间数量
    private Integer cpu;//									//cup使用情况
    private Integer disk;//								//硬盘使用情况
    private Integer mem;//								//内存使用情况
    private Date createTime;							//创建时间
    
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
        this.serverId = serverId ;
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

    public Integer getUdxConnectionLength() {
        return udxConnectionLength;
    }

    public void setUdxConnectionLength(Integer udxConnectionLength) {
        this.udxConnectionLength = udxConnectionLength;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
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