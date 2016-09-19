package com.ttmv.monitoring.inter;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.MediaForwardData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

/**
 * 媒体转发服务器数据 接口
 * @author wll
 */
public interface IMediaForwardDataInter {

	/**
	 * 添加mediaForwardData
	 * @param mediaForwardData
	 * @return
	 */
	public Integer addMediaForwardData(MediaForwardData mediaForwardData)throws Exception;
	
	/**
	 * 根据ID查询mediaForwardData
	 * @param id
	 * @return
	 */
	public MediaForwardData queryMediaForwardData(BigInteger id)throws Exception;
	
	/**
	 * 根据ID查询mediaForwardData
	 * @param id
	 * @return
	 */
	public MediaForwardData queryMediaForwardDataByIpAndServerTypeAndPort(DataBeanQuery mediaForwardDataQuery)throws Exception;
	
	/**
	 * 级联菜单的查询
	 * @param mediaForwardDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<MediaForwardData> querySelectedMediaForwardData(DataBeanQuery mediaForwardDataQuery)throws Exception;
	
	/**
	 * 查询预置数据MediaForwardData列表
	 * @param mediaForwardDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<MediaForwardData> queryListMediaForwardDataByIpAndServerTypeAndPort(DataBeanQuery mediaForwardDataQuery)throws Exception;
	
	/**
	 * 查询预置数据MediaForwardData列表,根据时间段
	 * @param mediaForwardDataQuery
	 * @return
	 * @throws Exception
	 */
	public List<MediaForwardData> queryListOnDateMediaForwardDataByIpAndServerTypeAndPort(DataBeanQuery mediaForwardDataQuery)throws Exception;
}
