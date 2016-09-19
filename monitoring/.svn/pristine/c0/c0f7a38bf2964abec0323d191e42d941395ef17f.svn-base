package com.ttmv.monitoring.webService.impl.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.entity.UserInfo;
import com.ttmv.monitoring.entity.page.Page;
import com.ttmv.monitoring.entity.querybean.UserInfoQuery;
import com.ttmv.monitoring.inter.IUserInfoInter;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.webService.WebServerInf;
import com.ttmv.monitoring.webService.tools.ResponseTool;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年9月16日10:45:29
 * @explain : W005_用户列表信息查询（支持条件模糊查询）
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryUsersServiceImpl  implements WebServerInf{

	private static Logger logger = LogManager.getLogger(QueryUsersServiceImpl.class);
	
	private IUserInfoInter iUserInfoInter;

	/**
	 * 用户列表查询（支持模糊查询）
	 * @param reqMap
	 * @return resMap
	 */
	public Map handler(Map reqMap) {
		logger.info("[QueryUsersServiceImpl] @ start...");
		//请求数据验证
		try {
			checkData(reqMap);
		} catch (Exception e) {
			logger.error("数据校验失败!!!" + e.getMessage());
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
		}
		UserInfoQuery userInfoQuery = new UserInfoQuery();
		userInfoQuery.setPage(Integer.parseInt(reqMap.get("page").toString()));
		userInfoQuery.setPageSize(Integer.parseInt(reqMap.get("pageSize").toString()));
		if(reqMap.get("userName") != null){
			userInfoQuery.setUserName(reqMap.get("userName").toString());
		}
		if(reqMap.get("realName") != null){
			userInfoQuery.setUserRealName(reqMap.get("realName").toString());
		}
		if(reqMap.get("mobile") != null){
			userInfoQuery.setUserMobile(reqMap.get("mobile").toString());
		}
		if(reqMap.get("email") != null){
			userInfoQuery.setUserMail(reqMap.get("email").toString());
		}
		
		
		//创建查询对象
		Page pages = null;
		try {
			pages = iUserInfoInter.queryPageUserInfo(userInfoQuery);
		} catch (Exception e) {
			logger.error("用户信息查询处理异常!!!" , e);
			return ResponseTool.returnError();
		}
		logger.info("[用户列表查询成功!!!]");
		return ResponseTool.returnSuccess(takeResData(pages));
	}
	
	/**
	 * 组装返回数据
	 * @param pages
	 * @param userInfo
	 * @return 
	 */
	private Map takeResData(Page pages){
		Map resData = new HashMap();
		resData.put("dataSum", pages.getSum());//总条数
		List<UserInfo> userInfos = pages.getData();
		logger.info("查询记录数：" +  userInfos.size());
		List users = new ArrayList();
		for(int i = 0; i < userInfos.size();i++){
			Map map = new HashMap();
			map.put("userID", userInfos.get(i).getId());
			map.put("userName", userInfos.get(i).getUserName());
			map.put("realName", userInfos.get(i).getUserRealName());
			map.put("mobile", userInfos.get(i).getUserMobile());
			map.put("email", userInfos.get(i).getUserMail());
			map.put("creatTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(userInfos.get(i).getCreateTime()));
			users.add(map);
		}
		resData.put("users", users);//用户list
		return resData;
	}
	

	/**
	 * 业务数据校验(非空，数据规格)
	 * @param reqMap
	 * @return void
	 * @exception Exception
	 */
	public void checkData(Map reqMap) throws Exception {
		if(reqMap.get("page") == null || "".equals(reqMap.get("page").toString())){
			throw new Exception("[QueryUsersServiceImpl_[page] is null...]");
		}
		if(reqMap.get("pageSize") == null || "".equals(reqMap.get("pageSize").toString())){
			throw new Exception("[QueryUsersServiceImpl_[pageSize] is null...]");
		}
	}

	public IUserInfoInter getiUserInfoInter() {
		return iUserInfoInter;
	}

	public void setiUserInfoInter(IUserInfoInter iUserInfoInter) {
		this.iUserInfoInter = iUserInfoInter;
	}
	
	
	
	
}
