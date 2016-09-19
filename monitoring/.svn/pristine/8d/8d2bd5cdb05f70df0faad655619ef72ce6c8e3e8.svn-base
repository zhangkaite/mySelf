package com.ttmv.monitoring.webService.impl.configuration.ipWhitelist;

import java.math.BigInteger;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.filter.FiltratorAgent;
import com.ttmv.monitoring.inter.IWhiteListInter;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.webService.WebServerInf;
import com.ttmv.monitoring.webService.tools.ResponseTool;


/**
 * @author Damon_Zs
 * @version 创建时间：2015年10月15日15:02:27
 * @explain : 删除ip白名单
 */
@SuppressWarnings({ "rawtypes"})
public class DeleteWhitelistServiceImpl implements WebServerInf{
	private static Logger logger = LogManager.getLogger(DeleteWhitelistServiceImpl.class);
	private IWhiteListInter iWhiteListInter ;
	private FiltratorAgent dataSourceIPFilter;
	
	public Map handler(Map reqMap) {
		logger.info("[DeleteWhitelistServiceImpl] @ start...");
		//请求数据校验
		try {
			checkData(reqMap);
		} catch (Exception e) {
			logger.error("数据校验失败!!!" + e.getMessage());
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
		}
		try {
			iWhiteListInter.deleteWhiteList(new BigInteger(reqMap.get("whiteID").toString()));
		} catch (Exception e) {
			logger.error("ip白名单删除处理异常!!!",e);
			return ResponseTool.returnError();
		}
		//更新缓存中的白名单信息
		dataSourceIPFilter.updateWhiteValue();
		logger.info("[白名单删除成功!!!]");
		return ResponseTool.returnSuccess();
	}

	/**
	 * 业务数据校验(非空，数据规格)
	 * @param reqMap
	 * @return void
	 * @exception Exception
	 */
	public void checkData(Map reqMap) throws Exception {
		if(reqMap.get("whiteID") == null || "".equals(reqMap.get("whiteID"))){
			throw new Exception("[DeleteWhitelist_[whiteID] is null...]");
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
