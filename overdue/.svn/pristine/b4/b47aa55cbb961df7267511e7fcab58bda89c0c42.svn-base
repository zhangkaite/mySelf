package com.ttmv.service.input.impl.control;

import java.math.BigInteger;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ttmv.dao.bean.Cmp;
import com.ttmv.dao.inter.mysql.ICmpExpireInter;
import com.ttmv.service.OverdueService;
import com.ttmv.service.input.ResponseTool;
import com.ttmv.service.tools.constant.ErrorCodeConstant;

/**
 * 
 * @author kate
 *  关闭金色弹窗（开始/结束）提醒
 */
@SuppressWarnings("rawtypes")
@Service("inOverdueCloseCMPServiceImpl")
public class InOverdueCloseCMPServiceImpl  extends OverdueService{
	private static Logger logger = LogManager.getLogger(InOverdueCloseCMPServiceImpl.class);
	@Resource(name="icmpExpireInterImpl")
	private ICmpExpireInter  iCmpExpireInterImpl;
	@Override
	public Map handler(Map obj) {
		try {
			checkData(obj);
		} catch (Exception e) {
			logger.error("关闭金色弹窗数据校验失败！！！", e);
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_DATAFORMAT_ERROR_CODE);
		}
		
		Cmp data;
		try {
			data = (Cmp)getDataObject(obj);
		} catch (Exception e) {
			logger.error("关闭金色弹窗数据转换对象失败！！！", e);
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_DATAFORMAT_ERROR_CODE);
		}
		
		try {
			iCmpExpireInterImpl.deleteCmp(data);
		} catch (Exception e) {
			logger.error("关闭金色弹窗数据删除失败！！！", e);
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_DATAFORMAT_ERROR_CODE);
		}
		return ResponseTool.returnSuccess();
	}

	@Override
	public Object getDataObject(Map reqMap) throws Exception {
		Cmp cmp=new Cmp();
		cmp.setUserId(new BigInteger(reqMap.get("userID").toString()));
		cmp.setTag(reqMap.get("type").toString());
		return cmp;
	}

	@Override
	public void checkData(Map reqMap) throws Exception {
		if (reqMap.get("userID") == null || "".equals(reqMap.get("userID"))) {
			throw new Exception("[InOverdueCloseCMPServiceImpl[userID] is null...]");
		}
		
		if (reqMap.get("type") == null || "".equals(reqMap.get("type"))) {
			throw new Exception("[InOverdueCloseCMPServiceImpl[type] is null...]");
		}
		
		
	}

}
