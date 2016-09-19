package com.ttmv.monitoring.inter;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.ServerSubSysinfo;

/**
 * 数据 接口
 * 
 * @author wll
 */
public interface IServerSubSysinfoInter {

	/**
	 * 添加ServerSubSysinfo
	 * 
	 * @param ServerSubSysinfo
	 * @return
	 */
	public Integer addServerSubSysinfo(ServerSubSysinfo ServerSubSysinfo)throws Exception;

	/**
	 * 根据ID查询ServerSubSysinfo
	 * 
	 * @param id
	 * @return
	 */
	public ServerSubSysinfo queryServerSubSysinfo(BigInteger id)throws Exception;


	/**
	 * 根据SysType查询ServerSubSysinfo
	 * 
	 * @param ServerSubSysinfo
	 * @return
	 * @throws Exception
	 */
	public List<ServerSubSysinfo> queryServerSubSysinfoBySysType(String  sysType) throws Exception;
}
