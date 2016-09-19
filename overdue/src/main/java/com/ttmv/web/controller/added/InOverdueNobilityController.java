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

import com.ttmv.service.input.impl.added.InOverdueNobilityServiceImpl;
import com.ttmv.web.controller.util.JsonUtil;


/**
 * 开通、续费爵位命令 上报到期处理服务器
 * @author Damon
 * @time 2015年11月12日15:09:46
 */
@SuppressWarnings("rawtypes")
@Controller
public class InOverdueNobilityController {
	private static Logger logger = LogManager.getLogger(InOverdueNobilityController.class);
	
	@Autowired
	private InOverdueNobilityServiceImpl inOverdueNobilityServiceImpl;
	
	
	@RequestMapping(value="inOverdueNobility", method = RequestMethod.POST)
	public void printWelcome(HttpServletRequest request, HttpServletResponse response) {
		try {
			//获取请求数据
			String data = request.getParameter("data");
			logger.info("[--->>>爵位 收到请求数据:] " + data);
			//请求数据转map
			Map reqMap = (Map) JsonUtil.getObjectFromJson(data, Map.class);
			response.getWriter().print(JsonUtil.getObjectToJson(inOverdueNobilityServiceImpl.execute(reqMap)));
		} catch (Exception e) {
			response.setStatus(405);
            logger.error("接收信息程序出现错误。",e);
		}
	}

}
