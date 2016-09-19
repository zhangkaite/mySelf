package com.ttmv.monitoring.inter;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.LbsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

/**
 * Lbs服务器的数据
 * @author wll
 *
 */
public interface ILbsServerDataInter {

	/**
	 * 添加LbsServerData
	 * @param LbsServerData
	 * @return
	 */
	public Integer addLbsServerData(LbsServerData lbsServerData)throws Exception;
	
	/**
	 * 根据ID查询LbsServerData
	 * @param id
	 * @return
	 */
	public LbsServerData queryLbsServerData(BigInteger id)throws Exception;

	/**
	 * 根据ip,servertype,port查询LbsServerData
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public LbsServerData queryLbsServerDataByIpAndServerTypeAndPort(DataBeanQuery lbsServerDataQuery) throws Exception;
	
	/**
	 * 查询LbsServerData列表
	 * @param LbsServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<LbsServerData> querySelectedLbsServerData(DataBeanQuery lbsServerDataQuery)throws Exception;
	
	/**
	 * 查询预置数据LbsServerData列表,根据时间段
	 * @param LbsServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<LbsServerData> queryListOnDateLbsServerDataByIpAndServerTypeAndPort(DataBeanQuery lbsServerDataQuery)throws Exception;
}
