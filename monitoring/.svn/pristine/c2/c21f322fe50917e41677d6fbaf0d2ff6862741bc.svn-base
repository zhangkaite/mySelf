package com.ttmv.monitoring.webService.impl.configuration.ipWhitelist;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.WhiteList;
import com.ttmv.monitoring.filter.FiltratorAgent;
import com.ttmv.monitoring.inter.IWhiteListInter;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.tools.constant.SmsConstant;
import com.ttmv.monitoring.webService.WebServerInf;
import com.ttmv.monitoring.webService.tools.ResponseTool;


/**
 * @author Damon_Zs
 * @version 创建时间：2015年10月15日13:59:16
 * @explain : 添加ip过滤白名单
 */
@SuppressWarnings({ "rawtypes"})
public class AddWhitelistServiceImpl implements WebServerInf{
	private static Logger logger = LogManager.getLogger(AddWhitelistServiceImpl.class);
	private IWhiteListInter iWhiteListInter ;
	
	protected FiltratorAgent dataSourceIPFilter;
	
	public Map handler(Map reqMap) {
		logger.info("[AddWhitelistServiceImpl] @ start...");
		//请求数据校验
		try {
			checkData(reqMap);
		} catch (Exception e) {
			logger.error("数据校验失败!!!" + e.getMessage());
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
		}
		//创建添加对象
		WhiteList whiteList = null;
		try {
			whiteList = this.creatWhiteListInfo(reqMap);
		} catch (Exception e) {
			logger.error("创建IpAllowedData对象出现异常!!!",e);
			return ResponseTool.returnError();
		}
		//数据入库
		try {
			iWhiteListInter.addWhiteList(whiteList);
			
		} catch (Exception e) {
			logger.error("IP白名单添加处理异常!!!",e);
			return ResponseTool.returnError();
		}
		
		dataSourceIPFilter.addWhiteListValue(whiteList);
		
		return ResponseTool.returnSuccess();
		
	}
	
	
	/**
	 * 创建新增对象
	 * @param reqMap
	 * @return IpAllowedData
	 * @throws Exception
	 */
	private WhiteList creatWhiteListInfo(Map reqMap) throws Exception {
		WhiteList whiteList = new WhiteList();
		whiteList.setStartIp(reqMap.get("startIP").toString());
		whiteList.setEndIp(reqMap.get("endIP").toString());
		whiteList.setType(Integer.parseInt(reqMap.get("type").toString()));
		return whiteList;
		
	}

	/**
	 * 业务数据校验(非空，数据规格)
	 * @param reqMap
	 * @return void
	 * @exception Exception
	 */
	public void checkData(Map reqMap) throws Exception {
		
		if(reqMap.get("type") == null || "".equals(reqMap.get("type"))){
			throw new Exception("[AddWhitelist_[type] is null...]");
		}
		if(reqMap.get("startIP") == null || "".equals(reqMap.get("startIP"))){
			throw new Exception("[AddWhitelist_[startIP] is null...]");
		}
		if(reqMap.get("type").toString().equals(SmsConstant.WHITELIST_IP_TWO)){//类型为ip区间
			if(reqMap.get("endIP") == null || "".equals(reqMap.get("endIP"))){
				throw new Exception("[AddWhitelist_[endIP] is null...]");
			}
		}
		
		
	}

	public IWhiteListInter getiWhiteListInter() {
		return iWhiteListInter;
	}

	public void setiWhiteListInter(IWhiteListInter iWhiteListInter) {
		this.iWhiteListInter = iWhiteListInter;
	}


	public FiltratorAgent getDataSourceIPFilter() {
		return dataSourceIPFilter;
	}


	public void setDataSourceIPFilter(FiltratorAgent dataSourceIPFilter) {
		this.dataSourceIPFilter = dataSourceIPFilter;
	}
	
	




}
