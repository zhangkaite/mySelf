package com.ttmv.monitoring.inter;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.UmsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

/**
 * ums服务器的数据
 * @author wll
 *
 */
public interface IUmsServerDataInter {

	/**
	 * 添加UmsServerData
	 * @param UmsServerData
	 * @return
	 */
	public Integer addUmsServerData(UmsServerData umsServerData)throws Exception;
	
	/**
	 * 根据ID查询UmsServerData
	 * @param id
	 * @return
	 */
	public UmsServerData queryUmsServerData(BigInteger id)throws Exception;

	/**
	 * 根据ip,servertype,port查询UmsServerData
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public UmsServerData queryUmsServerDataByIpAndServerTypeAndPort(DataBeanQuery umsServerDataQuery) throws Exception;
	
	/**
	 * 查询UmsServerData列表
	 * @param UmsServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<UmsServerData> querySelectedUmsServerData(DataBeanQuery umsServerDataQuery)throws Exception;
	
	/**
	 * 查询预置数据UmsServerData列表,根据时间段
	 * @param UmsServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<UmsServerData> queryListOnDateUmsServerDataByIpAndServerTypeAndPort(DataBeanQuery umsServerDataQuery)throws Exception;
}
