package com.ttmv.monitoring.interfaceService.impl.info;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.MediaControlData;
import com.ttmv.monitoring.inter.IMediaControlDataInter;
import com.ttmv.monitoring.interfaceService.InterServerInf;
/**
 * @author Damon_Zs
 * @version 创建时间：2015年10月22日11:41:30
 * @explain : 流媒体控制服务器（Streaming media control services）监控数据入库
 */
@SuppressWarnings({ "rawtypes","unused","unchecked"})
public class AvServerControlServiceDataReportImpl extends InterServerInf{
	
	private static Logger logger = LogManager.getLogger(AvServerControlServiceDataReportImpl.class);
	
	private IMediaControlDataInter iMediaControlDataInter;

	/**
	 * 入库
	 */
	public int handler(Object obj) throws Exception {
		return iMediaControlDataInter.addMediaControlData((MediaControlData)obj);
	}

	/**
	 * 组装入库对象
	 */
	protected Object getDataObject(Map reqMap) throws Exception {
		//校验传入数据是否符合接口文档（能多不能少）
		List ls = new ArrayList();
		ls.add("ServerType");
		ls.add("serverId");
		ls.add("IP");
		ls.add("Port");
		ls.add("Timestamp");
		ls.add("CreatedRoomCount");
		ls.add("InputMessages");
		ls.add("OutputMessages");
		ls.add("CPU");
		ls.add("Disk");
		ls.add("MEM");
		ls.add("MediaServers");
		for(int i=0 ; i<ls.size() ;i++){
			if(reqMap.get(ls.get(i))==null){
				throw new Exception("[AvServerTransmitServiceDataReport_["+ls.get(i)+"] is null...]");
			}
		}
		/**
		 * 组对象
		 */
		MediaControlData mediaControlData = new MediaControlData();
		mediaControlData.setCpu(Integer.parseInt(reqMap.get("CPU").toString()));
		mediaControlData.setDisk(Integer.parseInt(reqMap.get("Disk").toString()));
		mediaControlData.setMem(Integer.parseInt(reqMap.get("MEM").toString()));
		mediaControlData.setServerType(reqMap.get("ServerType").toString());
		mediaControlData.setServerId(reqMap.get("serverId").toString());
		mediaControlData.setIp(reqMap.get("IP").toString());
		mediaControlData.setPort(Integer.parseInt(reqMap.get("Port").toString()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                
		mediaControlData.setTimestamp(sdf.parse(reqMap.get("Timestamp").toString()));//采样时间
		mediaControlData.setCreatedRoomCount(Integer.parseInt(reqMap.get("CreatedRoomCount").toString()));
		mediaControlData.setInputMessages(Integer.parseInt(reqMap.get("InputMessages").toString()));
		mediaControlData.setOutputMessages(Integer.parseInt(reqMap.get("OutputMessages").toString()));
		mediaControlData.setMediaTransmissionServers(reqMap.get("MediaServers").toString());
		return mediaControlData;
	}

	public IMediaControlDataInter getiMediaControlDataInter() {
		return iMediaControlDataInter;
	}

	public void setiMediaControlDataInter(
			IMediaControlDataInter iMediaControlDataInter) {
		this.iMediaControlDataInter = iMediaControlDataInter;
	}

}
