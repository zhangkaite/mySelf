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
import com.ttmv.service.input.impl.added.InOverdueGoodNumberServiceImpl;
import com.ttmv.web.controller.util.JsonUtil;
/**
 * 
 * @author zkt
 * 靓号开通、续费
 */
@SuppressWarnings("rawtypes")
@Controller
public class InOverdueGoodNumberController {
	private static Logger logger = LogManager.getLogger(InOverdueLuxuryCarController.class);
	@Autowired
	private OverdueService inOverdueGoodNumberServiceImpl;
	
	@Autowired
	private OverdueService inOverdueCloseGoodNumberServiceImpl;
	
	@RequestMapping(value="inOverdueGoodNumber", method = RequestMethod.POST)
	public void printWelcome(HttpServletRequest request, HttpServletResponse response) {
		try {
			//获取请求数据
			String data = request.getParameter("data");
			logger.info("[--->>> 靓号收到请求数据:] " + data);
			//请求数据转map
			Map reqMap = (Map) JsonUtil.getObjectFromJson(data, Map.class);
			response.getWriter().print(JsonUtil.getObjectToJson(inOverdueGoodNumberServiceImpl.execute(reqMap)));
		} catch (Exception e) {
			response.setStatus(405);
            logger.error("靓号接收信息程序出现错误。",e);
		}
	}
	
	
	
	@RequestMapping(value="closeGoodNumber", method = RequestMethod.POST)
	public void closeGoodNumber(HttpServletRequest request, HttpServletResponse response) {
		try {
			//获取请求数据
			String data = request.getParameter("data");
			logger.info("[--->>> 靓号到期提醒关闭收到请求数据:] " + data);
			//请求数据转map
			Map reqMap = (Map) JsonUtil.getObjectFromJson(data, Map.class);
			response.getWriter().print(JsonUtil.getObjectToJson(inOverdueCloseGoodNumberServiceImpl.execute(reqMap)));
		} catch (Exception e) {
			response.setStatus(405);
            logger.error("靓号到期提醒关闭接收信息程序出现错误。",e);
		}
	}
	
	
	
	
	
}
