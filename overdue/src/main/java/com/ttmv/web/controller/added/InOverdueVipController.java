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

import com.ttmv.service.input.impl.added.InOverdueVipServiceImpl;
import com.ttmv.web.controller.util.JsonUtil;

/***
 * 
 * ClassName: InOverdueVipController <br/>
 * Function: TODO 开通、续费会员命令 上报到期处理服务器 <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2015年11月20日 下午2:19:55 <br/>
 *
 * @author zkt
 * @since JDK 1.6
 */
@SuppressWarnings("rawtypes")
@Controller
public class InOverdueVipController {
	private static Logger logger = LogManager.getLogger(InOverdueVipController.class);
	
	@Autowired
	private InOverdueVipServiceImpl inOverdueVipServiceImpl;
	
	@RequestMapping(value="inOverdueVip", method = RequestMethod.POST)
	public void printWelcome(HttpServletRequest request, HttpServletResponse response) {
		try {
			//获取请求数据
			String data = request.getParameter("data");
			logger.info("[--->>> vip收到请求数据:] " + data);
			//请求数据转map
			Map reqMap = (Map) JsonUtil.getObjectFromJson(data, Map.class);
			String resDate = JsonUtil.getObjectToJson(inOverdueVipServiceImpl.execute(reqMap));
			logger.info("返回结果:"+resDate);
			response.getWriter().print(resDate);
		} catch (Exception e) {
			response.setStatus(405);
            logger.error("接收信息程序出现错误。",e);
		}
	}

}
