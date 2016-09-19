package com.ttmv.monitoring.webService.impl.configuration.threshold;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.ThresholdInfo;
import com.ttmv.monitoring.inter.IThresholdInfoInter;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.webService.WebServerInf;
import com.ttmv.monitoring.webService.entity.QueryThresholdBean;
import com.ttmv.monitoring.webService.entity.ThresholdBean;
import com.ttmv.monitoring.webService.tools.ResponseTool;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月23日20:26:49
 * @explain : 根据系统类型查询系统阀值
 */
@SuppressWarnings({ "rawtypes"})
public class QueryThresholdByTypeServiceImpl implements WebServerInf{
	
	private static Logger logger = LogManager.getLogger(QueryThresholdByTypeServiceImpl.class);

	private IThresholdInfoInter iThresholdInfoInter;
	
	/**
	 * 业务处理
	 * @param reqMap
	 * @return Map
	 */
	public Map handler(Map reqMap) {
		logger.info("[QueryThresholdByTypeServiceImpl] @ start...");
		//请求数据校验
		try {
			checkData(reqMap);
		} catch (Exception e) {
			logger.error("数据校验失败!!!" + e.getMessage());
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
		}
		String type = reqMap.get("threshold_type").toString();
		List<ThresholdInfo> thresholdInfos = null;
		try {
			thresholdInfos = iThresholdInfoInter.queryThresholdInfoByType(type);
		} catch (Exception e) {
			logger.error("查询阀值失败!!!");
			return ResponseTool.returnError();
		}
		//拼接返回数据
		Map resMp = this.takeResData(thresholdInfos);
		return ResponseTool.returnSuccess(resMp);
	}
	
	/**
	 * 组装返回数据
	 * @param reqMap
	 * @return void
	 * @exception Exception
	 */
	private Map takeResData(List<ThresholdInfo> thresholdInfos){
		Map resData = new HashMap();
		if(thresholdInfos.size()>0){			
			resData.put("threshold_alerter_id", thresholdInfos.get(0).getThresholdAlerterIds());
		}
		//QueryThresholdBean thresholdBean = new QueryThresholdBean();
		List<ThresholdBean> beans = new ArrayList<ThresholdBean>();
		for(int i=0;i<thresholdInfos.size();i++){
			ThresholdBean bean = new ThresholdBean();
			bean.setKey(thresholdInfos.get(i).getThresholdName());//key
			bean.setValue(thresholdInfos.get(i).getThresholdValue());//value
			bean.setName(thresholdInfos.get(i).getThresholdShowName());//中文
			beans.add(bean);
			//thresholdBean.setList(beans);
		}
		resData.put("thresholds", beans);
		return resData;
	}
	
	/**
	 * 业务数据校验(非空，数据规格)
	 * @param reqMap
	 * @return void
	 * @exception Exception
	 */
	public void checkData(Map reqMap) throws Exception {
		if(reqMap.get("threshold_type") == null || "".equals(reqMap.get("threshold_type"))){
			throw new Exception("[QueryThresholdByType_[threshold_type] is null...]");
		}
		
	}

	public IThresholdInfoInter getiThresholdInfoInter() {
		return iThresholdInfoInter;
	}

	public void setiThresholdInfoInter(IThresholdInfoInter iThresholdInfoInter) {
		this.iThresholdInfoInter = iThresholdInfoInter;
	}

	
	
	
	
}
