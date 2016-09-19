package com.ttmv.monitoring.webService.impl.configuration.alerter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.AlerterInfo;
import com.ttmv.monitoring.entity.UserInfo;
import com.ttmv.monitoring.entity.page.Page;
import com.ttmv.monitoring.entity.querybean.AlerterInfoQuery;
import com.ttmv.monitoring.entity.tmp.AlerterInfoTmp;
import com.ttmv.monitoring.inter.IAlerterInfoInter;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.webService.WebServerInf;
import com.ttmv.monitoring.webService.tools.ResponseTool;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月23日11:46:21
 * @explain : W011_报警器列表查询
 */
@SuppressWarnings({ "rawtypes","unused","unchecked"})
public class QueryAlertersServiceImpl implements WebServerInf{
	private static Logger logger = LogManager.getLogger(QueryAlerterInfoByIdServiceImpl.class);
	private IAlerterInfoInter iAlerterInfoInter;
	
	/** 
	 * 报警器务逻辑处理
	 * @param reqMap
	 * @return resMap
	 */
	public Map handler(Map reqMap) {
		logger.info("[QueryAlertersServiceImpl] @ start...");
		//请求数据验证
		try {
			checkData(reqMap);
		} catch (Exception e) {
			logger.error("数据校验失败!!!" + e.getMessage());
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
		}
		//创建查询对象
		AlerterInfoQuery alerterInfoQuery = new AlerterInfoQuery();
		alerterInfoQuery.setPage(Integer.parseInt(reqMap.get("page").toString()));
		alerterInfoQuery.setPageSize(Integer.parseInt(reqMap.get("pageSize").toString()));
		if(reqMap.get("alerterName") != null){
			alerterInfoQuery.setAlerterName(reqMap.get("alerterName").toString());
		}
		//查询
		Page pages = null;
		try {
			pages = iAlerterInfoInter.queryPageAlerterInfo(alerterInfoQuery);
		} catch (Exception e) {
			logger.error("报警器信息查询处理异常!!!" , e);
			return ResponseTool.returnError();
		}
		logger.info("[用户列表查询成功!!!]");
		return ResponseTool.returnSuccess(takeResData(pages));
		
	}
	
	/**
	 * 组装返回数据
	 * @param pages
	 * @param 
	 * @return 
	 */
	private Map takeResData(Page pages) {
			Map resData = new HashMap();
			if(pages != null){
				resData.put("dataSum", pages.getSum());//总条数
				List<AlerterInfoTmp> alerterInfos = pages.getData();
				logger.info("查询记录数：" +  alerterInfos.size());
				List alerters = new ArrayList();
				for(int i = 0; i < alerterInfos.size();i++){
					Map map = new HashMap();
					map.put("alerterID", alerterInfos.get(i).getId());
					map.put("alerterName", alerterInfos.get(i).getAlerterName());
					alerters.add(map);
				}
				resData.put("alerters", alerters);//用户list
				return resData;
			}else{
				return null;
			}
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

	public IAlerterInfoInter getiAlerterInfoInter() {
		return iAlerterInfoInter;
	}

	public void setiAlerterInfoInter(IAlerterInfoInter iAlerterInfoInter) {
		this.iAlerterInfoInter = iAlerterInfoInter;
	}
	
	
	
	
}
