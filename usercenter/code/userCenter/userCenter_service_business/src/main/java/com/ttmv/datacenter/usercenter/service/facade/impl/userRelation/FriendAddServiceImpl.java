package com.ttmv.datacenter.usercenter.service.facade.impl.userRelation;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.GroupDao;
import com.ttmv.datacenter.usercenter.dao.interfaces.UserCrossRelationDao;
import com.ttmv.datacenter.usercenter.domain.data.Group;
import com.ttmv.datacenter.usercenter.domain.data.UserCrossRelation;
import com.ttmv.datacenter.usercenter.domain.operation.query.GroupQuery;
import com.ttmv.datacenter.usercenter.domain.operation.query.UserCrossRelationQuery;
import com.ttmv.datacenter.usercenter.domain.protocol.AddFriend;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;
import com.ttmv.datacenter.usercenter.service.processor.constant.UcConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月2日 下午9:35:14  
 * @explain :添加好友
 */
@SuppressWarnings({ "rawtypes" })
public class FriendAddServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(FriendAddServiceImpl.class);
	private GroupDao groupDao;
	
	public GroupDao getGroupDao() {
		return groupDao;
	}
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}


	private UserCrossRelationDao userCrossRelationDao;
	public UserCrossRelationDao getUserCrossRelationDao() {
		return userCrossRelationDao;
	}
	public void setUserCrossRelationDao(UserCrossRelationDao userCrossRelationDao) {
		this.userCrossRelationDao = userCrossRelationDao;
	}

	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[好友添加]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		AddFriend addFriend = null;
		try {
			addFriend = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		UserCrossRelation userCrossRelation;
		try {
			userCrossRelation = this.creatuserCrossRelation(addFriend);
			userCrossRelation.setReqId(reqID);
		} catch (Exception e1) {
			logger.error("[" + reqID + "]@@"+"查询[我的好友]组ID失败！！！" +e1.getMessage());
			return ResponseTool.returnException();
		}
		
		//创建查询对象
		UserCrossRelationQuery crossRelationQuery = new UserCrossRelationQuery();
		crossRelationQuery.setUserIdA(addFriend.getUserID());
		crossRelationQuery.setUserIdB(addFriend.getFriendId());
		crossRelationQuery.setType(UcConstant.RELATIONSHIPTYPE_FRIEND);
		List<UserCrossRelation> userCrossRelations = null;
		try {
			userCrossRelations = userCrossRelationDao.selectListBySelective(crossRelationQuery);
		} catch (Exception e1) {
			logger.error("[" + reqID + "]@@"+"好友重复添加查询异常！！！"+e1.getMessage());
			return ResponseTool.returnException();
		}
		if(userCrossRelations != null && userCrossRelations.size()>=1){//已经是好友关系返回提醒
			logger.warn("[" + reqID + "]@@"+"无法重复添加好友！！！");
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE, ErrorCodeConstant.ERRORMSG_PARAMETVIOLATE_CODE);
			
		}
		try {
			userCrossRelationDao.addUserCrossRelation(userCrossRelation);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@"+"数据添加失败！！！" +e.getMessage());
			return ResponseTool.returnException();
		}
		logger.info("[" + reqID + "]@@"+ "[***添加好友***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(null);
	}
	
	
	/**
	 * 创建对象
	 * @param addFriend
	 * @return
	 * @throws Exception 
	 */
	private UserCrossRelation creatuserCrossRelation(AddFriend addFriend) throws Exception{
		UserCrossRelation userCrossRelation = new UserCrossRelation();
		userCrossRelation.setType(UcConstant.RELATIONSHIPTYPE_FRIEND);//关系类型（好友）
		userCrossRelation.setUserIdA(addFriend.getUserID());
		userCrossRelation.setUserIdB(addFriend.getFriendId());
		userCrossRelation.setRemarkName(null);//备注名称
		userCrossRelation.setGroupId(this.getGroup(addFriend.getUserID()).getGroupId());//新加好友，默认在“1”分组（我的好友）
		return userCrossRelation;
	}
	
	/**
	 * 
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	private Group getGroup(BigInteger userID) throws Exception{
		GroupQuery groupQuery = new GroupQuery();
		groupQuery.setUserId(userID);
		groupQuery.setDefaultType(1);
		List<Group> ls=groupDao.selectListBySelective(groupQuery);
		return ls.get(0);
		
	}
	
	
	/**
	 * 数据验证
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	private AddFriend checkData(Object object) throws Exception{
		AddFriend addFriend = (AddFriend)object;
		if(addFriend == null){
			throw new Exception("对象转换异常！！！");
		}
		if(addFriend.getUserID() == null){
			throw new Exception("userID为空！！！");
		}
		if(addFriend.getFriendId() == null){
			throw new Exception("好友ID为空！！！");
		}
		return addFriend;
	}

}
