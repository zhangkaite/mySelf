package com.ttmv.monitoring.interfaceService.impl.info;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.PhpVideoServerData;
import com.ttmv.monitoring.inter.IPHPVideoServerDataInter;
import com.ttmv.monitoring.interfaceService.InterServerInf;


/**
 * @author Damon_Zs
 * @version 创建时间：2015年10月21日17:13:01
 * @explain : PHP点播服务器监控数据上报
 */
@SuppressWarnings({ "rawtypes", "unused","unchecked" })
public class PhpVideoServiceDataReportImpl extends InterServerInf{
	
	private static Logger logger = LogManager.getLogger(PhpVideoServiceDataReportImpl.class);
	
	private IPHPVideoServerDataInter iphpVideoServerDataInter;

	@Override
	public int handler(Object obj) throws Exception {
		// TODO Auto-generated method stub
		return iphpVideoServerDataInter.addPhpVideoServerData((PhpVideoServerData)obj);
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
		ls.add("CPU");
		ls.add("Disk");
		ls.add("MEM");
		
		ls.add("SysLoad");
		ls.add("NetConnections");
		ls.add("NetLoad");
		
		for (int i = 0; i < ls.size(); i++) {
			if (reqMap.get(ls.get(i)) == null) {
				throw new Exception("[PhpVideoServiceDataReport_["
						+ ls.get(i) + "] is null...]");
			}
		}
		/**
		 * 组对象
		 */
		PhpVideoServerData phpVideoServerData = new PhpVideoServerData();
		phpVideoServerData.setServerType(reqMap.get("ServerType").toString());// 服务器类型
		phpVideoServerData.setServerId(reqMap.get("serverId").toString());// 服务器ID
		phpVideoServerData.setIp(reqMap.get("IP").toString());// IP
		phpVideoServerData.setPort(Integer.parseInt(reqMap.get("Port").toString()));// 端口
		phpVideoServerData.setCpu(Integer.parseInt(reqMap.get("CPU").toString()));// CPU占用百分比
		phpVideoServerData.setDisk(Integer.parseInt(reqMap.get("Disk").toString()));// 硬盘空间占用，单位GB
		phpVideoServerData.setMem(Integer.parseInt(reqMap.get("MEM").toString()));// 内存占用，单位MB
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		phpVideoServerData.setTimestamp(sdf.parse(reqMap.get("Timestamp").toString()));// 采样时间
		
		phpVideoServerData.setSysLoad(Integer.parseInt(reqMap.get("SysLoad").toString()));
		phpVideoServerData.setNetConnections(Integer.parseInt(reqMap.get("NetConnections").toString()));
		phpVideoServerData.setNetLoad(Integer.parseInt(reqMap.get("NetLoad").toString()));
		
		return phpVideoServerData;
	}

	public IPHPVideoServerDataInter getIphpVideoServerDataInter() {
		return iphpVideoServerDataInter;
	}

	public void setIphpVideoServerDataInter(
			IPHPVideoServerDataInter iphpVideoServerDataInter) {
		this.iphpVideoServerDataInter = iphpVideoServerDataInter;
	}
	
	

}
