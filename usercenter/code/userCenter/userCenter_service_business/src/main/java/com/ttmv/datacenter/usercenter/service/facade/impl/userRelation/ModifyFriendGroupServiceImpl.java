package com.ttmv.datacenter.usercenter.service.facade.impl.userRelation;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.GroupDao;
import com.ttmv.datacenter.usercenter.domain.data.Group;
import com.ttmv.datacenter.usercenter.domain.protocol.ModifyFriendGroup;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月4日 下午2:16:58  
 * @explain :修改好友分组
 */
@SuppressWarnings({ "rawtypes"})
public class ModifyFriendGroupServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(ModifyFriendGroupServiceImpl.class);
	private GroupDao groupDao;
	public GroupDao getGroupDao() {
		return groupDao;
	}
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[修改好友分组]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		ModifyFriendGroup modifyFriendGroup = null;
		try {
			modifyFriendGroup = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		Group ugroup = this.createGroup(modifyFriendGroup);
		ugroup.setReqId(reqID);
		try {
			groupDao.updateUgroup(ugroup);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" +"数据修改失败！！！",e);
			return ResponseTool.returnException();
		}
		logger.info("[" + reqID + "]@@"+ "[***修改好友分组***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(null);
	}
	
	/**
	 * 创建修改对象
	 * @param modifyFriendGroup
	 * @return
	 */
	private Group createGroup(ModifyFriendGroup modifyFriendGroup){
		Group group = new Group();
		group.setUserId(modifyFriendGroup.getUserID());
		group.setGroupId(modifyFriendGroup.getGroupId());
		group.setName(modifyFriendGroup.getGroupName());
		return group;
	}
	
	
	/**
	 * 数据验证
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private ModifyFriendGroup checkData(Object object) throws Exception{
		ModifyFriendGroup modifyFriendGroup = (ModifyFriendGroup)object;
		if(modifyFriendGroup == null){
			throw new Exception("对象转换错误！！！");
		}
		if(modifyFriendGroup.getUserID() == null){
			throw new Exception("userID为空！！！");
		}
		if(modifyFriendGroup.getGroupId() == null){
			throw new Exception("GroupId为空！！！");
		}
		if(modifyFriendGroup.getGroupName() == null){
			throw new Exception("GroupName为空！！！");
		}
		return modifyFriendGroup;
	}

}
