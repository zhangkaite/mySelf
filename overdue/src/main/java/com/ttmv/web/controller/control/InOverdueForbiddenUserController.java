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

import com.ttmv.service.input.impl.control.InOverdueForbiddenUserServiceImpl;
import com.ttmv.web.controller.util.JsonUtil;


/**
 * 冻结用户命令 上报到期处理服务器 
 * @author Damon
 * @time 2015年11月17日10:22:50
 */
@SuppressWarnings("rawtypes")
@Controller
public class InOverdueForbiddenUserController {
	private static Logger logger = LogManager.getLogger(InOverdueForbiddenUserController.class);
	
	@Autowired
	private InOverdueForbiddenUserServiceImpl inOverdueForbiddenUserServiceImpl;

	@RequestMapping(value="inOverdueForbiddenUser", method = RequestMethod.POST)
	public void printWelcome(HttpServletRequest request, HttpServletResponse response) {
		try {
			//获取请求数据
			String data = request.getParameter("data");
			logger.info("[--->>> 冻结用户收到请求数据:] " + data);
			//请求数据转map
			Map reqMap = (Map) JsonUtil.getObjectFromJson(data, Map.class);
			String resDate = JsonUtil.getObjectToJson(inOverdueForbiddenUserServiceImpl.execute(reqMap));
			logger.info("返回数据："+resDate);
			response.getWriter().print(resDate);
		} catch (Exception e) {
			response.setStatus(405);
            logger.error("接收信息程序出现错误。",e);
		}
	}
	
}
