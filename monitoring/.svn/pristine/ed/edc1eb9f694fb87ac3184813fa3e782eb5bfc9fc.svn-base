package com.springapp.mvc.user;

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
@RequestMapping("/")
@SuppressWarnings({ "rawtypes", "unchecked","restriction" })
public class UserController {
	@Resource(name = "addUserServiceImpl")
	private WebServerInf webServerInf;
	@Resource(name = "modifyUserInfoServiceImpl")
	private WebServerInf modifyUserInfo;
	@Resource(name = "queryUserByIdServiceImpl")
	private WebServerInf queryUserInfoById;
	@Resource(name = "queryUsersServiceImpl")
	private WebServerInf queryUserInfo;

	@RequestMapping(value = "homepage",method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("message", "Hello world!");
		return "homepage";
	}

	@RequestMapping(value = "/aaa/newpage1", method = RequestMethod.GET)
	public String newpagee(ModelMap model) {
		model.addAttribute("message", "aaaaaaa!");
		return "newpage";
	}

	@RequestMapping(value = "common/innerpage", method = RequestMethod.GET)
	public String innerpage(ModelMap model) {
		model.addAttribute("message", "Hello world!");
		return "newpage";
	}

	@RequestMapping(value = "userList", method = RequestMethod.GET)
	public String userList(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return "userList";
	}

	/**
	 * 用户列表查询
	 * 
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "userShow", method = RequestMethod.POST)
	public void userShow(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		Map resultMap = null;
		try {
			Map map = (Map) JsonUtil.getObjectFromJson(request.getParameter("reqData"), Map.class);
			resultMap = queryUserInfo.handler(map);
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				Map secReslutMap = (Map) resultMap.get("resData");
				dataMap.put("result", secReslutMap.get("users"));
				dataMap.put("dataSum",secReslutMap.get("dataSum"));
				JsonUtil.WriteJson("userList", dataMap, request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("queryUser", dataMap, request, response);
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * 查询用户信息
	 * 
	 * @param data
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "queryUser", method = RequestMethod.POST)
	public void queryUser(HttpServletRequest request, HttpServletResponse response) {
		Map resultMap = null;
		try {
			Map map = (Map) JsonUtil.getObjectFromJson(request.getParameter("reqData"), Map.class);
			resultMap = queryUserInfoById.handler(map);
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				dataMap.put("result", resultMap.get("resData"));
				JsonUtil.WriteJson("queryUser", dataMap, request, response);
				
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("queryUser", dataMap, request, response);
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * 按条件插叙用户
	 * @param request
	 * @param response
	 */
	
	@RequestMapping(value = "queryUsers", method = RequestMethod.POST)
	public void queryUsers(HttpServletRequest request, HttpServletResponse response) {
		Map resultMap = null;
		try {
			Map map = (Map) JsonUtil.getObjectFromJson(request.getParameter("reqData"), Map.class);
			resultMap = queryUserInfo.handler(map);
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				Map secReslutMap = (Map) resultMap.get("resData");
				dataMap.put("result", secReslutMap.get("users"));
				dataMap.put("dataSum",secReslutMap.get("dataSum"));
				JsonUtil.WriteJson("queryUser", dataMap, request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("queryUser", dataMap, request, response);
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * 新增用户
	 * 
	 * @param data
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "addUser", method = RequestMethod.POST)
	public void addUser(String data, HttpServletRequest request, HttpServletResponse response) {
		Map resultMap = null;
		try {
			Map map = (Map) JsonUtil.getObjectFromJson(request.getParameter("reqData"), Map.class);
			resultMap = webServerInf.handler(map);
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			dataMap.put("result", ResultCode.getResultMessage(flag));
			JsonUtil.WriteJson("addUser", dataMap, request, response);
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
	@RequestMapping(value = "updateUser", method = RequestMethod.POST)
	public void updateUser( HttpServletRequest request, HttpServletResponse response) {
		Map resultMap = null;
		try {
			Map map = (Map) JsonUtil.getObjectFromJson(request.getParameter("reqData"), Map.class);
			resultMap = modifyUserInfo.handler(map);
			Map dataMap = new HashMap();
			dataMap.put("result", ResultCode.getResultMessage(resultMap.get("resultCode").toString()));
			JsonUtil.WriteJson("userList", dataMap, request, response);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * 删除用户信息
	 * 
	 * @param data
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "delUser", method = RequestMethod.POST)
	public void delUser(String data, HttpServletRequest request, HttpServletResponse response) {
		//modifyUserInfo
		Map resultMap = null;
		try {
			Map map = (Map) JsonUtil.getObjectFromJson(request.getParameter("reqData"), Map.class);
			resultMap = modifyUserInfo.handler(map);
			Map dataMap = new HashMap();
			dataMap.put("result", ResultCode.getResultMessage(resultMap.get("resultCode").toString()));
			JsonUtil.WriteJson("userList", dataMap, request, response);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}