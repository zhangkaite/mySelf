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

import com.ttmv.service.input.impl.control.InOverdueForbiddenRoomServiceImpl;
import com.ttmv.web.controller.util.JsonUtil;

/***
 * 
 * @author zkt 频道(房间)冻结
 */
@Controller
public class InOverdueForbiddenRoomController {
	private static Logger logger = LogManager.getLogger(InOverdueForbiddenRoomController.class);

	@Autowired
	private InOverdueForbiddenRoomServiceImpl inOverdueForbiddenRoomServiceImpl;

	@RequestMapping(value = "inOverdueForbiddenRoom", method = RequestMethod.POST)
	public void printWelcome(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获取请求数据
			String data = request.getParameter("data");
			logger.info("[--->>> 频道(房间)冻结收到请求数据:] " + data);
			// 请求数据转map
			Map reqMap = (Map) JsonUtil.getObjectFromJson(data, Map.class);
			response.getWriter().print(JsonUtil.getObjectToJson(inOverdueForbiddenRoomServiceImpl.execute(reqMap)));

		} catch (Exception e) {
			response.setStatus(405);
			logger.error("接收信息程序出现错误。", e);
		}
	}
}
