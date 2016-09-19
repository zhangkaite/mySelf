package com.ttmv.monitoring.interfaceService.impl.info;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.ScreenShotData;
import com.ttmv.monitoring.inter.IScreenShotDataInter;
import com.ttmv.monitoring.interfaceService.InterServerInf;


/**
 * @author Damon_Zs
 * @version 创建时间：2015年10月21日17:10:20
 * @explain : 截图服务器监控数据上报
 */
@SuppressWarnings({ "rawtypes", "unused","unchecked" })
public class ScreenshotServiceDataReportImpl extends InterServerInf{
	
	private static Logger logger = LogManager.getLogger(ScreenshotServiceDataReportImpl.class);
	private IScreenShotDataInter iScreenShotDataInter;

	@Override
	public int handler(Object obj) throws Exception {
		// TODO Auto-generated method stub
		return iScreenShotDataInter.addScreenShotData((ScreenShotData)obj);
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
				throw new Exception("[ScreenshotServiceDataReport_["
						+ ls.get(i) + "] is null...]");
			}
		}
		/**
		 * 组对象
		 */
		ScreenShotData screenShotData = new ScreenShotData();
		screenShotData.setServerType(reqMap.get("ServerType").toString());// 服务器类型
		screenShotData.setServerId(reqMap.get("serverId").toString());// 服务器ID
		screenShotData.setIp(reqMap.get("IP").toString());// IP
		screenShotData.setPort(Integer.parseInt(reqMap.get("Port").toString()));// 端口
		screenShotData.setCpu(Integer.parseInt(reqMap.get("CPU").toString()));// CPU占用百分比
		screenShotData.setDisk(Integer.parseInt(reqMap.get("Disk").toString()));// 硬盘空间占用，单位GB
		screenShotData.setMem(Integer.parseInt(reqMap.get("MEM").toString()));// 内存占用，单位MB
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		screenShotData.setTimestamp(sdf.parse(reqMap.get("Timestamp").toString()));// 采样时间
		
		screenShotData.setRooms(reqMap.get("Rooms").toString());
		return screenShotData;
	}

	public IScreenShotDataInter getiScreenShotDataInter() {
		return iScreenShotDataInter;
	}

	public void setiScreenShotDataInter(IScreenShotDataInter iScreenShotDataInter) {
		this.iScreenShotDataInter = iScreenShotDataInter;
	}
	
	

}
