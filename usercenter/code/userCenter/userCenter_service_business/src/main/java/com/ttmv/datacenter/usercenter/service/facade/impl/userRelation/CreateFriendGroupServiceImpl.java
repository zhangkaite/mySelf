package com.ttmv.datacenter.usercenter.service.facade.impl.userRelation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.GroupDao;
import com.ttmv.datacenter.usercenter.domain.data.Group;
import com.ttmv.datacenter.usercenter.domain.operation.query.GroupQuery;
import com.ttmv.datacenter.usercenter.domain.protocol.CreateFriendGroup;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月4日 下午2:15:50  
 * @explain :创建好友分组
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CreateFriendGroupServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(CreateFriendGroupServiceImpl.class);
	private GroupDao groupDao;
	public GroupDao getGroupDao() {
		return groupDao;
	}
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
	


	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[创建好友分组]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		CreateFriendGroup createFriendGroup = null;
		//数据验证
		try {
			createFriendGroup = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		//查询当前组个数，order+1
		GroupQuery groupQuery = new GroupQuery();
		groupQuery.setUserId(createFriendGroup.getUserID());
		List<Group> ls = null;
		try {
			ls = groupDao.selectListBySelective(groupQuery);
		} catch (Exception e1) {
			logger.error("[" + reqID + "]@@"+"好友分组总数查询失败！！！"+e1.getMessage());
			return ResponseTool.returnException();
		}
		if(ls == null || ls.size() == 0){
			logger.warn("[" + reqID + "]@@"+"没有查询到好友分组！！！");
			return ResponseTool.returnException();
		}
		
		//创建Group对象
		Group group = this.createGroup(createFriendGroup,ls.size());
		group.setReqId(reqID);
		//入库
		try {
			groupDao.addUgroup(group);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@"+"数据添加失败！！！"+e.getMessage());
			return ResponseTool.returnException();
		}
		logger.info("[" + reqID + "]@@"+ "[***创建好友分组***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		Map resMap = new HashMap();
		resMap.put("groupId", group.getGroupId());
		return ResponseTool.returnSuccess(resMap);
	}
	
	/**
	 * 创建添加对象
	 * @param createFriendGroup
	 * @return
	 */
	private Group createGroup(CreateFriendGroup createFriendGroup,int size){
		Group group = new Group();
		group.setUserId(createFriendGroup.getUserID());
		group.setName(createFriendGroup.getGroupName());	
		group.setDefaultType(new Integer(2));//组类型-普通组
		group.setGorder(size);
		return group;
	}
	
	/**
	 * 数据验证
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private CreateFriendGroup checkData(Object object) throws Exception{
		CreateFriendGroup createFriendGroup = (CreateFriendGroup)object;
		if(createFriendGroup == null){
			throw new Exception("对象转换失败！！！");
		}
		if(createFriendGroup.getUserID() == null){
			throw new Exception("UserID为空！！！");
		}
		if(createFriendGroup.getGroupName() == null){
			throw new Exception("GroupName为空！！！");
		}
		
		return createFriendGroup;
	}

}
