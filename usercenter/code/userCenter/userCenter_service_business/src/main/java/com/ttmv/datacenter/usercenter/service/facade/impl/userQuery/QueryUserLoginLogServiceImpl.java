package com.ttmv.datacenter.usercenter.service.facade.impl.userQuery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserLoginRecordDao;
import com.ttmv.datacenter.usercenter.domain.data.record.UserLoginRecord;
import com.ttmv.datacenter.usercenter.domain.operation.query.UserLoginRecordQuery;
import com.ttmv.datacenter.usercenter.domain.protocol.QueryUserLog;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.usercenter.service.processor.constant.ErrorCodeConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月5日 下午2:43:30  
 * @explain :用户上线记录查询
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryUserLoginLogServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(QueryUserLoginLogServiceImpl.class);
	
	private UserLoginRecordDao userLoginRecordDao;
	public UserLoginRecordDao getUserLoginRecordDao() {
		return userLoginRecordDao;
	}

	public void setUserLoginRecordDao(UserLoginRecordDao userLoginRecordDao) {
		this.userLoginRecordDao = userLoginRecordDao;
	}


	public Map handler(Object object, String reqID) {
		logger.debug("[" + reqID + "]@@" + "[用户上线记录查询]_Start...");
		Long startTime = System.currentTimeMillis();
		
		QueryUserLog queryUserLog = null;
		try {
			queryUserLog = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		UserLoginRecordQuery userLoginRecordQuery = this.getUserLoginRecordQuery(queryUserLog);
		
		List<UserLoginRecord> ls = null;
		try { 
			ls = userLoginRecordDao.selectListBySelective(userLoginRecordQuery);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" +"[数据查询异常！！！]"+e.getMessage());
			return ResponseTool.returnException();
		}
		if(ls == null){
			logger.debug("[" + reqID + "]@@ 查询数据为空！！！");
			return ResponseTool.returnSuccess(null);
		}
		//组装返回数据
		Map resMap = this.takeResData(ls);
		logger.info("[" + reqID + "]@@"+ "[***用户上线记录查询***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return resMap;
	}
	
	/**
	 * 组装返回信息
	 * @param ls
	 * @return
	 */
	private Map takeResData(List<UserLoginRecord> ls){
		List result = new ArrayList();
		for(int i=0; i<ls.size(); i++){
			UserLoginRecord record = ls.get(i);
			Map map = new HashMap();
			map.put("TTnum", record.getTTnum());
			map.put("clientType", record.getClientType());
			map.put("IP", record.getIp());
			map.put("MAC", record.getMac());
			map.put("HdNum", record.getDisksn());
			map.put("time", record.getTime());
			result.add(map);
		}
		return ResponseTool.returnSuccess(result);
	}
	

	/**
	 * 创建查询对象
	 * @param queryUserLog
	 * @return
	 */
	private UserLoginRecordQuery getUserLoginRecordQuery(QueryUserLog queryUserLog){
		UserLoginRecordQuery userLoginRecordQuery = new UserLoginRecordQuery();
		try {
			userLoginRecordQuery.setTTnum(queryUserLog.getTTnum());
			userLoginRecordQuery.setType(queryUserLog.getType());
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			if(queryUserLog.getStartTime() != null){
				userLoginRecordQuery.setStartTime(ft.parse(queryUserLog.getStartTime()));
			}
			if(queryUserLog.getEndTime() != null){
				userLoginRecordQuery.setEndTime(ft.parse(queryUserLog.getEndTime()));
			}
			if(queryUserLog.getPage()!=null){
				userLoginRecordQuery.setPage(queryUserLog.getPage());
			}
			if(queryUserLog.getPageSize() != null){
				userLoginRecordQuery.setPageSize(queryUserLog.getPageSize());
			}
		} catch (Exception e) {
			logger.error("创建查询对象失败!!!"+e.getMessage());
			e.printStackTrace();
		}
		return userLoginRecordQuery;
	}
	
	/**
	 * 数据验证
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private QueryUserLog checkData(Object object) throws Exception{
		QueryUserLog queryUserLog = (QueryUserLog) object;
		if(queryUserLog == null){
			throw new Exception("对象转换异常！！！");
		}
		if(queryUserLog.getTTnum() == null){
			throw new Exception("TTnum为空！！！");
		}
		if(queryUserLog.getType() == null){
			throw new Exception("查询类型type为空！！！");
		}
		return queryUserLog;
	}

}
