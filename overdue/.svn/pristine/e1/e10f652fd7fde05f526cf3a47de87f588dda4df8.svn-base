package com.ttmv.web.controller.added;

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

/**
 * 
 * @author zkt
 * 豪车开通、续费请求
 */
@SuppressWarnings("rawtypes")
@Controller
public class InOverdueLuxuryCarController {
	private static Logger logger = LogManager.getLogger(InOverdueLuxuryCarController.class);
	@Autowired
	private OverdueService inOverdueLuxuryCarServiceImpl;
	@Autowired
	private OverdueService inOverdueCloseLuxuryCarServiceImpl;
	
	@RequestMapping(value="inOverdueLuxuryCar", method = RequestMethod.POST)
	public void printWelcome(HttpServletRequest request, HttpServletResponse response) {
		try {
			//获取请求数据
			String data = request.getParameter("data");
			logger.info("[--->>> 豪车收到请求数据:] " + data);
			//请求数据转map
			Map reqMap = (Map) JsonUtil.getObjectFromJson(data, Map.class);
			response.getWriter().print(JsonUtil.getObjectToJson(inOverdueLuxuryCarServiceImpl.execute(reqMap)));
		} catch (Exception e) {
			response.setStatus(405);
            logger.error("接收信息程序出现错误。",e);
		}
	}
	
	
	@RequestMapping(value="closeLuxuryCar", method = RequestMethod.POST)
	public void closeLuxuryCar(HttpServletRequest request, HttpServletResponse response) {
		try {
			//获取请求数据
			String data = request.getParameter("data");
			logger.info("[--->>> 豪车到期提醒关闭收到请求数据:] " + data);
			//请求数据转map
			Map reqMap = (Map) JsonUtil.getObjectFromJson(data, Map.class);
			response.getWriter().print(JsonUtil.getObjectToJson(inOverdueCloseLuxuryCarServiceImpl.execute(reqMap)));
		} catch (Exception e) {
			response.setStatus(405);
            logger.error("豪车到期提醒关闭接收信息程序出现错误。",e);
		}
	}
	
	
	
	
}
