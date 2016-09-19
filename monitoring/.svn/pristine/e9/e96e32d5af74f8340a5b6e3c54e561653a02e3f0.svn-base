package com.ttmv.monitoring.interfaceService.impl.info;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.RmsServerData;
import com.ttmv.monitoring.entity.TasServerData;
import com.ttmv.monitoring.inter.ITasServerDataInter;
import com.ttmv.monitoring.interfaceService.InterServerInf;
import com.ttmv.monitoring.webService.impl.user.AddUserServiceImpl;


/**
 * @author Damon_Zs
 * @version 创建时间：2015年10月21日17:13:01
 * @explain : IM-Mds 服务器监控数据上报
 */
@SuppressWarnings({ "rawtypes", "unused" ,"unchecked" })
public class ImShowTasServiceDataReportImpl extends InterServerInf{
	
	private static Logger logger = LogManager.getLogger(ImShowTasServiceDataReportImpl.class);
	private ITasServerDataInter iTasServerDataInter;

	@Override
	public int handler(Object obj) throws Exception {
		// TODO Auto-generated method stub
		return iTasServerDataInter.addTasServerData((TasServerData)obj);
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
		ls.add("ClientConntionCount");
		ls.add("CPU");
		ls.add("Disk");
		ls.add("MEM");
		for (int i = 0; i < ls.size(); i++) {
			if (reqMap.get(ls.get(i)) == null) {
				throw new Exception("[ImShowTasServiceDataReport_["
						+ ls.get(i) + "] is null...]");
			}
		}
		/**
		 * 组对象
		 */
		TasServerData tasServerData = new TasServerData();
		tasServerData.setServerType(reqMap.get("ServerType").toString());// 服务器类型
		tasServerData.setServerId(reqMap.get("serverId").toString());// 服务器ID
		tasServerData.setIp(reqMap.get("IP").toString());// IP
		tasServerData.setPort(Integer.parseInt(reqMap.get("Port").toString()));// 端口
		tasServerData.setCpu(Integer.parseInt(reqMap.get("CPU").toString()));// CPU占用百分比
		tasServerData.setDisk(Integer.parseInt(reqMap.get("Disk").toString()));// 硬盘空间占用，单位GB
		tasServerData.setMem(Integer.parseInt(reqMap.get("MEM").toString()));// 内存占用，单位MB
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		tasServerData.setTimestamp(sdf.parse(reqMap.get("Timestamp").toString()));// 采样时间
		
		tasServerData.setWorkThread(Integer.parseInt(reqMap.get("WorkThread").toString()));
		tasServerData.setRunTime(Integer.parseInt(reqMap.get("RunTime").toString()));
		tasServerData.setClientConnectionSum(Integer.parseInt(reqMap.get("ClientConntionCount").toString()));
		return tasServerData;
	}

	public ITasServerDataInter getiTasServerDataInter() {
		return iTasServerDataInter;
	}

	public void setiTasServerDataInter(ITasServerDataInter iTasServerDataInter) {
		this.iTasServerDataInter = iTasServerDataInter;
	}
	
	

}
