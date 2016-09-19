package com.ttmv.datacenter.usercenter.service.facade.impl.userManage;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.TerminalForbidDao;
import com.ttmv.datacenter.usercenter.domain.data.TerminalForbid;
import com.ttmv.datacenter.usercenter.domain.protocol.ForceOutDevice;
import com.ttmv.datacenter.usercenter.service.facade.template.AbstractUserBase;
import com.ttmv.datacenter.usercenter.service.facade.tools.ResponseTool;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月3日 上午10:00:26  
 * @explain :封终端设备
 */
@SuppressWarnings({ "rawtypes" })
public class ForceOutDeviceServiceImpl extends AbstractUserBase{
	private static Logger logger = LogManager.getLogger(ForceOutDeviceServiceImpl.class);
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
		ForceOutDevice forceOutDevice = null;
		//数据校验
		try {
			forceOutDevice = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnException();
		}
		//创建添加对象
		TerminalForbid terminalForbid = this.createTerminalForbid(forceOutDevice,reqID);
		try {
			terminalForbidDao.addTerminalForbid(terminalForbid);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@"+"数据添加失败！！！"+e.getMessage());
			return ResponseTool.returnException();
		}
		logger.info("[" + reqID + "]@@"+ "[***封终端设备***] && [业务处理耗时(ms)]:" + (System.currentTimeMillis() - startTime));
		return ResponseTool.returnSuccess(null);
	}
	
	/**
	 * 创建对象
	 * @param forceOutDevice
	 * @return
	 */
	private TerminalForbid createTerminalForbid(ForceOutDevice forceOutDevice , String reqId){
		TerminalForbid terminalForbid = new TerminalForbid();
		if(forceOutDevice.getIp() != null){
			terminalForbid.setIp(forceOutDevice.getIp());
		}
		if(forceOutDevice.getMac() != null){
			terminalForbid.setMac(forceOutDevice.getMac());
		}
		if(forceOutDevice.getHdNum() != null){
			terminalForbid.setDisksn(forceOutDevice.getHdNum());
		}
		terminalForbid.setReqId(reqId);
		return terminalForbid;
	}
	
	/**
	 * 数据校验
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	private ForceOutDevice checkData(Object object) throws Exception{
		ForceOutDevice forceOutDevice = (ForceOutDevice)object;
		if(forceOutDevice == null){
			throw new Exception("对象转换失败！！！");
		}
		boolean tag = false;
		if(forceOutDevice.getIp() != null){
			tag = true;
		}
		if(forceOutDevice.getMac() != null){
			tag = true;
		}
		if(forceOutDevice.getHdNum() != null){
			tag = true;
		}
		if(!tag){
			throw new Exception("没有输入需要封的终端条件！！！");
		}
		return forceOutDevice;
	}

}
