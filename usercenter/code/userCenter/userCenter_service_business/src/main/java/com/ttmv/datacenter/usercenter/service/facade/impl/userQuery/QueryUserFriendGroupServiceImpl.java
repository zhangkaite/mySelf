package com.ttmv.datacenter.usercenter.service.facade.impl.userQuery;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.ttmv.datacenter.usercenter.domain.protocol.QueryUserFriendGroup;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年2月5日 下午11:43:47
 * @explain :用户好友 好友分组 关系
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryUserFriendGroupServiceImpl extends AbstractUserBase {

	private static Logger logger = LogManager.getLogger(QueryUserFriendGroupServiceImpl.class);
	private UserCrossRelationDao userCrossRelationDao;
	private GroupDao groupDao;

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public UserCrossRelationDao getUserCrossRelationDao() {
		return userCrossRelationDao;
	}

	public void setUserCrossRelationDao(
			UserCrossRelationDao userCrossRelationDao) {
		this.userCrossRelationDao = userCrossRelationDao;
	}

	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[:用户好友 好友分组 关系查询]_Start...");
		Long startTime = System.currentTimeMillis();
		QueryUserFriendGroup queryUserFriendGroup = null;
		try {
			queryUserFriendGroup = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		UserCrossRelationQuery userCrossRelationQuery = this.createUserCrossRelationQuery(queryUserFriendGroup);
		//查询好友分组信息（组名称,id,排序）
		GroupQuery groupQuery = new GroupQuery();
		groupQuery.setUserId(queryUserFriendGroup.getUserID());
		List result = new ArrayList();
		try {
			List<Group> groups = groupDao.selectListBySelective(groupQuery);
			if(groups == null){
				logger.debug("[" + reqID + "]@@ 查询group数据为空！！！");
				return ResponseTool.returnSuccess(null);
			}
			for (int i = 0; i < groups.size(); i++) {
				userCrossRelationQuery.setGroupId(groups.get(i).getGroupId());
				userCrossRelationQuery.setPageSize(9999);//分页参数覆盖2015年5月12日15:05:23
				List<UserCrossRelation> userCrossRelations = userCrossRelationDao.selectListBySelective(userCrossRelationQuery);
				Map map = new HashMap();
				map.put("groupId", groups.get(i).getGroupId());//组ID
				map.put("groupName", groups.get(i).getName());//组名称
				map.put("groupType", groups.get(i).getDefaultType());//组类型
				map.put("groupOrder", groups.get(i).getGorder());//组序号
				List userList = new ArrayList();
				if(userCrossRelations != null && userCrossRelations.size() != 0){
					for (int j = 0; j < userCrossRelations.size(); j++) {
						Map userMap = new HashMap();
						userMap.put("friendId", userCrossRelations.get(j).getUserIdB());//好友ID
						userMap.put("remarkName", userCrossRelations.get(j).getRemarkName());//好友备注
						userList.add(userMap);
					}
				}
				map.put("FuserIDs",userList);//好友ID
				result.add(map);
				
			}
			
		} catch (Exception e1) {
			logger.error("[" + reqID + "]@@" +"[查询失败]@@"+e1.getMessage());
			return ResponseTool.returnException();
		}
		// 组装返回信息
		logger.info("[" + reqID + "]@@"+ "[***用户好友 好友分组 关系***] && [业务处理耗时(ms)]:"+ (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(result);
	}

	/**
	 * 查询对象创建
	 * 
	 * @param queryUserFriendGroup
	 * @return
	 */
	private UserCrossRelationQuery createUserCrossRelationQuery(
			QueryUserFriendGroup queryUserFriendGroup) {
		UserCrossRelationQuery crossRelationQuery = new UserCrossRelationQuery();
		crossRelationQuery.setUserIdA(queryUserFriendGroup.getUserID());
		return crossRelationQuery;
	}

	/**
	 * 数据验证
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private QueryUserFriendGroup checkData(Object object) throws Exception {
		QueryUserFriendGroup queryUserFriendGroup = (QueryUserFriendGroup) object;
		if (queryUserFriendGroup == null) {
			throw new Exception("对象转换失败！！！");
		}
		if (queryUserFriendGroup.getUserID() == null) {
			throw new Exception("UserID为空！！！");
		}

		return queryUserFriendGroup;
	}

}
