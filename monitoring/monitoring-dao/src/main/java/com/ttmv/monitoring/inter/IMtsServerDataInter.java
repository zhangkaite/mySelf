package com.ttmv.monitoring.inter;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.MtsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

/**
 * Mts服务器的数据
 * @author wll
 *
 */
public interface IMtsServerDataInter {

	/**
	 * 添加MtsServerData
	 * @param MtsServerData
	 * @return
	 */
	public Integer addMtsServerData(MtsServerData mtsServerData)throws Exception;
	
	/**
	 * 根据ID查询MtsServerData
	 * @param id
	 * @return
	 */
	public MtsServerData queryMtsServerData(BigInteger id)throws Exception;

	/**
	 * 根据ip,servertype,port查询MtsServerData
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public MtsServerData queryMtsServerDataByIpAndServerTypeAndPort(DataBeanQuery mtsServerDataQuery) throws Exception;
	
	/**
	 * 查询MtsServerData列表
	 * @param MtsServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<MtsServerData> querySelectedMtsServerData(DataBeanQuery mtsServerDataQuery)throws Exception;
	
	/**
	 * 查询预置数据MtsServerData列表,根据时间段
	 * @param MtsServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<MtsServerData> queryListOnDateMtsServerDataByIpAndServerTypeAndPort(DataBeanQuery mtsServerDataQuery)throws Exception;
}
