package com.ttmv.monitoring.inter;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.TasServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

/**
 * tas服务器的数据
 * @author wll
 *
 */
public interface ITasServerDataInter {

	/**
	 * 添加TasServerData
	 * @param TasServerData
	 * @return
	 */
	public Integer addTasServerData(TasServerData tasServerData)throws Exception;
	
	/**
	 * 根据ID查询TasServerData
	 * @param id
	 * @return
	 */
	public TasServerData queryTasServerData(BigInteger id)throws Exception;

	/**
	 * 根据ip,servertype,port查询TasServerData
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public TasServerData queryTasServerDataByIpAndServerTypeAndPort(DataBeanQuery tasServerDataQuery) throws Exception;
	
	/**
	 * 查询TasServerData列表
	 * @param TasServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<TasServerData> querySelectedTasServerData(DataBeanQuery tasServerDataQuery)throws Exception;
	
	/**
	 * 查询预置数据TasServerData列表,根据时间段
	 * @param TasServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<TasServerData> queryListOnDateTasServerDataByIpAndServerTypeAndPort(DataBeanQuery tasServerDataQuery)throws Exception;
}
