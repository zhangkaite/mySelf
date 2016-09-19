package com.ttmv.monitoring.interfaceService.impl.info;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.HpServerData;
import com.ttmv.monitoring.entity.LbsServerData;
import com.ttmv.monitoring.inter.ILbsServerDataInter;
import com.ttmv.monitoring.interfaceService.InterServerInf;
import com.ttmv.monitoring.webService.impl.user.AddUserServiceImpl;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年10月21日17:13:01
 * @explain : IM-LBS 服务器监控数据上报
 */
@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
public class ImShowLbsServiceDataReportImpl extends InterServerInf {

	private static Logger logger = LogManager
			.getLogger(ImShowLbsServiceDataReportImpl.class);
	private ILbsServerDataInter iLbsServerDataInter;

	public int handler(Object obj) throws Exception {
		return iLbsServerDataInter.addLbsServerData((LbsServerData) obj);
	}

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
		ls.add("CPU");
		ls.add("Disk");
		ls.add("MEM");
		for (int i = 0; i < ls.size(); i++) {
			if (reqMap.get(ls.get(i)) == null) {
				throw new Exception("[ImShowLbsServiceDataReport_["
						+ ls.get(i) + "] is null...]");
			}
		}
		/**
		 * 组对象
		 */
		LbsServerData lbsServerData = new LbsServerData();
		lbsServerData.setServerType(reqMap.get("ServerType").toString());// 服务器类型
		lbsServerData.setServerId(reqMap.get("serverId").toString());// 服务器ID
		lbsServerData.setIp(reqMap.get("IP").toString());// IP
		lbsServerData.setPort(Integer.parseInt(reqMap.get("Port").toString()));// 端口
		lbsServerData.setCpu(Integer.parseInt(reqMap.get("CPU").toString()));// CPU占用百分比
		lbsServerData.setDisk(Integer.parseInt(reqMap.get("Disk").toString()));// 硬盘空间占用，单位GB
		lbsServerData.setMem(Integer.parseInt(reqMap.get("MEM").toString()));// 内存占用，单位MB

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		lbsServerData.setTimestamp(sdf
				.parse(reqMap.get("Timestamp").toString()));// 采样时间

		lbsServerData.setWorkThread(Integer.parseInt(reqMap
				.get("WorkThread").toString()));
		lbsServerData.setRunTime(Integer.parseInt(reqMap.get("RunTime")
				.toString()));
		return lbsServerData;
	}

	public ILbsServerDataInter getiLbsServerDataInter() {
		return iLbsServerDataInter;
	}

	public void setiLbsServerDataInter(ILbsServerDataInter iLbsServerDataInter) {
		this.iLbsServerDataInter = iLbsServerDataInter;
	}

}
