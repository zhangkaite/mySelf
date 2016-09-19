package com.ttmv.dao.inter.mysql;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.dao.bean.NobilityExpire;
import com.ttmv.dao.bean.query.QueryNobilityExpire;

/**
 * 爵位到期接口
 * @author wll
 *
 */
public interface INobilityExpireInter {

	/**
	 * 添加NobilityExpire
	 * @param nobilityExpire
	 * @return
	 * @throws Exception
	 */
	public Integer addNobilityExpire(NobilityExpire nobilityExpire)throws Exception;
	
	/**
	 * 修改NobilityExpire
	 * @param nobilityExpire
	 * @return
	 * @throws Exception
	 */
	public Integer updateNobilityExpire(NobilityExpire nobilityExpire)throws Exception;
	
	/**
	 * 删除NobilityExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Integer deleteNobilityExpire(BigInteger id)throws Exception;
	
	/**
	 * 查询NobilityExpire
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public NobilityExpire queryNobilityExpire(BigInteger userId)throws Exception;
	
	/**
	 * 到期日查询NobilityExpire
	 * @param queryNobilityExpire
	 * @return
	 */
	public List<NobilityExpire> queryListNobilityExpireByEndTime(QueryNobilityExpire queryNobilityExpire) throws Exception;
	
	/**
	 * 提醒日查询NobilityExpire
	 * @param queryNobilityExpire
	 * @return
	 */
	public List<NobilityExpire> queryListNobilityExpireByRemindTime(QueryNobilityExpire queryNobilityExpire) throws Exception;
	
	/**
	 * 到期后一天提醒
	 */
	public List<NobilityExpire> queryListDelayNotify(QueryNobilityExpire queryNobilityExpire) throws Exception;
}