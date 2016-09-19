package com.ttmv.monitoring.inter;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.RmsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

/**
 * rms服务器的数据
 * @author wll
 *
 */
public interface IRmsServerDataInter {

	/**
	 * 添加RmsServerData
	 * @param RmsServerData
	 * @return
	 */
	public Integer addRmsServerData(RmsServerData rmsServerData)throws Exception;
	
	/**
	 * 根据ID查询RmsServerData
	 * @param id
	 * @return
	 */
	public RmsServerData queryRmsServerData(BigInteger id)throws Exception;

	/**
	 * 根据ip,servertype,port查询RmsServerData
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public RmsServerData queryRmsServerDataByIpAndServerTypeAndPort(DataBeanQuery rmsServerDataQuery) throws Exception;
	
	/**
	 * 查询RmsServerData列表
	 * @param RmsServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<RmsServerData> querySelectedRmsServerData(DataBeanQuery rmsServerDataQuery)throws Exception;
	
	/**
	 * 查询预置数据RmsServerData列表,根据时间段
	 * @param RmsServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<RmsServerData> queryListOnDateRmsServerDataByIpAndServerTypeAndPort(DataBeanQuery rmsServerDataQuery)throws Exception;
}
