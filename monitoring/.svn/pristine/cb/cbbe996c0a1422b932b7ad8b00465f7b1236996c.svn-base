package com.ttmv.monitoring.inter;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.PHPServerData;
import com.ttmv.monitoring.entity.PHPServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

/**
 * PHP服务器的数据
 * @author wll
 *
 */
public interface IPHPServerDataInter {

	/**
	 * 添加PHPServerData
	 * @param PHPServerData
	 * @return
	 */
	public Integer addPHPServerData(PHPServerData pHPServerData)throws Exception;
	
	/**
	 * 根据ID查询PHPServerData
	 * @param id
	 * @return
	 */
	public PHPServerData queryPHPServerData(BigInteger id)throws Exception;

	/**
	 * 根据ip,servertype,port查询PHPServerData
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public PHPServerData queryPHPServerDataByIpAndServerTypeAndPort(DataBeanQuery pHPServerDataQuery) throws Exception;
	
	/**
	 * 查询PHPServerData列表
	 * @param pHPServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<PHPServerData> querySelectedPHPServerData(DataBeanQuery pHPServerDataQuery)throws Exception;
	
	/**
	 * 查询预置数据PHPServerData列表
	 * @param pHPServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<PHPServerData> queryListPHPServerDataByIpAndServerTypeAndPort(DataBeanQuery pHPServerDataQuery)throws Exception;
	
	/**
	 * 查询预置数据PHPServerData列表,根据时间段
	 * @param PHPServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<PHPServerData> queryListOnDatePHPServerDataByIpAndServerTypeAndPort(DataBeanQuery pHPServerDataQuery)throws Exception;
}
