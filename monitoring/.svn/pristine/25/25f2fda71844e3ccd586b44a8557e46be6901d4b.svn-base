package com.ttmv.monitoring.inter;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.ScreenShotData;
import com.ttmv.monitoring.entity.ScreenShotData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

/**
 * 视频截图服务器数据
 * @author wll
 *
 */
public interface IScreenShotDataInter {

	/**
	 * 添加ScreenShotData
	 * @param ScreenShotData
	 * @return
	 */
	public Integer addScreenShotData(ScreenShotData screenShotData)throws Exception;
	
	/**
	 * 根据ID查询ScreenShotData
	 * @param id
	 * @return
	 */
	public ScreenShotData queryScreenShotData(BigInteger id)throws Exception;
	
	/**
	 * 条件查询
	 * @param transcodingDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<ScreenShotData> querySelectedScreenShotData(DataBeanQuery screenShotDataQuery) throws Exception;
	
	/**
	 * 根据IP,type,port 查询
	 * @param transcodingData
	 * @return
	 * @throws Exception
	 */
	public ScreenShotData queryScreenShotDataByIpAndServerTypeAndPort(DataBeanQuery screenShotDataQuery) throws Exception;
	
	/**
	 * 查询预置数据ScreenShotData列表
	 * @param transcodingDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<ScreenShotData> queryListScreenShotDataByIpAndServerTypeAndPort(DataBeanQuery screenShotDataQuery) throws Exception;
	
	/**
	 * 查询预置数据ScreenShotData列表,根据时间段
	 * @param ScreenShotDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<ScreenShotData> queryListOnDateScreenShotDataByIpAndServerTypeAndPort(DataBeanQuery screenShotDataQuery)throws Exception;
}
