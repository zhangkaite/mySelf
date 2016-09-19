package com.ttmv.monitoring.interfaceService.impl.info;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.TranscodingData;
import com.ttmv.monitoring.inter.ITranscodingDataInter;
import com.ttmv.monitoring.interfaceService.InterServerInf;


/**
 * @author Damon_Zs
 * @version 创建时间：2015年10月21日17:10:20
 * @explain : 转码服务器监控数据上报
 */
@SuppressWarnings({ "rawtypes", "unused","unchecked" })
public class TranscodingServiceDataReportImpl extends InterServerInf{
	
	private static Logger logger = LogManager.getLogger(TranscodingServiceDataReportImpl.class);
	
	private ITranscodingDataInter iTranscodingDataInter;

	@Override
	public int handler(Object obj) throws Exception {
		// TODO Auto-generated method stub
		return iTranscodingDataInter.addTranscodingData((TranscodingData)obj);
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
		ls.add("Rooms");
		ls.add("CPU");
		ls.add("Disk");
		ls.add("MEM");
		for (int i = 0; i < ls.size(); i++) {
			if (reqMap.get(ls.get(i)) == null) {
				throw new Exception("[TranscodingServiceDataReport_["
						+ ls.get(i) + "] is null...]");
			}
		}
		/**
		 * 组对象
		 */
		TranscodingData transcodingData = new TranscodingData();
		transcodingData.setServerType(reqMap.get("ServerType").toString());// 服务器类型
		transcodingData.setServerId(reqMap.get("serverId").toString());// 服务器ID
		transcodingData.setIp(reqMap.get("IP").toString());// IP
		transcodingData.setPort(Integer.parseInt(reqMap.get("Port").toString()));// 端口
		transcodingData.setCpu(Integer.parseInt(reqMap.get("CPU").toString()));// CPU占用百分比
		transcodingData.setDisk(Integer.parseInt(reqMap.get("Disk").toString()));// 硬盘空间占用，单位GB
		transcodingData.setMem(Integer.parseInt(reqMap.get("MEM").toString()));// 内存占用，单位MB
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		transcodingData.setTimestamp(sdf.parse(reqMap.get("Timestamp").toString()));// 采样时间
		
		transcodingData.setRooms(reqMap.get("Rooms").toString());
		return transcodingData;
	}

	public ITranscodingDataInter getiTranscodingDataInter() {
		return iTranscodingDataInter;
	}

	public void setiTranscodingDataInter(ITranscodingDataInter iTranscodingDataInter) {
		this.iTranscodingDataInter = iTranscodingDataInter;
	}

	 
}
