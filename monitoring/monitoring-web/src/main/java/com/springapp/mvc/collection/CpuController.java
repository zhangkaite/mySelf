package com.springapp.mvc.collection;

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

/**
 * 查看redis详情
 * 
 * @author zkt
 *
 */
@Controller
@RequestMapping("/collection")
public class CpuController {
	@SuppressWarnings("restriction")
	@Resource(name = "queryCpuInfoServiceImpl")
	private WebServerInf queryCpuInfoServiceImpl;
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {

		return "/collection/cpuList";
	}

	/**
	 * 根据配置文件查询redis的详情，支持多个redis查询
	 * 
	 * @param request
	 * @param response
	 */

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/queryCpuList", method = RequestMethod.POST)
	public void queryRedisDetailList(HttpServletRequest request, HttpServletResponse response) {
		
		Map resultMap = null;
		try {
			Map map = (Map) JsonUtil.getObjectFromJson(request.getParameter("reqData"), Map.class);
			resultMap = queryCpuInfoServiceImpl.handler(map);
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				Map secReslutMap = (Map) resultMap.get("resData");
				dataMap.put("result", secReslutMap.get("cpuList"));
				dataMap.put("dataSum",secReslutMap.get("dataSum"));
				JsonUtil.WriteJson("queryRedisDetailList", dataMap, request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("queryUser", dataMap, request, response);
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
