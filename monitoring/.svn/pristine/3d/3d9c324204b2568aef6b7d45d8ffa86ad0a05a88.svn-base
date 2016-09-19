package com.springapp.mvc.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springapp.util.JsonUtil;
import com.springapp.util.RandomCode;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.webService.WebServerInf;

@Controller
@RequestMapping("/")

@SuppressWarnings({ "rawtypes", "unchecked", "restriction" })
public class LoginController {

	@Resource(name = "loginServiceImpl")
	private WebServerInf webServerInf;

	@Resource(name = "resetPasswordServiceImpl")
	private WebServerInf resetPasswordServiceImpl;
	
	//resetPasswordServiceImpl
	/**
	 * 加载用户登录页
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String login(ModelMap model) {
		return "login";
	}

	/**
	 * 用户登录
	 */
	@RequestMapping(value = "userLogin", method = RequestMethod.POST)
	public void userLogin(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		Map resultMap = null;
		Map dataMap = new HashMap();
		try {
			Map map = (Map) JsonUtil.getObjectFromJson(request.getParameter("reqData"), Map.class);
			HttpSession session = request.getSession();
			String rand=(String) session.getAttribute("imageCaptcha");
			if (rand.toLowerCase().equals(map.get("rand")==null?"":map.get("rand").toString().toLowerCase())) {
				resultMap = webServerInf.handler(map);

				String flag = resultMap.get("resultCode").toString();
				if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
					dataMap.put("result", "0");
					JSONObject jsonObject = new JSONObject(request.getParameter("reqData"));
					String userName = jsonObject.getString("loginName");
					session.setAttribute("userName", userName);
					session.setMaxInactiveInterval(30 * 60);
				} else {
					dataMap.put("result", "1");
				}
				JsonUtil.WriteJson("userLogin", dataMap, request, response);
				session.removeAttribute("imageCaptcha");
			}else {
				dataMap.put("result", "2");
				JsonUtil.WriteJson("userLogin", dataMap, request, response);
				return;
			}
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/***
	 * 用户退出
	 */

	@RequestMapping(value = "userLogout", method = RequestMethod.POST)
	public String userLogout(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return "login";
	}

	/**
	 * 调用生成验证码
	 */

	@RequestMapping(value = "generate", method = RequestMethod.GET)
	public void generateCode(HttpServletRequest request, HttpServletResponse response) {
		// 验证码图片宽度，单位像素
		int width = 120;
		// 验证码图片高度，单位像素
		int height = 40;
		// 验证码图片格式
		String format = "png";
		// 验证码字符长度
		int len = 4;

		// 设置图片格式
		response.setContentType("image/" + format);

		// 禁止浏览器缓存图片
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		String code = RandomCode.randomString(len);

		// 把图片输出到response输出流
		try {
			HttpSession session = request.getSession();
			session.setAttribute("imageCaptcha", code);
			RandomCode.write(code, width, height, response.getOutputStream(), format);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * 用户密码修改加载页面
	 */
	@RequestMapping(value = "modifyPasswd",method = RequestMethod.GET)
	public String modifyPasswd(ModelMap model) {
		return "/user/modifyPasswd";
	}
	
	/**
	 * 密码修改
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "updatePasswd", method = RequestMethod.POST)
	public void updatePasswd(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		Map resultMap = null;
		try {
			Map map = (Map) JsonUtil.getObjectFromJson(request.getParameter("reqData"), Map.class);
			if (map.get("type").equals("1")) {
				map.put("userName",  request.getSession().getAttribute("userName"));
			}
			resultMap = resetPasswordServiceImpl.handler(map);
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				dataMap.put("flag", "0");
				JsonUtil.WriteJson("updatePasswd", dataMap, request, response);
			} else {
				dataMap.put("flag", "1");
				JsonUtil.WriteJson("updatePasswd", dataMap, request, response);
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}

	
	
	
	
	
	
	
	
	
	
	

}
