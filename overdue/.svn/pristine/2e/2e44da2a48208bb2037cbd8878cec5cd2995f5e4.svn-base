package com.ttmv.dao.inter.mysql;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.dao.bean.LuxuryExpire;
import com.ttmv.dao.bean.query.QueryLuxuryExpire;

/**
 * 豪车到期接口
 * @author wll
 *
 */
public interface ILuxuryExpireInter {

	/**
	 * 添加LuxuryExpire
	 * @param luxuryExpire
	 * @return
	 * @throws Exception
	 */
	public Integer addLuxuryExpire(LuxuryExpire luxuryExpire)throws Exception;
	
	/**
	 * 修改LuxuryExpire
	 * @param luxuryExpire
	 * @return
	 * @throws Exception
	 */
	public Integer updateLuxuryExpire(LuxuryExpire luxuryExpire)throws Exception;
	
	/**
	 * 删除LuxuryExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Integer deleteLuxuryExpire(BigInteger id)throws Exception;
	
	/**
	 * 查询LuxuryExpire
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public LuxuryExpire queryLuxuryExpire(BigInteger userId,BigInteger carId)throws Exception;
	
	/**
	 * 到期日查询LuxuryExpire
	 * @param queryLuxuryExpire
	 * @return
	 */
	public List<LuxuryExpire> queryListLuxuryExpireByEndTime(QueryLuxuryExpire queryLuxuryExpire)throws Exception;
	
	/**
	 * 提醒日查询LuxuryExpire
	 * @param queryLuxuryExpire
	 * @return
	 */
	public List<LuxuryExpire> queryListLuxuryExpireByRemindTime(QueryLuxuryExpire queryLuxuryExpire)throws Exception;
	
	/**
	 * 到期后一天提醒
	 */
	public List<LuxuryExpire> queryListDelayNotify(QueryLuxuryExpire queryLuxuryExpire) throws Exception;
}