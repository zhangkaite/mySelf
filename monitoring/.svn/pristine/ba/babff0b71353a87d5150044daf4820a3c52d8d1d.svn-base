package com.ttmv.monitoring.interfaceService.impl.info;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.PrsServerData;
import com.ttmv.monitoring.entity.RmsServerData;
import com.ttmv.monitoring.inter.IRmsServerDataInter;
import com.ttmv.monitoring.interfaceService.InterServerInf;
import com.ttmv.monitoring.webService.impl.user.AddUserServiceImpl;


/**
 * @author Damon_Zs
 * @version 创建时间：2015年10月21日17:13:01
 * @explain : IM-Mds 服务器监控数据上报
 */
@SuppressWarnings({ "rawtypes", "unused" ,"unchecked"})
public class ImShowRmsServiceDataReportImpl extends InterServerInf{
	
	private static Logger logger = LogManager.getLogger(ImShowRmsServiceDataReportImpl.class);
	private IRmsServerDataInter iRmsServerDataInter;

	@Override
	public int handler(Object obj) throws Exception {
		return iRmsServerDataInter.addRmsServerData((RmsServerData)obj);
	}

	@Override
	protected Object getDataObject(Map reqMap) throws Exception {
		// 校验传入数据是否符合接口文档（能多不能少）
		List ls = new ArrayList();
		ls.add("ServerType");
		ls.add("serverId");
		ls.add("IP");
		ls.add("Port");
		ls.add("Timestamp");
		ls.add("WorkThread");
		ls.add("RunTime");
		ls.add("InputCmds");
		ls.add("OutputCmds");
		ls.add("CPU");
		ls.add("Disk");
		ls.add("MEM");
		for (int i = 0; i < ls.size(); i++) {
			if (reqMap.get(ls.get(i)) == null) {
				throw new Exception("[ImShowRmsServiceDataReport_["
						+ ls.get(i) + "] is null...]");
			}
		}
		/**
		 * 组对象
		 */
		RmsServerData rmsServerData = new RmsServerData();
		rmsServerData.setServerType(reqMap.get("ServerType").toString());// 服务器类型
		rmsServerData.setServerId(reqMap.get("serverId").toString());// 服务器ID
		rmsServerData.setIp(reqMap.get("IP").toString());// IP
		rmsServerData.setPort(Integer.parseInt(reqMap.get("Port").toString()));// 端口
		rmsServerData.setCpu(Integer.parseInt(reqMap.get("CPU").toString()));// CPU占用百分比
		rmsServerData.setDisk(Integer.parseInt(reqMap.get("Disk").toString()));// 硬盘空间占用，单位GB
		rmsServerData.setMem(Integer.parseInt(reqMap.get("MEM").toString()));// 内存占用，单位MB
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		rmsServerData.setTimestamp(sdf.parse(reqMap.get("Timestamp").toString()));// 采样时间
		
		rmsServerData.setWorkThread(Integer.parseInt(reqMap.get("WorkThread").toString()));
		rmsServerData.setRunTime(Integer.parseInt(reqMap.get("RunTime").toString()));
		rmsServerData.setInputCmds(Integer.parseInt(reqMap.get("InputCmds").toString()));
		rmsServerData.setOutputCmds(Integer.parseInt(reqMap.get("OutputCmds").toString()));
		return rmsServerData;
	}

	public IRmsServerDataInter getiRmsServerDataInter() {
		return iRmsServerDataInter;
	}

	public void setiRmsServerDataInter(IRmsServerDataInter iRmsServerDataInter) {
		this.iRmsServerDataInter = iRmsServerDataInter;
	}
	
	

}
