package com.ttmv.monitoring.inter;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.MssServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

/**
 * Mss服务器的数据
 * @author wll
 *
 */
public interface IMssServerDataInter {

	/**
	 * 添加MssServerData
	 * @param MssServerData
	 * @return
	 */
	public Integer addMssServerData(MssServerData mssServerData)throws Exception;
	
	/**
	 * 根据ID查询MssServerData
	 * @param id
	 * @return
	 */
	public MssServerData queryMssServerData(BigInteger id)throws Exception;

	/**
	 * 根据ip,servertype,port查询MssServerData
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public MssServerData queryMssServerDataByIpAndServerTypeAndPort(DataBeanQuery mssServerDataQuery) throws Exception;
	
	/**
	 * 查询MssServerData列表
	 * @param MssServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<MssServerData> querySelectedMssServerData(DataBeanQuery mssServerDataQuery)throws Exception;
	
	/**
	 * 查询预置数据MssServerData列表,根据时间段
	 * @param MssServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<MssServerData> queryListOnDateMssServerDataByIpAndServerTypeAndPort(DataBeanQuery mssServerDataQuery)throws Exception;
}
