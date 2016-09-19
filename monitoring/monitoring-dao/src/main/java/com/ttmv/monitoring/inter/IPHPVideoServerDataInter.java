package com.ttmv.monitoring.inter;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.PhpVideoServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

/**
 * PHP服务器的数据
 * @author wll
 *
 */
public interface IPHPVideoServerDataInter {

	/**
	 * 添加PhpVideoServerData
	 * @param PhpVideoServerData
	 * @return
	 */
	public Integer addPhpVideoServerData(PhpVideoServerData phpVideoServerData)throws Exception;
	
	/**
	 * 根据ID查询PhpVideoServerData
	 * @param id
	 * @return
	 */
	public PhpVideoServerData queryPhpVideoServerData(BigInteger id)throws Exception;

	/**
	 * 根据ip,servertype,port查询PhpVideoServerData
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public PhpVideoServerData queryPhpVideoServerDataByIpAndServerTypeAndPort(DataBeanQuery phpVideoServerDataQuery) throws Exception;
	
	/**
	 * 查询PhpVideoServerData列表
	 * @param PhpVideoServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<PhpVideoServerData> querySelectedPhpVideoServerData(DataBeanQuery phpVideoServerDataQuery)throws Exception;
	
	/**
	 * 查询预置数据PhpVideoServerData列表,根据时间段
	 * @param PhpVideoServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<PhpVideoServerData> queryListOnDatePhpVideoServerDataByIpAndServerTypeAndPort(DataBeanQuery phpVideoServerDataQuery)throws Exception;
}
