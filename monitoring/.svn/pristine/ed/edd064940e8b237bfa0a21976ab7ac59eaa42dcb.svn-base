package com.ttmv.monitoring.interfaceService.impl.info;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.alerterService.bean.AlerterLogMessage;
import com.ttmv.monitoring.entity.MediaForwardData;
import com.ttmv.monitoring.entity.UserInfo;
import com.ttmv.monitoring.inter.IMediaForwardDataInter;
import com.ttmv.monitoring.interfaceService.InterServerInf;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.tools.constant.SmsConstant;
import com.ttmv.monitoring.webService.entity.MonitoringInitBean;
import com.ttmv.monitoring.webService.tools.ResponseTool;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月26日12:21:21
 * @explain : 流媒体转发服务器（Stream Transmission Server）控数据上报
 */
@SuppressWarnings({ "rawtypes", "unused","unchecked"})
public class AvServerTransmitServiceDataReportImpl extends InterServerInf{
	
	private static Logger logger = LogManager.getLogger(AvServerTransmitServiceDataReportImpl.class);
	
	private IMediaForwardDataInter iMediaForwardDataInter;
	

	/**
	 * 流媒体转发服务器数据上报逻辑处理
	 * @param reqMap
	 * @return resMap
	 * @throws Exception 
	 */
	public int handler(Object obj) throws Exception {
		return iMediaForwardDataInter.addMediaForwardData((MediaForwardData)obj);
	}
	
	/**
	 * 组装数据
	 */
	protected Object getDataObject(Map reqMap) throws Exception {
		//校验传入数据是否符合接口文档（能多不能少）
		List ls = new ArrayList();
		ls.add("ServerType");
		ls.add("serverId");
		ls.add("IP");
		ls.add("Port");
		ls.add("Timestamp");
//		ls.add("WriteBufferLength");
//		ls.add("ReadBufferLength");
		ls.add("UdxConnectionLength");
//		ls.add("ReadPacketCount");
//		ls.add("WritePacketCount");
		ls.add("RoomCount");
		ls.add("CPU");
		ls.add("Disk");
		ls.add("MEM");
		for(int i=0 ; i<ls.size() ;i++){
			if(reqMap.get(ls.get(i))==null){
				throw new Exception("[AvServerTransmitServiceDataReport_["+ls.get(i)+"] is null...]");
			}
		}
		/**
		 * 组对象
		 */
		MediaForwardData mediaForwardData = new MediaForwardData();
		mediaForwardData.setServerType(reqMap.get("ServerType").toString());//服务器类型
		mediaForwardData.setServerId(reqMap.get("serverId").toString());//服务器ID
		mediaForwardData.setIp(reqMap.get("IP").toString());//IP
		mediaForwardData.setPort(Integer.parseInt(reqMap.get("Port").toString()));//端口
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                
		mediaForwardData.setTimestamp(sdf.parse(reqMap.get("Timestamp").toString()));//采样时间
//		mediaForwardData.setWriteBufferLength(Integer.parseInt(reqMap.get("WriteBufferLength").toString()));//写缓冲区列表长度
//		mediaForwardData.setReadBufferLength(Integer.parseInt(reqMap.get("ReadBufferLength").toString()));//当前待处理的读事件列表长度
		mediaForwardData.setUdxConnectionLength(Integer.parseInt(reqMap.get("UdxConnectionLength").toString()));//当前的连接列表长度
//		mediaForwardData.setReadPacketCount(Integer.parseInt(reqMap.get("ReadPacketCount").toString()));//已读数据包数量
//		mediaForwardData.setWritePacketCount(Integer.parseInt(reqMap.get("WritePacketCount").toString()));//已发数据包数量
		mediaForwardData.setRoomCount(Integer.parseInt(reqMap.get("RoomCount").toString()));//房间数量
		mediaForwardData.setCpu(Integer.parseInt(reqMap.get("CPU").toString()));//CPU占用百分比
		mediaForwardData.setDisk(Integer.parseInt(reqMap.get("Disk").toString()));//硬盘空间占用，单位GB
		mediaForwardData.setMem(Integer.parseInt(reqMap.get("MEM").toString()));//内存占用，单位MB
		return mediaForwardData;
	}
	
	
	


	public IMediaForwardDataInter getiMediaForwardDataInter() {
		return iMediaForwardDataInter;
	}

	public void setiMediaForwardDataInter(
			IMediaForwardDataInter iMediaForwardDataInter) {
		this.iMediaForwardDataInter = iMediaForwardDataInter;
	}




	

}
