package com.ttmv.monitoring.interfaceService.impl.info;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.HpServerData;
import com.ttmv.monitoring.inter.IHpServerDataInter;
import com.ttmv.monitoring.interfaceService.InterServerInf;


/**
 * @author Damon_Zs
 * @version 创建时间：2015年10月22日11:44:38
 * @explain : ImShowHttpProxy 服务器监控数据上报
 */
@SuppressWarnings({ "rawtypes", "unused" ,"unchecked"})
public class ImShowHttpProxyServiceDataReportImpl extends InterServerInf{
	
	private static Logger logger = LogManager.getLogger(ImShowHttpProxyServiceDataReportImpl.class);
	
	private IHpServerDataInter iHpServerDataInter;

	public int handler(Object obj) throws Exception {
		return iHpServerDataInter.addHpServerData((HpServerData)obj);
	}

	protected Object getDataObject(Map reqMap) throws Exception {
		//校验传入数据是否符合接口文档（能多不能少）
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
		for(int i=0 ; i<ls.size() ;i++){
			if(reqMap.get(ls.get(i))==null){
				throw new Exception("[ImShowHttpProxyServiceDataReport_["+ls.get(i)+"] is null...]");
			}
		}
		/**
		 * 组对象
		 */
		HpServerData hpServerData = new HpServerData();
		hpServerData.setServerType(reqMap.get("ServerType").toString());//服务器类型
		hpServerData.setServerId(reqMap.get("serverId").toString());//服务器ID
		hpServerData.setIp(reqMap.get("IP").toString());//IP
		hpServerData.setPort(Integer.parseInt(reqMap.get("Port").toString()));//端口
		hpServerData.setCpu(Integer.parseInt(reqMap.get("CPU").toString()));//CPU占用百分比
		hpServerData.setDisk(Integer.parseInt(reqMap.get("Disk").toString()));//硬盘空间占用，单位GB
		hpServerData.setMem(Integer.parseInt(reqMap.get("MEM").toString()));//内存占用，单位MB
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                
		hpServerData.setTimestamp(sdf.parse(reqMap.get("Timestamp").toString()));//采样时间
		
		hpServerData.setWorkThread(Integer.parseInt(reqMap.get("WorkThread").toString()));
		hpServerData.setRunTime(Integer.parseInt(reqMap.get("RunTime").toString()));
		return hpServerData;
	}
	
	
	

	public IHpServerDataInter getiHpServerDataInter() {
		return iHpServerDataInter;
	}

	public void setiHpServerDataInter(IHpServerDataInter iHpServerDataInter) {
		this.iHpServerDataInter = iHpServerDataInter;
	}
	
	


}
