package com.springapp.mvc.ipfilter;

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
@RequestMapping("/ipFilter")
public class IpFilterController {


	@Resource(name = "queryWhitelistServiceImpl")
	private WebServerInf queryWhitelistServiceImpl;

	
	@Resource(name = "deleteWhitelistServiceImpl")
	private WebServerInf deleteWhitelistServiceImpl;
	
	@Resource(name = "addWhitelistServiceImpl")
	private WebServerInf addWhitelistServiceImpl;
	
	@Resource(name = "modifyWhitelistServiceImpl")
	private WebServerInf modifyWhitelistServiceImpl;
	
	
	/**
	 * ip列表页面加载
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {

		return "/ipfilter/ipFilter";
	}

	
	/**
	 * 警报配置查询
	 */

	@RequestMapping(value = "/queryWhitelist", method = RequestMethod.POST)
	public void queryBurglar(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		Map resultMap = null;
		try {
			Map map = (Map) JsonUtil.getObjectFromJson(request.getParameter("reqData"), Map.class);
			resultMap = queryWhitelistServiceImpl.handler(map);
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				Map secReslutMap = (Map) resultMap.get("resData");
				dataMap.put("result", secReslutMap.get("whiteList"));
				dataMap.put("dataSum",secReslutMap.get("dataSum"));
				JsonUtil.WriteJson("queryWhitelist", dataMap, request, response);
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
	
	

	/**
	 * ip白名单新增
	 */

	@RequestMapping(value = "/addip", method = RequestMethod.POST)
	public void addBurglar(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		Map resultMap = null;
		try {
			Map map = (Map) JsonUtil.getObjectFromJson(request.getParameter("reqData"), Map.class);
			resultMap = addWhitelistServiceImpl.handler(map);
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			dataMap.put("result", ResultCode.getResultMessage(flag));
			JsonUtil.WriteJson("addBurglar", dataMap, request, response);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	
	/**
	 * 更新用户信息
	 * 
	 * @param data
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "updateIp", method = RequestMethod.POST)
	public void updateUser( HttpServletRequest request, HttpServletResponse response) {
		Map resultMap = null;
		try {
			Map map = (Map) JsonUtil.getObjectFromJson(request.getParameter("reqData"), Map.class);
			resultMap = modifyWhitelistServiceImpl.handler(map);
			Map dataMap = new HashMap();
			dataMap.put("result", ResultCode.getResultMessage(resultMap.get("resultCode").toString()));
			JsonUtil.WriteJson("userList", dataMap, request, response);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	
	

	/**
	 * ip白名单删除删除
	 */

	@RequestMapping(value = "/delip", method = RequestMethod.POST)
	public void delBurglar(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		Map resultMap = null;
		try {
			Map map = (Map) JsonUtil.getObjectFromJson(request.getParameter("reqData"), Map.class);
			resultMap = deleteWhitelistServiceImpl.handler(map);
			Map dataMap = new HashMap();
			dataMap.put("result", ResultCode.getResultMessage(resultMap.get("resultCode").toString()));
			JsonUtil.WriteJson("delip", dataMap, request, response);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
