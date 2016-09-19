package com.ttmv.monitoring.inter;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.MediaControlData;
import com.ttmv.monitoring.entity.MediaControlData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

/**
 * 媒体转发服务器数据 接口
 * @author wll
 */
public interface IMediaControlDataInter {

	/**
	 * 添加MediaControlData
	 * @param MediaControlData
	 * @return
	 */
	public Integer addMediaControlData(MediaControlData mediaControlData)throws Exception;
	
	/**
	 * 根据ID查询MediaControlData
	 * @param id
	 * @return
	 */
	public MediaControlData queryMediaControlData(BigInteger id)throws Exception;
	
	/**
	 * 查询MediaControlData根据Ip，Servertype，port
	 * @param id
	 * @return
	 */
	public MediaControlData queryMediaControlDataByIpAndServerTypeAndPort(DataBeanQuery mediaControlDataQuery)throws Exception;
	
	/**
	 * 级联菜单的查询
	 * @param MediaControlDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<MediaControlData> querySelectedMediaControlData(DataBeanQuery mediaControlDataQuery)throws Exception;
	
	/**
	 * 查询预置数据MediaControlData列表
	 * @param MediaControlDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<MediaControlData> queryListMediaControlDataByIpAndServerTypeAndPort(DataBeanQuery mediaControlDataQuery)throws Exception;
	
	/**
	 * 查询预置数据MediaControlData列表,根据时间段
	 * @param mediaControlDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<MediaControlData> queryListOnDateMediaControlDataByIpAndServerTypeAndPort(DataBeanQuery mediaControlDataQuery)throws Exception;
}
