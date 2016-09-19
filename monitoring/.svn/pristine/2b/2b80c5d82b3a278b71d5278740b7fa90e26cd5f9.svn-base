package com.springapp.mvc.threshold;

import java.util.HashMap;
import java.util.List;
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
@RequestMapping("/threshold")
public class ThresholdController {
	
	
	@Resource(name = "queryThresholdByTypeServiceImpl")
	private WebServerInf queryThresholdByTypeServiceImpl;
	
   //modifyThresholdServiceImpl
	
	@Resource(name = "modifyThresholdServiceImpl")
	private WebServerInf modifyThresholdServiceImpl;
	
	//queryAllThresholdServiceImpl
	@Resource(name = "queryAllThresholdServiceImpl")
	private WebServerInf queryAllThresholdServiceImpl;
	/**
	 * 阀值配置配置列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		return "/threshold/threshold";
	}
	/**
	 * 根据子系统类型查询对应的阀值配置
	 */
	@RequestMapping(value = "/queryThreshold", method = RequestMethod.POST)
	public void queryThreshold(ModelMap model, HttpServletRequest request, HttpServletResponse response,String reqData) {
		Map resultMap = null;
		try {
			Map map = new HashMap();
			//map.put("pageSize", "20");
			map.put("threshold_type", reqData);
			resultMap = queryThresholdByTypeServiceImpl.handler(map);
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				Map secReslutMap = (Map) resultMap.get("resData");
				dataMap.put("result", secReslutMap);
				JsonUtil.WriteJson("queryThreshold", dataMap, request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("queryThreshold", dataMap, request, response);
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	
	
	/***
	 * 更新阀值配置
	 */

	@RequestMapping(value = "/updateThreshold", method = RequestMethod.POST)
	public void updateThreshold(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		Map resultMap = null;
		try {
			Map map = (Map) JsonUtil.getObjectFromJson(request.getParameter("reqData"), Map.class);
			resultMap = modifyThresholdServiceImpl.handler(map);
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			dataMap.put("result", ResultCode.getResultMessage(flag));
			JsonUtil.WriteJson("updateThreshold", dataMap, request, response);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	
	/***
	 * 子系统服务下拉单
	 */
	
	@RequestMapping(value = "/querySysType", method = RequestMethod.POST)
	public void querySysType(ModelMap model, HttpServletRequest request, HttpServletResponse response,String reqData) {
		Map resultMap = null;
		try {
			Map map = new HashMap();
			//map.put("pageSize", "20");
			map.put("threshold_type", reqData);
			resultMap = queryAllThresholdServiceImpl.handler(map);
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				List ls = (List) resultMap.get("resData");
				JsonUtil.WriteJson("querySysType", ls, request, response);
			} 

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	

	

}
