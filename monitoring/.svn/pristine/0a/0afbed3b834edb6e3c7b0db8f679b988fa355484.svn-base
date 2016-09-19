package com.ttmv.monitoring.inter;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.ttmv.monitoring.entity.ThresholdInfo;

public interface IThresholdInfoInter {

	
	/**
	 * 根据ID查询ThresholdInfo
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public ThresholdInfo queryThresholdInfo(BigInteger id)throws Exception;
	/**
	 * 根据type查询ThresholdInfo
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<ThresholdInfo> queryThresholdInfoByType(String type)throws Exception;
	
	/**
	 * 更新ThresholdInfo
	 * @param listThresholdInfo
	 * @return
	 * @throws Exception
	 */
	public Integer updateListThresholdInfo(ThresholdInfo thresholdInfo)throws Exception;
	
	/**
	 * 更新ThresholdInfo，一次更新多条数据
	 * @param listThresholdInfo
	 * @return
	 * @throws Exception
	 */
	public Integer updateListThresholdInfo(Map map)throws Exception;
	
	/**
	 * 查询全部阀值
	 * @return
	 * @throws Exception
	 */
	public List<ThresholdInfo> queryAllThresholdInfo()throws Exception;
}
