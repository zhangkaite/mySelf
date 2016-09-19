package com.ttmv.monitoring.imDataList;

public class ImServiceDataEntity {
	private String id;
	private String server_type;
	private String server_id;
	private String ip;
	private String port;
	private String work_thread_sum;
	private String run_time;
	private String cpu;
	private String disk;
	private String mem;
	private String client_connection_sum;
	private String create_time;
	private String group_count;
	private String input_cmds;
	private String output_cmds;

	public String getGroup_count() {
		return group_count;
	}

	public void setGroup_count(String group_count) {
		this.group_count = group_count;
	}

	public String getInput_cmds() {
		return input_cmds;
	}

	public void setInput_cmds(String input_cmds) {
		this.input_cmds = input_cmds;
	}

	public String getOutput_cmds() {
		return output_cmds;
	}

	public void setOutput_cmds(String output_cmds) {
		this.output_cmds = output_cmds;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getClient_connection_sum() {
		return client_connection_sum;
	}

	public void setClient_connection_sum(String client_connection_sum) {
		this.client_connection_sum = client_connection_sum;
	}

	// private String
	public String getServer_type() {
		return server_type;
	}

	public void setServer_type(String server_type) {
		this.server_type = server_type;
	}

	public String getServer_id() {
		return server_id;
	}

	public void setServer_id(String server_id) {
		this.server_id = server_id;
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

	public String getWork_thread_sum() {
		return work_thread_sum;
	}

	public void setWork_thread_sum(String work_thread_sum) {
		this.work_thread_sum = work_thread_sum;
	}

	public String getRun_time() {
		return run_time;
	}

	public void setRun_time(String run_time) {
		this.run_time = run_time;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getDisk() {
		return disk;
	}

	public void setDisk(String disk) {
		this.disk = disk;
	}

	public String getMem() {
		return mem;
	}

	public void setMem(String mem) {
		this.mem = mem;
	}
}
