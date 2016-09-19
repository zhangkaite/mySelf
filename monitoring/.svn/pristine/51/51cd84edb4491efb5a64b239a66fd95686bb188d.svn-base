package com.ttmv.monitoring.inter;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.MdsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

/**
 * Mds服务器的数据
 * @author wll
 *
 */
public interface IMdsServerDataInter {

	/**
	 * 添加MdsServerData
	 * @param MdsServerData
	 * @return
	 */
	public Integer addMdsServerData(MdsServerData mdsServerData)throws Exception;
	
	/**
	 * 根据ID查询MdsServerData
	 * @param id
	 * @return
	 */
	public MdsServerData queryMdsServerData(BigInteger id)throws Exception;

	/**
	 * 根据ip,servertype,port查询MdsServerData
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public MdsServerData queryMdsServerDataByIpAndServerTypeAndPort(DataBeanQuery mdsServerDataQuery) throws Exception;
	
	/**
	 * 查询MdsServerData列表
	 * @param MdsServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<MdsServerData> querySelectedMdsServerData(DataBeanQuery mdsServerDataQuery)throws Exception;
	
	/**
	 * 查询预置数据MdsServerData列表,根据时间段
	 * @param MdsServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<MdsServerData> queryListOnDateMdsServerDataByIpAndServerTypeAndPort(DataBeanQuery mdsServerDataQuery)throws Exception;
}
