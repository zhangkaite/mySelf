package com.ttmv.datacenter.usercenter.dao.interfaces;

import com.ttmv.datacenter.usercenter.domain.data.TerminalForbid;

public interface TerminalForbidDao {
	/**
	 * 新增TerminalForbid
	 * @param TerminalForbid
	 * @throws Exception
	 */
	public Integer addTerminalForbid(TerminalForbid terminalForbid) throws Exception ;
	/**
	 * 删除TerminalForbid 的Key
	 * @param TerminalForbid
	 * @throws Exception
	 */
	public void deleteTerminalForbidKey(TerminalForbid terminalForbid)throws Exception ;
	/**
	 * 判断Key是否存在
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Boolean isExistKey(TerminalForbid terminalForbid)throws Exception;
}