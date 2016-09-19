package com.ttmv.monitoring.webService;

import java.util.Map;


/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月16日17:11:57
 * @explain :web服务接口处理模板
 */
@SuppressWarnings("rawtypes")
public interface  WebServerInf{
	
	/**
	 * web服务业务处理接口
	 * @param reqMap
	 * @return Map
	 */
	public Map handler(Map reqMap);
	
	/**
	 * 业务数据校验(非空，数据规格)
	 * @param reqMap
	 * @return void
	 * @exception Exception
	 */
	public abstract void checkData(Map reqMap) throws Exception;
	
}
