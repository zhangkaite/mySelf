package com.ttmv.web.controller.added.manage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ttmv.service.input.impl.added.ManageGoodTTNumServiceImpl;
import com.ttmv.web.controller.util.JsonUtil;

/***
 * vip绑定靓号
 * @author zkt
 *
 */
@Controller
public class ManageGoodTTNumController {

	private static Logger logger = LogManager.getLogger(ManageGoodTTNumController.class);
	@Autowired
	private ManageGoodTTNumServiceImpl manageGoodTTNumServiceImpl;
	@RequestMapping(value="manageGoodTTNum", method = RequestMethod.POST)
	public void printWelcome(HttpServletRequest request, HttpServletResponse response) {
		try {
			//获取请求数据
			String data = request.getParameter("data");
			logger.info("[--->>> 靓号绑定收到请求数据:] " + data);
			//请求数据转map
			Map reqMap = (Map) JsonUtil.getObjectFromJson(data, Map.class);
			response.getWriter().print(JsonUtil.getObjectToJson(manageGoodTTNumServiceImpl.execute(reqMap)));
		} catch (Exception e) {
			response.setStatus(405);
            logger.error("接收信息程序出现错误。",e);
		}
	}

}
