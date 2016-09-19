package com.ttmv.monitoring.inter;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.TranscodingData;
import com.ttmv.monitoring.entity.TranscodingData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

/**
 * 转码服务器数据
 * @author wll
 *
 */
public interface ITranscodingDataInter {

	/**
	 * 添加TranscodingData
	 * @param TranscodingData
	 * @return
	 */
	public Integer addTranscodingData(TranscodingData TranscodingData)throws Exception;
	
	/**
	 * 根据ID查询TranscodingData
	 * @param id
	 * @return
	 */
	public TranscodingData queryTranscodingData(BigInteger id)throws Exception;
	
	/**
	 * 条件查询
	 * @param transcodingDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<TranscodingData> querySelectedTranscodingData(DataBeanQuery transcodingDataQuery) throws Exception;
	
	/**
	 * 根据IP,type,port 查询
	 * @param transcodingData
	 * @return
	 * @throws Exception
	 */
	public TranscodingData queryTranscodingDataByIpAndServerTypeAndPort(DataBeanQuery transcodingDataQuery) throws Exception;
	
	/**
	 * 查询预置数据TranscodingData列表
	 * @param transcodingDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<TranscodingData> queryListTranscodingDataByIpAndServerTypeAndPort(DataBeanQuery transcodingDataQuery) throws Exception;
	
	/**
	 * 查询预置数据TranscodingData列表,根据时间段
	 * @param TranscodingDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<TranscodingData> queryListOnDateTranscodingDataByIpAndServerTypeAndPort(DataBeanQuery transcodingDataQuery)throws Exception;
}
