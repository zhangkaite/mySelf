package com.ttmv.monitoring.webService.impl.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.collection.entity.CpuInfo;
import com.ttmv.monitoring.collection.interf.ICpuInfo;
import com.ttmv.monitoring.entity.page.Page;
import com.ttmv.monitoring.webService.WebServerInf;
import com.ttmv.monitoring.webService.tools.ResponseTool;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月16日10:45:29
 * @explain : W005_CPU列表信息查询（支持条件模糊查询）
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryCpuInfoServiceImpl  implements WebServerInf{

	private static Logger logger = LogManager.getLogger(QueryCpuInfoServiceImpl.class);
	
	private ICpuInfo cpuInfoImpl;

	/**
	 * CPU列表查询（支持模糊查询）
	 * @param reqMap
	 * @return resMap
	 */
	public Map handler(Map reqMap) {
		logger.info("[QueryCpuInfoServiceImpl] @ start...");
		CpuInfo cpuInfo = new CpuInfo();
		cpuInfo.setPage(Integer.parseInt(reqMap.get("page").toString()));
		cpuInfo.setPageSize(Integer.parseInt(reqMap.get("pageSize").toString()));
		//创建查询对象
		Page pages = null;
		try {
			pages = cpuInfoImpl.queryPageCpuInfo(cpuInfo);
		} catch (Exception e) {
			logger.error("CPU信息查询处理异常!!!" , e);
			return ResponseTool.returnError();
		}
		logger.info("[CPU列表查询成功!!!]");
		return ResponseTool.returnSuccess(takeResData(pages));
	}
	
	/**
	 * 组装返回数据
	 * @param pages
	 * @param userInfo
	 * @return 
	 */
	private Map takeResData(Page pages){
		Map resData = new HashMap();
		resData.put("dataSum", pages.getSum());//总条数
		List<CpuInfo> cpuInfo = pages.getData();
		logger.info("查询记录数：" +  cpuInfo.size());
		
		List cpuList = new ArrayList();
		for(int i = 0; i < cpuInfo.size();i++){
			Map map = new HashMap();
			map.put("ip", cpuInfo.get(i).getIp());
			map.put("mac", cpuInfo.get(i).getMac());
			map.put("cpuNo", cpuInfo.get(i).getCpuNo());
			map.put("cpuMhz", cpuInfo.get(i).getCpuMhz());
			map.put("cpuVendor", cpuInfo.get(i).getCpuVendor());
			map.put("cpuModel", cpuInfo.get(i).getCpuModel());
			map.put("cpuUser", cpuInfo.get(i).getCpuUser());
			map.put("cpuSys", cpuInfo.get(i).getCpuSys());
			map.put("cpuWait", cpuInfo.get(i).getCpuWait());
			map.put("cpuNice", cpuInfo.get(i).getCpuNice());
			map.put("cpuIdle", cpuInfo.get(i).getCpuIdle());
			map.put("cpuTotal", cpuInfo.get(i).getCpuTotal());
			cpuList.add(map);
		}
		resData.put("cpuList", cpuList);//CPUlist
		return resData;
	}
	

	/**
	 * 业务数据校验(非空，数据规格)
	 * @param reqMap
	 * @return void
	 * @exception Exception
	 */
	public void checkData(Map reqMap) throws Exception {
		if(reqMap.get("page") == null || "".equals(reqMap.get("page").toString())){
			throw new Exception("[QueryUsersServiceImpl_[page] is null...]");
		}
		if(reqMap.get("pageSize") == null || "".equals(reqMap.get("pageSize").toString())){
			throw new Exception("[QueryUsersServiceImpl_[pageSize] is null...]");
		}
	}

	public ICpuInfo getCpuInfoImpl() {
		return cpuInfoImpl;
	}

	public void setCpuInfoImpl(ICpuInfo cpuInfoImpl) {
		this.cpuInfoImpl = cpuInfoImpl;
	}

	

	
	
	
	
}
