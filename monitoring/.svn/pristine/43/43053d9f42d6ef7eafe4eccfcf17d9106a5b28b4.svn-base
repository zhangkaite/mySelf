package com.ttmv.monitoring.webService.impl.configuration.ipWhitelist;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.UserInfo;
import com.ttmv.monitoring.entity.WhiteList;
import com.ttmv.monitoring.entity.page.Page;
import com.ttmv.monitoring.entity.querybean.WhiteListQuery;
import com.ttmv.monitoring.inter.IWhiteListInter;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.webService.WebServerInf;
import com.ttmv.monitoring.webService.tools.ResponseTool;


/**
 * @author Damon_Zs
 * @version 创建时间：2015年10月15日15:02:27
 * @explain : 查询IP白名单列表
 */
@SuppressWarnings({ "rawtypes","unused","unchecked"})
public class QueryWhitelistServiceImpl implements WebServerInf{
	private static Logger logger = LogManager.getLogger(QueryWhitelistServiceImpl.class);
	private IWhiteListInter iWhiteListInter ;
	
	public Map handler(Map reqMap) {
		logger.info("[QueryWhitelistServiceImpl] @ start...");
		//请求数据校验
		try {
			checkData(reqMap);
		} catch (Exception e) {
			logger.error("数据校验失败!!!" + e.getMessage());
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
		}
		//创建查询对象
		WhiteListQuery whiteListQuery = new WhiteListQuery();
		whiteListQuery.setPage(Integer.parseInt(reqMap.get("page").toString()));
		whiteListQuery.setPageSize(Integer.parseInt(reqMap.get("pageSize").toString()));
		//查询
		Page pages = null;
		try {
			pages = iWhiteListInter.queryPageWhiteList(whiteListQuery);
		} catch (Exception e) {
			logger.error("报警器信息查询处理异常!!!" , e);
			return ResponseTool.returnError();
		}
		logger.info("[白名单列表查询成功!!!]");
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
		resData.put("dataSum", pages.getSum());//总条数
		List<WhiteList> whiteList = pages.getData();
		List whitelist = new ArrayList();
		for(int i = 0; i < whiteList.size();i++){
			Map map = new HashMap();
			map.put("whiteID", whiteList.get(i).getId());
			map.put("startIP", whiteList.get(i).getStartIp());
			map.put("endIP", whiteList.get(i).getEndIp());
			map.put("type", whiteList.get(i).getType());
			whitelist.add(map);
		}
		resData.put("whiteList", whitelist);
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
			throw new Exception("[QueryWhitelist_[page] is null...]");
		}
		if(reqMap.get("pageSize") == null || "".equals(reqMap.get("pageSize").toString())){
			throw new Exception("[QueryWhitelist_[pageSize] is null...]");
		}
	}


	public IWhiteListInter getiWhiteListInter() {
		return iWhiteListInter;
	}


	public void setiWhiteListInter(IWhiteListInter iWhiteListInter) {
		this.iWhiteListInter = iWhiteListInter;
	}





	
	

}
