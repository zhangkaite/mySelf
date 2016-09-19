package com.ttmv.monitoring.inter;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.monitoring.entity.WhiteList;
import com.ttmv.monitoring.entity.page.Page;
import com.ttmv.monitoring.entity.querybean.WhiteListQuery;

/**
 * 用户信息
 * @author wll
 */
public interface IWhiteListInter {

	/**
	 * 添加WhiteList
	 * @param WhiteList
	 * @return
	 */
	public Integer addWhiteList(WhiteList whiteList) throws Exception;
	
	/**
	 * 修改WhiteList
	 * @param WhiteList
	 * @return
	 */
	public Integer updateWhiteList(WhiteList whiteList) throws Exception;
	
	/**
	 * 修改WhiteListInter状态
	 * @param WhiteList
	 * @return
	 */
	public Integer deleteWhiteList(BigInteger id) throws Exception;
	
	/**
	 * 根据ID查询WhiteListInter
	 * @param id
	 * @return
	 */
	public WhiteList queryWhiteList(BigInteger id) throws Exception;
	
	/**
	 * 条件查询WhiteList，支持模糊查询
	 * Map对象中存在两个key
	 * sum:查询总数(Integer)
	 * data :查询结果(List)
	 * @param WhiteListQuery
	 * @return
	 */
	public Page queryPageWhiteList(WhiteListQuery whiteListQuery) throws Exception;
	
	/**
	 * 模糊查询WhiteListInter
	 * @return
	 * @throws Exception
	 */
	public List<WhiteList> queryListByConditions(WhiteList whiteListInter)throws Exception;

}
