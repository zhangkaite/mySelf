package com.ttmv.monitoring.webService.impl.configuration.ipWhitelist;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.WhiteList;
import com.ttmv.monitoring.entity.tmp.AlerterInfoTmp;
import com.ttmv.monitoring.inter.IWhiteListInter;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.webService.WebServerInf;
import com.ttmv.monitoring.webService.tools.ResponseTool;


/**
 * @author Damon_Zs
 * @version 创建时间：2015年10月15日15:38:11
 * @explain : 查询IP白名单详情
 */
@SuppressWarnings({ "rawtypes","unused","unchecked"})
public class QueryWhitelistByIdServiceImpl implements WebServerInf{
	private static Logger logger = LogManager.getLogger(QueryWhitelistByIdServiceImpl.class);
	private IWhiteListInter iWhiteListInter ;
	
	public Map handler(Map reqMap) {
		logger.info("[QueryWhitelistByIdServiceImpl] @ start...");
		//请求数据校验
		try {
			checkData(reqMap);
		} catch (Exception e) {
			logger.error("数据校验失败!!!" + e.getMessage());
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
		}
		WhiteList whiteList = null;
		try {
			iWhiteListInter.queryWhiteList(new BigInteger(reqMap.get("WhiteID").toString()));
		} catch (Exception e) {
			logger.error("IP白名单信息查询处理异常!!!" , e);
			return ResponseTool.returnError();
		}
		if(whiteList == null){
			logger.warn("whiteID查询白名单不存在!!!");
			return ResponseTool.returnSuccess(null);
		}
		//装返回结果resMap
		Map resData = this.takeResData(whiteList);
		return ResponseTool.returnSuccess(resData);
	}
	
	/**
	 * 组装返回数据
	 * @param resData
	 * @param alerterInfo
	 * @return 
	 */
	private Map takeResData(WhiteList whiteList){
		Map resData = new HashMap();
		resData.put("whiteID", whiteList.getId());
		resData.put("startIP", whiteList.getStartIp());
		resData.put("endIP", whiteList.getEndIp());
		resData.put("type", whiteList.getType());
		return resData;
	}
	

	/**
	 * 业务数据校验(非空，数据规格)
	 * @param reqMap
	 * @return void
	 * @exception Exception
	 */
	public void checkData(Map reqMap) throws Exception {
		if(reqMap.get("WhiteID") == null || "".equals(reqMap.get("WhiteID"))){
			throw new Exception("[QueryWhitelistById_[WhiteID] is null...]");
		}
	}

	public IWhiteListInter getiWhiteListInter() {
		return iWhiteListInter;
	}

	public void setiWhiteListInter(IWhiteListInter iWhiteListInter) {
		this.iWhiteListInter = iWhiteListInter;
	}




	
	

}
