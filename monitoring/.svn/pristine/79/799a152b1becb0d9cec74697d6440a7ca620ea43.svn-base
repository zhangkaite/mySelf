package com.ttmv.monitoring.inter;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.page.Page;
import com.ttmv.monitoring.entity.querybean.AlerterInfoQuery;
import com.ttmv.monitoring.entity.tmp.AlerterInfoTmp;

public interface IAlerterInfoInter {

	/**
	 * 添加AlerterInfo
	 * @param AlerterInfo
	 * @return
	 */
	public Integer addAlerterInfo(AlerterInfoTmp alerterInfo) throws Exception;
	
	/**
	 * 修改AlerterInfo
	 * @param AlerterInfo
	 * @return
	 */
	public Integer updateAlerterInfo(AlerterInfoTmp alerterInfo) throws Exception;
	
	/**
	 * 根据ID查询AlerterInfo
	 * @param id
	 * @return
	 */
	public AlerterInfoTmp queryAlerterInfo(BigInteger id) throws Exception;
	
	/**
	 * 根据ID删除AlerterInfo
	 * @param id
	 * @return
	 */
	public Integer deleteAlerterInfo(BigInteger id) throws Exception;
	
	/**
	 * 条件查询AlerterInfo，支持模糊查询
	 * Map对象中存在两个key
	 * sum:查询总数(Integer)
	 * data :查询结果(List)
	 * @param AlerterInfoQuery
	 * @return
	 */
	public Page queryPageAlerterInfo(AlerterInfoQuery alerterInfoQuery) throws Exception;
	
	/**
	 * 条件查询AlerterInfo，精确查询
	 * @param AlerterInfoQuery
	 * @return
	 */
	public List<AlerterInfoTmp> queryAlerterInfo(AlerterInfoQuery alerterInfoQuery) throws Exception;
}
