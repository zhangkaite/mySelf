package com.ttmv.dao.inter.mysql;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.dao.bean.VipExpire;
import com.ttmv.dao.bean.query.QueryVipExpire;

public interface IVipExpireInter {

	/**
	 * 添加VipExpire
	 * @param QueryVipExpire
	 * @return
	 * @throws Exception
	 */
	public Integer addVipExpire(VipExpire vipExpire)throws Exception;
	
	/**
	 * 修改VipExpire
	 * @param QueryVipExpire
	 * @return
	 * @throws Exception
	 */
	public Integer updateVipExpire(VipExpire vipExpire)throws Exception;
	
	/**
	 * 删除VipExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Integer deleteVipExpire(BigInteger id)throws Exception;
	
	/**
	 * 查询VipExpire
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public VipExpire queryVipExpire(BigInteger userId)throws Exception;
	
	/**
	 * 到期日查询VipExpire
	 * @param queryVipExpire
	 * @return
	 */
	public List<VipExpire> queryListVipExpireByEndTime(QueryVipExpire queryVipExpire) throws Exception;
	
	/**
	 * 提醒日查询VipExpire
	 * @param queryVipExpire
	 * @return
	 */
	public List<VipExpire> queryListVipExpireByRemindTime(QueryVipExpire queryVipExpire) throws Exception;
	
	
	/**
	 * 到期后一天提醒
	 * @param queryVipExpire
	 * @return
	 * @throws Exception
	 */
	public List<VipExpire> queryListDelayNotify(QueryVipExpire queryVipExpire) throws Exception;
	
	
	
	
	
}