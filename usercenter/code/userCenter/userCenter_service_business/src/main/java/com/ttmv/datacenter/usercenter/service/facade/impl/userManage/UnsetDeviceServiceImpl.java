package com.ttmv.datacenter.usercenter.service.facade.impl.userManage;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.TerminalForbidDao;
import com.ttmv.datacenter.usercenter.domain.data.TerminalForbid;
import com.ttmv.datacenter.usercenter.domain.protocol.UnsetDevice;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月3日 上午10:01:41  
 * @explain :解封终端设备
 */
@SuppressWarnings({ "rawtypes" })
public class UnsetDeviceServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(UnsetDeviceServiceImpl.class);
	private TerminalForbidDao terminalForbidDao;
	public TerminalForbidDao getTerminalForbidDao() {
		return terminalForbidDao;
	}
	public void setTerminalForbidDao(TerminalForbidDao terminalForbidDao) {
		this.terminalForbidDao = terminalForbidDao;
	}


	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[封终端设备]_开始逻辑处理...");
		long startTime = System.currentTimeMillis();
		UnsetDevice unsetDevice = null;
		try {
			unsetDevice = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnException();
		}
		TerminalForbid terminalForbid = new TerminalForbid();
		terminalForbid.setIp(unsetDevice.getIp());	
		terminalForbid.setMac(unsetDevice.getMac());
		terminalForbid.setDisksn(unsetDevice.getHdNum());
		terminalForbid.setReqId(reqID);
		try {
			terminalForbidDao.deleteTerminalForbidKey(terminalForbid);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseTool.returnException();
		}
		logger.info("[" + reqID + "]@@"+ "[***解封终端设备***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(null);
	}
	

	
	/**
	 * 数据验证
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	private UnsetDevice checkData(Object object) throws Exception{
		UnsetDevice unsetDevice = (UnsetDevice)object;
		if(unsetDevice == null){
			throw new Exception("对象转换失败！！！");
		}
		
		return unsetDevice;
	}

}
