package com.ttmv.monitoring.interfaceService.impl.info;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.MssServerData;
import com.ttmv.monitoring.entity.MtsServerData;
import com.ttmv.monitoring.inter.IMtsServerDataInter;
import com.ttmv.monitoring.interfaceService.InterServerInf;
import com.ttmv.monitoring.webService.impl.user.AddUserServiceImpl;


/**
 * @author Damon_Zs
 * @version 创建时间：2015年10月21日17:13:01
 * @explain : IM-Mds 服务器监控数据上报
 */
@SuppressWarnings({ "rawtypes", "unused","unchecked" })
public class ImShowMtsServiceDataReportImpl extends InterServerInf{
	
	private static Logger logger = LogManager.getLogger(ImShowMtsServiceDataReportImpl.class);
	
	private IMtsServerDataInter iMtsServerDataInter;

	@Override
	public int handler(Object obj) throws Exception {
		// TODO Auto-generated method stub
		return iMtsServerDataInter.addMtsServerData((MtsServerData)obj);
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
		ls.add("ClientConnectionCount");
		ls.add("CPU");
		ls.add("Disk");
		ls.add("MEM");
		for (int i = 0; i < ls.size(); i++) {
			if (reqMap.get(ls.get(i)) == null) {
				throw new Exception("[ImShowMtsServiceDataReport_["
						+ ls.get(i) + "] is null...]");
			}
		}
		/**
		 * 组对象
		 */
		MtsServerData mtsServerData = new MtsServerData();
		mtsServerData.setServerType(reqMap.get("ServerType").toString());// 服务器类型
		mtsServerData.setServerId(reqMap.get("serverId").toString());// 服务器ID
		mtsServerData.setIp(reqMap.get("IP").toString());// IP
		mtsServerData.setPort(Integer.parseInt(reqMap.get("Port").toString()));// 端口
		mtsServerData.setCpu(Integer.parseInt(reqMap.get("CPU").toString()));// CPU占用百分比
		mtsServerData.setDisk(Integer.parseInt(reqMap.get("Disk").toString()));// 硬盘空间占用，单位GB
		mtsServerData.setMem(Integer.parseInt(reqMap.get("MEM").toString()));// 内存占用，单位MB
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mtsServerData.setTimestamp(sdf.parse(reqMap.get("Timestamp").toString()));// 采样时间
		
		mtsServerData.setWorkThread(Integer.parseInt(reqMap.get("WorkThread").toString()));
		mtsServerData.setRunTime(Integer.parseInt(reqMap.get("RunTime").toString()));
		mtsServerData.setClientConnectionSum(Integer.parseInt(reqMap.get("ClientConnectionCount").toString()));
		return mtsServerData;
	}

	public IMtsServerDataInter getiMtsServerDataInter() {
		return iMtsServerDataInter;
	}

	public void setiMtsServerDataInter(IMtsServerDataInter iMtsServerDataInter) {
		this.iMtsServerDataInter = iMtsServerDataInter;
	}
	
	

}
