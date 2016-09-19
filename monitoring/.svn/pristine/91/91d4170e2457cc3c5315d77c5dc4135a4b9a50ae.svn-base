package com.springapp.mvc.burglar;

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
@SuppressWarnings({ "rawtypes", "unchecked" })
@RequestMapping("/burglar")
public class BurglarController {


	@Resource(name = "addAlerterServiceImpl")
	private WebServerInf webServerInf;

	
	@Resource(name = "queryAlertersServiceImpl")
	private WebServerInf queryAlertersServiceImpl;
	
	@Resource(name = "queryAlerterInfoByIdServiceImpl")
	private WebServerInf queryAlerterInfoByIdServiceImpl;
	
	@Resource(name = "modifyAlerterInfoServiceImpl")
	private WebServerInf modifyAlerterInfoServiceImpl;
	
	
	@Resource(name = "deleteAlerterServiceImpl")
	private WebServerInf deleteAlerterServiceImpl;
	
	
	/**
	 * 报警器配置配置列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {

		return "/burglar/burglarTemplate";
	}

	@RequestMapping(value = "test", method = {RequestMethod.GET,RequestMethod.POST})
	public String currentPage(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return "currentPage";
	}

	
	
	/**
	 * 警报配置查询
	 */

	@RequestMapping(value = "/queryBurglar", method = RequestMethod.POST)
	public void queryBurglar(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		Map resultMap = null;
		try {
			Map map = (Map) JsonUtil.getObjectFromJson(request.getParameter("reqData"), Map.class);
			resultMap = queryAlertersServiceImpl.handler(map);
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				Map secReslutMap = (Map) resultMap.get("resData");
				dataMap.put("result", secReslutMap.get("alerters"));
				dataMap.put("dataSum",secReslutMap.get("dataSum"));
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
	
	@RequestMapping(value = "/queryBurglarId", method = RequestMethod.POST)
	public void queryBurglarId( HttpServletRequest request, HttpServletResponse response) {
		Map resultMap = null;
		try {
			Map map = (Map) JsonUtil.getObjectFromJson(request.getParameter("reqData"), Map.class);
			resultMap = queryAlerterInfoByIdServiceImpl.handler(map);
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				dataMap.put("result", resultMap.get("resData"));
				JsonUtil.WriteJson("queryUser", dataMap, request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("queryBurglarId", dataMap, request, response);
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	

	/**
	 * 警报配置新增
	 */

	@RequestMapping(value = "/addBurglar", method = RequestMethod.POST)
	public void addBurglar(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		Map resultMap = null;
		try {
			Map map = (Map) JsonUtil.getObjectFromJson(request.getParameter("reqData"), Map.class);
			resultMap = webServerInf.handler(map);
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			dataMap.put("result", ResultCode.getResultMessage(flag));
			JsonUtil.WriteJson("addBurglar", dataMap, request, response);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	
	/***
	 * 更新警报配置
	 */
	
	@RequestMapping(value = "/updateBurglar", method = RequestMethod.POST)
	public void updateBurglar(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		Map resultMap = null;
		try {
			Map map = (Map) JsonUtil.getObjectFromJson(request.getParameter("reqData"), Map.class);
			resultMap = modifyAlerterInfoServiceImpl.handler(map);
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			dataMap.put("result", ResultCode.getResultMessage(flag));
			JsonUtil.WriteJson("updateBurglar", dataMap, request, response);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	

	/**
	 * 警报配置删除
	 */

	@RequestMapping(value = "/delBurglar", method = RequestMethod.POST)
	public void delBurglar(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		Map resultMap = null;
		try {
			Map map = (Map) JsonUtil.getObjectFromJson(request.getParameter("reqData"), Map.class);
			resultMap = deleteAlerterServiceImpl.handler(map);
			Map dataMap = new HashMap();
			dataMap.put("result", ResultCode.getResultMessage(resultMap.get("resultCode").toString()));
			JsonUtil.WriteJson("delBurglar", dataMap, request, response);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
