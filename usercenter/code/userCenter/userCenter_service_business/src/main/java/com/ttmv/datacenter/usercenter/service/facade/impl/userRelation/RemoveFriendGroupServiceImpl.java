package com.ttmv.datacenter.usercenter.service.facade.impl.userRelation;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.GroupDao;
import com.ttmv.datacenter.usercenter.domain.protocol.RemoveFriendGroup;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月4日 下午2:18:41  
 * @explain :删除好友分组
 */
@SuppressWarnings({ "rawtypes"})
public class RemoveFriendGroupServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(RemoveFriendGroupServiceImpl.class);
	private GroupDao groupDao;
	public GroupDao getGroupDao() {
		return groupDao;
	}
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[删除好友分组]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		RemoveFriendGroup removeFriendGroup = null;
		try {
			removeFriendGroup = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		try {
			groupDao.deleteUgroup(removeFriendGroup.getGroupId(),reqID);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" +"数据删除失败！！！"+e.getMessage());
			return ResponseTool.returnException();
		}
		logger.info("[" + reqID + "]@@"+ "[***删除好友分组***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(null);
	}
	
	private RemoveFriendGroup checkData(Object object) throws Exception{
		RemoveFriendGroup removeFriendGroup =(RemoveFriendGroup)object;
		if(removeFriendGroup == null){
			throw new Exception("对象转换失败！！！");
		}
		if(removeFriendGroup.getGroupId() == null){
			throw new Exception("GroupId为空！！！");
		}
		return removeFriendGroup;
	}

}
