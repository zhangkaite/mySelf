package com.ttmv.monitoring.webService.impl.configuration.ipWhitelist;

import java.math.BigInteger;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.WhiteList;
import com.ttmv.monitoring.entity.tmp.AlerterInfoTmp;
import com.ttmv.monitoring.filter.FiltratorAgent;
import com.ttmv.monitoring.inter.IWhiteListInter;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.webService.WebServerInf;
import com.ttmv.monitoring.webService.tools.ResponseTool;


/**
 * @author Damon_Zs
 * @version 创建时间：2015年10月15日15:23:36
 * @explain : 修改IP白名单
 */
@SuppressWarnings({ "rawtypes","unused"})
public class ModifyWhitelistServiceImpl implements WebServerInf{
	private static Logger logger = LogManager.getLogger(ModifyWhitelistServiceImpl.class);
	private IWhiteListInter iWhiteListInter ;
	protected FiltratorAgent dataSourceIPFilter;
	
	public Map handler(Map reqMap) {
		logger.info("[ModifyWhitelistServiceImpl] @ start...");
		//请求数据校验
		try {
			checkData(reqMap);
		} catch (Exception e) {
			logger.error("数据校验失败!!!" + e.getMessage());
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
		}
		WhiteList  whiteList = this.creatWhiteList(reqMap);
		try {
			iWhiteListInter.updateWhiteList(whiteList);
			logger.debug("IP白名单信息修改操作成功!!!");
		} catch (Exception e) {
			logger.error("IP白名单信息修改失败!!!" , e);
			return ResponseTool.returnError();
		}
		
		dataSourceIPFilter.updateWhiteValue();
		
		return ResponseTool.returnSuccess();
	}
	
	/**
	 * 创建修改对象
	 * 
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	private WhiteList creatWhiteList(Map reqMap) {
		// 创建修改对象
		WhiteList whiteList = new WhiteList();
		whiteList.setId(new BigInteger(reqMap.get("whiteID").toString()));
		
		if(reqMap.get("startIP") != null){
			whiteList.setStartIp(reqMap.get("startIP").toString());
		}
		if(reqMap.get("type") != null){
			whiteList.setType(Integer.parseInt(reqMap.get("type").toString()));
		}
		if(reqMap.get("endIP") != null){
			whiteList.setEndIp(reqMap.get("endIP").toString());
		}
		return whiteList;
	}
	

	/**
	 * 业务数据校验(非空，数据规格)
	 * @param reqMap
	 * @return void
	 * @exception Exception
	 */
	public void checkData(Map reqMap) throws Exception {
		if(reqMap.get("whiteID") == null || "".equals(reqMap.get("whiteID"))){
			throw new Exception("[ModifyWhitelist_[whiteID] is null...]");
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
