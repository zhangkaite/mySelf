package com.ttmv.web.controller.control;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ttmv.service.OverdueService;
import com.ttmv.web.controller.util.JsonUtil;


/***
 * 金色弹窗 开始提醒、结束提醒、删除数据三种业务
 * @author kate
 *
 */
@SuppressWarnings("rawtypes")
@Controller
public class GlodenWindowController {
	private static Logger logger = LogManager.getLogger(GlodenWindowController.class);
	
	@Autowired
	private OverdueService inOverdueStartCMPServiceImpl;
	@Autowired
	private OverdueService  inOverdueEndCMPServiceImpl;
	@Autowired
	private OverdueService inOverdueCloseCMPServiceImpl;

	@RequestMapping(value="inOverdueStartCMP", method = RequestMethod.POST)
	public void inOverdueStartCMP(HttpServletRequest request, HttpServletResponse response) {
		try {
			//获取请求数据
			String data = request.getParameter("data");
			logger.info("[--->>> 金色弹窗开始提醒收到请求数据:] " + data);
			//请求数据转map
			Map reqMap = (Map) JsonUtil.getObjectFromJson(data, Map.class);
			String resDate = JsonUtil.getObjectToJson(inOverdueStartCMPServiceImpl.execute(reqMap));
			logger.info("金色弹窗开始提醒返回数据："+resDate);
			response.getWriter().print(resDate);
		} catch (Exception e) {
			response.setStatus(405);
            logger.error("金色弹窗开始提醒接收信息程序出现错误。",e);
		}
	}
	
	
	
	@RequestMapping(value="inOverdueEndCMP", method = RequestMethod.POST)
	public void inOverdueEndCMP(HttpServletRequest request, HttpServletResponse response) {
		try {
			//获取请求数据
			String data = request.getParameter("data");
			logger.info("[--->>> 金色弹窗结束提醒收到请求数据:] " + data);
			//请求数据转map
			Map reqMap = (Map) JsonUtil.getObjectFromJson(data, Map.class);
			String resDate = JsonUtil.getObjectToJson(inOverdueEndCMPServiceImpl.execute(reqMap));
			logger.info("金色弹窗结束提醒返回数据："+resDate);
			response.getWriter().print(resDate);
		} catch (Exception e) {
			response.setStatus(405);
            logger.error("金色弹窗结束提醒接收信息程序出现错误。",e);
		}
	}
	
	
	@RequestMapping(value="closeCMP", method = RequestMethod.POST)
	public void closeCMP(HttpServletRequest request, HttpServletResponse response) {
		try {
			//获取请求数据
			String data = request.getParameter("data");
			logger.info("[--->>> 关闭金色弹窗（开始/结束）提醒收到请求数据:] " + data);
			//请求数据转map
			Map reqMap = (Map) JsonUtil.getObjectFromJson(data, Map.class);
			String resDate = JsonUtil.getObjectToJson(inOverdueCloseCMPServiceImpl.execute(reqMap));
			logger.info("关闭金色弹窗（开始/结束）提醒返回数据："+resDate);
			response.getWriter().print(resDate);
		} catch (Exception e) {
			response.setStatus(405);
            logger.error("关闭金色弹窗（开始/结束）提醒接收信息程序出现错误。",e);
		}
	}
	
	
	
	
	
}
