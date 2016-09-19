package com.ttmv.monitoring.inter;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.PhpManagerServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

/**
 * PHP服务器的数据
 * @author wll
 *
 */
public interface IPHPManagerServerDataInter {

	/**
	 * 添加PhpManagerServerData
	 * @param PhpManagerServerData
	 * @return
	 */
	public Integer addPhpManagerServerData(PhpManagerServerData phpManagerServerData)throws Exception;
	
	/**
	 * 根据ID查询PhpManagerServerData
	 * @param id
	 * @return
	 */
	public PhpManagerServerData queryPhpManagerServerData(BigInteger id)throws Exception;

	/**
	 * 根据ip,servertype,port查询PhpManagerServerData
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public PhpManagerServerData queryPhpManagerServerDataByIpAndServerTypeAndPort(DataBeanQuery phpManagerServerDataQuery) throws Exception;
	
	/**
	 * 查询PhpManagerServerData列表
	 * @param PhpManagerServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<PhpManagerServerData> querySelectedPhpManagerServerData(DataBeanQuery phpManagerServerDataQuery)throws Exception;
	
	/**
	 * 查询预置数据PhpManagerServerData列表,根据时间段
	 * @param PhpManagerServerDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<PhpManagerServerData> queryListOnDatePhpManagerServerDataByIpAndServerTypeAndPort(DataBeanQuery phpManagerServerDataQuery)throws Exception;
}
