package com.ttmv.monitoring.inter;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.PrsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

/**
 * prs服务器的数据
 * @author wll
 *
 */
public interface IPrsServerDataInter {

	/**
	 * 添加PrsServerData
	 * @param PrsServerData
	 * @return
	 */
	public Integer addPrsServerData(PrsServerData prsServerData)throws Exception;
	
	/**
	 * 根据ID查询PrsServerData
	 * @param id
	 * @return
	 */
	public PrsServerData queryPrsServerData(BigInteger id)throws Exception;

	/**
	 * 根据ip,servertype,port查询PrsServerData
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public PrsServerData queryPrsServerDataByIpAndServerTypeAndPort(DataBeanQuery prsServerDataQuery) throws Exception;
	
	/**
	 * 查询PrsServerData列表
	 * @param PrsServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<PrsServerData> querySelectedPrsServerData(DataBeanQuery prsServerDataQuery)throws Exception;
	
	/**
	 * 查询预置数据PrsServerData列表,根据时间段
	 * @param PrsServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<PrsServerData> queryListOnDatePrsServerDataByIpAndServerTypeAndPort(DataBeanQuery prsServerDataQuery)throws Exception;
}
