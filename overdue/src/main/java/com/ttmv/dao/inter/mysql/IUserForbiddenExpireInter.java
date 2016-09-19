package com.ttmv.dao.inter.mysql;

import java.math.BigInteger;
import java.util.List;

import com.ttmv.dao.bean.UserForbiddenExpire;
import com.ttmv.dao.bean.query.QueryUserForbiddenExpire;

/**
 * 用户冻结到期接口
 * @author wll
 *
 */
public interface IUserForbiddenExpireInter {

	/**
	 * 添加UserForbiddenExpire
	 * @param userForbiddenExpire
	 * @return
	 * @throws Exception
	 */
	public Integer addUserForbiddenExpire(UserForbiddenExpire userForbiddenExpire)throws Exception;
	
	/**
	 * 修改UserForbiddenExpire
	 * @param userForbiddenExpire
	 * @return
	 * @throws Exception
	 */
	public Integer updateUserForbiddenExpire(UserForbiddenExpire userForbiddenExpire)throws Exception;
	
	/**
	 * 删除UserForbiddenExpire
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Integer deleteUserForbiddenExpire(BigInteger id)throws Exception;
	
	/**
	 * 查询UserForbiddenExpire
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public UserForbiddenExpire queryUserForbiddenExpire(BigInteger userId)throws Exception;
	
	/**
	 * 到期日期查询UserForbiddenExpire
	 * @param queryUserForbiddenExpire
	 * @return
	 */
	public List<UserForbiddenExpire> queryListUserForbiddenExpireByEndTime(QueryUserForbiddenExpire queryUserForbiddenExpire) throws Exception;
	
	/**
	 * 提醒日查询UserForbiddenExpire
	 * @param queryUserForbiddenExpire
	 * @return
	 */
	public List<UserForbiddenExpire> queryListUserForbiddenExpireByRemindTime(QueryUserForbiddenExpire queryUserForbiddenExpire) throws Exception;
}