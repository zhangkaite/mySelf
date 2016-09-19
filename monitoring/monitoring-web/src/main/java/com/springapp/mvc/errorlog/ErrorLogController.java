package com.springapp.mvc.errorlog;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springapp.util.JsonUtil;
import com.springapp.util.ResultCode;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.webService.WebServerInf;

@Controller
@SuppressWarnings({ "rawtypes", "unchecked", "restriction" })
@RequestMapping("/errorLog")
public class ErrorLogController {

	//queryAlertRecordServiceImpl
	@Resource(name = "queryAlertRecordServiceImpl")
	private WebServerInf queryAlertRecordServiceImpl;
	
	/**
	 * 加载报警日志页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {

		return "/error/errorList";
	}

	
	/**
	 * 查询报警日志历史
	 */

	@RequestMapping(value = "/queryError", method = RequestMethod.POST)
	public void queryBurglar(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		Map resultMap = null;
		try {
			Map map = (Map) JsonUtil.getObjectFromJson(request.getParameter("reqData"), Map.class);
			resultMap = queryAlertRecordServiceImpl.handler(map);
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				Map secReslutMap = (Map) resultMap.get("resData");
				dataMap.put("result", secReslutMap==null?"":secReslutMap.get("alerts"));
				dataMap.put("dataSum",secReslutMap==null?0:secReslutMap.get("dataSum"));
				JsonUtil.WriteJson("userList", dataMap, request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				dataMap.put("dataSum", 0);
				JsonUtil.WriteJson("queryBurglar", dataMap, request, response);
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	

	
	
	

	

}
