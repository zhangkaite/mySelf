package com.ttmv.monitoring.imp;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.ServerSubSysinfo;
import com.ttmv.monitoring.inter.IServerSubSysinfoInter;
import com.ttmv.monitoring.mapper.ServerSubSysinfoDaoMapper;

public class IServerSubSysinfoInterImpl implements IServerSubSysinfoInter{

	private ServerSubSysinfoDaoMapper serverSubSysinfoDaoMapper;
	
	public Integer addServerSubSysinfo(ServerSubSysinfo serverSubSysinfo)
			throws Exception {
		Integer result = serverSubSysinfoDaoMapper.addServerSubSysinfo(serverSubSysinfo);
		return result;
	}

	public ServerSubSysinfo queryServerSubSysinfo(BigInteger id)throws Exception {
		ServerSubSysinfo result = serverSubSysinfoDaoMapper.queryServerSubSysinfo(id);
		return result;
	}

	public List<ServerSubSysinfo> queryServerSubSysinfoBySysType(String sysType)throws Exception {
		List<ServerSubSysinfo> result = serverSubSysinfoDaoMapper.queryServerSubSysinfoBySysType(sysType);
		return result;
	}

	public ServerSubSysinfoDaoMapper getServerSubSysinfoDaoMapper() {
		return serverSubSysinfoDaoMapper;
	}

	public void setServerSubSysinfoDaoMapper(
			ServerSubSysinfoDaoMapper serverSubSysinfoDaoMapper) {
		this.serverSubSysinfoDaoMapper = serverSubSysinfoDaoMapper;
	}
}
