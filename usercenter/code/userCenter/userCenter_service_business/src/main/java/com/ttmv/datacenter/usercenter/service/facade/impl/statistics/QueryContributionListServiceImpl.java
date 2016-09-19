package com.ttmv.datacenter.usercenter.service.facade.impl.statistics;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserContributeDao;
import com.ttmv.datacenter.usercenter.domain.data.UserContribution;
import com.ttmv.datacenter.usercenter.domain.protocol.QueryContributionList;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**
 * 贡献周榜/月榜查询
 * 
 * @author Damon
 * @time 2015年11月27日11:10:25
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryContributionListServiceImpl extends AbstractUserBase {

	private static Logger logger = LogManager.getLogger(QueryContributionListServiceImpl.class);

	private UserContributeDao userContributeDao;

	public UserContributeDao getUserContributeDao() {
		return userContributeDao;
	}

	public void setUserContributeDao(UserContributeDao userContributeDao) {
		this.userContributeDao = userContributeDao;
	}

	@Override
	public Map handler(Object object, String reqID) {
		logger.debug("[" + reqID + "]@@" + "[周共享榜查询]_Start...");
		Long startTime = System.currentTimeMillis();
		QueryContributionList queryContributionList = null;
		try {
			queryContributionList = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,
					ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		// 查询
		List<UserContribution> userContributions = null;
		try {
			UserContribution userContribution=new UserContribution();
			userContribution.setRoomId(queryContributionList.getRoomID());
			userContribution.setDataType(queryContributionList.getDataType());
			userContributions = userContributeDao.getAllUserContributionByRoomId(userContribution);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "[数据查询异常！！！]" + e.getMessage());
			return ResponseTool.returnException();
		}
		if (userContributions == null) {
			logger.debug("[" + reqID + "]@@ 查询数据为空！！！");
			return ResponseTool.returnSuccess(this.createContributionDate(userContributions,queryContributionList.getRoomID()));
		}
		Map resMap = this.createContributionDate(userContributions,queryContributionList.getRoomID());
		logger.info("[" + reqID + "]@@" + "[***周贡献榜查询***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(resMap);
	}

	/**
	 * 组装返回数据
	 * 
	 * @param userContributions
	 * @return resMap
	 */
	private Map createContributionDate(List<UserContribution> userContributions ,BigInteger roomID) {
		
		Map resData = new HashMap();
		if(userContributions!=null){
			List contributionList = new ArrayList();
			if (userContributions.size() > 0) {
				for (int i = 0; i < userContributions.size(); i++) {
					Map map = new HashMap();
					map.put("userID", userContributions.get(i).getUserId());
					map.put("nickName", userContributions.get(i).getNickName());
					map.put("amount", userContributions.get(i).getContributionSum());
					map.put("roomID", userContributions.get(i).getRoomId());
					map.put("userLogo", userContributions.get(i).getUserPhoto());
					contributionList.add(map);
				}
			}
			resData.put("roomID", roomID);
			resData.put("contributionList", contributionList);
		}else{
			resData.put("roomID", roomID);
			resData.put("contributionList", null);
		}
		return resData;
		
		
		

	}

	/**
	 * 数据验证
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private QueryContributionList checkData(Object object) throws Exception {
		QueryContributionList queryContributionList = (QueryContributionList) object;
		if (queryContributionList == null) {
			throw new Exception("对象转换异常！！！");
		}
		if (queryContributionList.getRoomID() == null) {
			throw new Exception("RoomID为空！！！");
		}
		
		if (queryContributionList.getDataType() == null) {
			throw new Exception("dataType为空！！！");
		}
		
		return queryContributionList;
	}

}
