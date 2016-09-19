package com.ttmv.datacenter.usercenter.service.facade.impl.userQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.GroupDao;
import com.ttmv.datacenter.usercenter.domain.data.Group;
import com.ttmv.datacenter.usercenter.domain.protocol.QueryFriendGroupInfo;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月5日 下午11:44:06  
 * @explain :好友分组信息查询
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryFriendGroupInfoServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(QueryFriendGroupInfoServiceImpl.class);
	private GroupDao groupDao;
	
	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public Map handler(Object object, String reqID) {
		logger.debug("[" + reqID + "]@@" + "[好友分组信息查询]_Start...");
		Long startTime = System.currentTimeMillis();
		QueryFriendGroupInfo friendGroupInfo = null;
		try {
			friendGroupInfo = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		List<Group> list = null;
		try {
			list = groupDao.selectGroupsByIds(friendGroupInfo.getGroupIDs());
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" +"[数据查询异常！！！]"+e.getMessage());
			return ResponseTool.returnException();
		}
		if(list == null){
			logger.debug("[" + reqID + "]@@ 查询数据为空！！！");
			return ResponseTool.returnSuccess(null);
		}
		
		//组装返回信息
		Map resMap = this.takeResData(list);
		logger.info("[" + reqID + "]@@"+ "[***好友分组信息查询***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return resMap;
	}
	
	/**
	 * 拼返回数据
	 * @param group
	 * @return
	 */
	private Map takeResData(List<Group> groups){
//		Map resMap = new HashMap();
		List result = new ArrayList();
		for(int i=0; i<groups.size(); i++){
			Group gp = groups.get(i);
			Map map = new HashMap();
			map.put("groupName", gp.getName());
			map.put("groupOrder", gp.getGorder());
			map.put("defaultType", gp.getDefaultType());
			result.add(map);
		}
//		resMap.put("resData", result);
		return ResponseTool.returnSuccess(result);
	}
	
	/**
	 * 数据验证
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private QueryFriendGroupInfo checkData(Object object) throws Exception{
		QueryFriendGroupInfo friendGroupInfo = (QueryFriendGroupInfo)object;
		if(friendGroupInfo == null){
			throw new Exception("对象转换异常！！！");
		}
		if(friendGroupInfo.getGroupIDs()==null){
			throw new Exception("GroupIDs为空！！！");
		}
		return friendGroupInfo;
	}

}
