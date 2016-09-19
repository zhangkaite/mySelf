package com.ttmv.monitoring.chartService.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.ServerSubSysinfo;
import com.ttmv.monitoring.inter.IServerSubSysinfoInter;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.webService.tools.ResponseTool;

public class QueryServerSubSysinfoServiceImpl {

	private static Logger logger = LogManager.getLogger(QueryServerSubSysinfoServiceImpl.class);
	private IServerSubSysinfoInter iServerSubSysinfoInter;
	
	public Map handler(Map reqMap) {
		logger.info("[QueryServerSubSysinfoServiceImpl] @ start...");
		//请求数据校验
		try {
			checkData(reqMap);
		} catch (Exception e) {
			logger.error("数据校验失败!!!" + e.getMessage());
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
		}
		//创建请求对象
		String data = null;
		try {			
			data = this.createQueryServerSubSysinfo(reqMap);
		} catch (Exception e) {
			logger.error("创建String查询对象出现异常!!!",e);
			return ResponseTool.returnError();
		}
		//查询数据
		List<ServerSubSysinfo> result = null; 
		try {
			result = iServerSubSysinfoInter.queryServerSubSysinfoBySysType(data);
		} catch (Exception e) {
			logger.error("查询ServerSubSysinfoServiceImpl对象出现异常!!!",e);
			return ResponseTool.returnError();
		}
		List<HashMap> resultList  = this.takeResData(result);
		return ResponseTool.returnSuccess(resultList);
	}

	/**
	 * 处理结果
	 * @param result
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<HashMap> takeResData(List<ServerSubSysinfo> result) {
		if(result != null){
			List<HashMap> list  = new ArrayList<HashMap>();
			for(int i=0;i<result.size();i++){
				ServerSubSysinfo info = result.get(i);
				HashMap map = new HashMap();
				map.put("value", info.getSysKey());
				map.put("text", info.getSysName());
				list.add(map);
			}
			return list;
		}
		return null;
	}

	/**
	 *检查参数是否为空
	 */
	public void checkData(Map reqMap) throws Exception {
		if(reqMap.get("sys_type") == null || "".equals(reqMap.get("sys_type"))){//server类型验证
			throw new Exception("[QueryServerSubSysinfoServiceImpl_[sys_type] is null...]");
		}
	}

	/**
	 * 组装查询对象
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	private String createQueryServerSubSysinfo(Map reqMap)throws Exception{
		String sysType = reqMap.get("sys_type").toString();
		return sysType;
	}

	public IServerSubSysinfoInter getiServerSubSysinfoInter() {
		return iServerSubSysinfoInter;
	}

	public void setiServerSubSysinfoInter(
			IServerSubSysinfoInter iServerSubSysinfoInter) {
		this.iServerSubSysinfoInter = iServerSubSysinfoInter;
	}
}
