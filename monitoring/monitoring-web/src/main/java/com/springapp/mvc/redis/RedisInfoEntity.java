package com.springapp.mvc.redis;

public class RedisInfoEntity {

	/**
	 * 主机
	 */
	private String hostName;
	/**
	 * redis队列名称
	 */
	private String keyName;
	/**
	 * redis key类型
	 */
	private String keyType;
	/**
	 * redis key对应的values长度
	 */
	private String valueLength;

	/**
	 * 以人类可读的格式返回 Redis 分配的内存总量
	 */

	private String used_memory;
	/**
	 * 以人类可读的格式返回 Redis 的内存消耗峰值
	 */
	private String used_memory_peak;

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String getValueLength() {
		return valueLength;
	}

	public void setValueLength(String valueLength) {
		this.valueLength = valueLength;
	}

	public String getUsed_memory() {
		return used_memory;
	}

	public void setUsed_memory(String used_memory) {
		this.used_memory = used_memory;
	}

	public String getUsed_memory_peak() {
		return used_memory_peak;
	}

	public void setUsed_memory_peak(String used_memory_peak) {
		this.used_memory_peak = used_memory_peak;
	}

}
