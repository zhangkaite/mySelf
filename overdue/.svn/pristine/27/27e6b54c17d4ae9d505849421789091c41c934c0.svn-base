package com.ttmv.dao.inter.mysql;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.dao.bean.GoodNumberExpire;
import com.ttmv.dao.bean.query.QueryGoodNumberExpire;

/**
 * 靓号到期接口
 * @author wll
 *
 */
public interface IGoodNumberExpireInter {

	/**
	 * 添加GoodNumberExpire
	 * @param goodNumberExpire
	 * @return
	 * @throws Exception
	 */
	public Integer addGoodNumberExpire(GoodNumberExpire goodNumberExpire)throws Exception;
	
	/**
	 * 修改GoodNumberExpire
	 * @param goodNumberExpire
	 * @return
	 * @throws Exception
	 */
	public Integer updateGoodNumberExpire(GoodNumberExpire goodNumberExpire)throws Exception;
	
	/**
	 * 删除GoodNumberExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Integer deleteGoodNumberExpire(BigInteger id)throws Exception;
	
	/**
	 * 查询GoodNumberExpire
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public GoodNumberExpire queryGoodNumberExpire(BigInteger userId,BigInteger goodNumber)throws Exception;
	
	
	/**
	 * 
	 * @param userId
	 * @param goodNumber
	 * @param numType
	 * @return
	 * @throws Exception
	 */
	public GoodNumberExpire queryGoodNumberExpire(BigInteger userId,BigInteger goodNumber,int numType)throws Exception;
	
	/**
	 * 到期日查询GoodNumberExpire
	 * @param queryGoodNumberExpire
	 * @return
	 */
	public List<GoodNumberExpire> queryListGoodNumberExpireByEndTime(QueryGoodNumberExpire queryGoodNumberExpire)throws Exception;
	
	/**
	 * 提醒日查询GoodNumberExpire
	 * @param queryGoodNumberExpire
	 * @return
	 */
	public List<GoodNumberExpire> queryListGoodNumberExpireByRemindTime(QueryGoodNumberExpire queryGoodNumberExpire)throws Exception;
	
	
	
	/**
	 * vip绑定靓号改变状态
	 */
	public Integer updateFlag(GoodNumberExpire goodNumberExpire)throws Exception;
	
	
	/**
	 * 查询当前用户绑定的靓号
	 */
	
	public GoodNumberExpire queryBindedNum(GoodNumberExpire goodNumberExpire)throws Exception;
	
	/**
	 * 到期后一天提醒
	 */
	public List<GoodNumberExpire> queryListDelayNotify(QueryGoodNumberExpire queryGoodNumberExpire) throws Exception;
}