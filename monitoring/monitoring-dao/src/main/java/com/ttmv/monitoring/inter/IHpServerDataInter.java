package com.ttmv.monitoring.inter;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.HpServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

/**
 * HP服务器的数据
 * @author wll
 *
 */
public interface IHpServerDataInter {

	/**
	 * 添加HpServerData
	 * @param HpServerData
	 * @return
	 */
	public Integer addHpServerData(HpServerData hpServerData)throws Exception;
	
	/**
	 * 根据ID查询HpServerData
	 * @param id
	 * @return
	 */
	public HpServerData queryHpServerData(BigInteger id)throws Exception;

	/**
	 * 根据ip,servertype,port查询HpServerData
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public HpServerData queryHpServerDataByIpAndServerTypeAndPort(DataBeanQuery hpServerDataQuery) throws Exception;
	
	/**
	 * 查询HpServerData列表
	 * @param HpServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<HpServerData> querySelectedHpServerData(DataBeanQuery hpServerDataQuery)throws Exception;
	
	/**
	 * 查询预置数据HpServerData列表,根据时间段
	 * @param HpServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<HpServerData> queryListOnDateHpServerDataByIpAndServerTypeAndPort(DataBeanQuery hpServerDataQuery)throws Exception;
}
