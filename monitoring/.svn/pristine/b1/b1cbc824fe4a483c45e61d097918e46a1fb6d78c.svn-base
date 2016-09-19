package com.springapp.mvc.chart;

import java.util.Date;
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

import com.springapp.mvc.common.SubSysConstant;
import com.springapp.util.JsonUtil;
import com.springapp.util.ResultCode;
import com.ttmv.monitoring.chartService.impl.ChartServiceInf;
import com.ttmv.monitoring.chartService.impl.QueryServerSubSysinfoServiceImpl;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;

@Controller
@RequestMapping("/php")
@SuppressWarnings({ "rawtypes", "unchecked", "restriction" })
public class PhpForwardController {

	// 直播
	@Resource(name = "queryPhpServerDataServiceImpl")
	private ChartServiceInf queryPhpServerDataServiceImpl;
	@Resource(name = "queryPHPSelectedIpServiceImpl")
	private ChartServiceInf queryPHPSelectedIpServiceImpl;
	@Resource(name = "queryPHPSelectedPortServiceImpl")
	private ChartServiceInf queryPHPSelectedPortServiceImpl;
	@Resource(name = "queryListByDatePHPServiceImpl")
	private ChartServiceInf queryListByDatePHPServiceImpl;
	// 点播
	@Resource(name = "queryVideoServerDataServiceImpl")
	private ChartServiceInf queryVideoServerDataServiceImpl;
	@Resource(name = "queryVideoSelectedIpServiceImpl")
	private ChartServiceInf queryVideoSelectedIpServiceImpl;
	@Resource(name = "queryVideoSelectedPortServiceImpl")
	private ChartServiceInf queryVideoSelectedPortServiceImpl;
	@Resource(name = "queryListByDateVideoServiceImpl")
	private ChartServiceInf queryListByDateVideoServiceImpl;
	// 管控
	@Resource(name = "queryPhpManagerServerDataServiceImpl")
	private ChartServiceInf queryPhpManagerServerDataServiceImpl;
	@Resource(name = "queryPhpManagerSelectedIpServiceImpl")
	private ChartServiceInf queryPhpManagerSelectedIpServiceImpl;
	@Resource(name = "queryPhpManagerSelectedPortServiceImpl")
	private ChartServiceInf queryPhpManagerSelectedPortServiceImpl;
	@Resource(name = "queryListByDatePhpManagerServiceImpl")
	private ChartServiceInf queryListByDatePhpManagerServiceImpl;

	@Resource(name = "queryServerSubSysinfoServiceImpl")
	private QueryServerSubSysinfoServiceImpl queryServerSubSysinfoServiceImpl;

	// queryListMediaForwardDataServiceImpl

	@RequestMapping(value = "/mfChart", method = RequestMethod.GET)
	public String userList(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return "/charts/phpForward";
	}

	/***
	 * 查询媒体转发当前最新节点信息
	 * 
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryData", method = RequestMethod.POST)
	public void queryThreshold(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String fisData = request.getParameter("fisData");
		String secData = request.getParameter("secData");
		String server_type = request.getParameter("server_type");
		Date date = new Date(Long.parseLong(request.getParameter("time")));
		String previousId = request.getParameter("previousId");
		Map resultMap = null;
		try {
			Map map = new HashMap();
			map.put("ip", fisData);
			map.put("port", secData);
			map.put("server_type", server_type);
			map.put("time", date);
			map.put("previousId", previousId);
			if (server_type.equals(SubSysConstant.PHP_ZB)) {
				resultMap = queryPhpServerDataServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.PHP_DB)) {
				resultMap = queryVideoServerDataServiceImpl.handler(map);
			} else {
				resultMap = queryPhpManagerServerDataServiceImpl.handler(map);
			}

			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				JsonUtil.WriteJson("php/queryData", resultMap.get("resData"), request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("php/queryData", dataMap, request, response);
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * php子系统下拉菜单
	 * 
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/initFisData", method = RequestMethod.POST)
	public void initFisData(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		Map resultMap = null;
		try {
			Map map = new HashMap();
			map.put("sys_type", SubSysConstant.PHP);
			resultMap = queryServerSubSysinfoServiceImpl.handler(map);
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				List ls = (List) resultMap.get("resData");
				JsonUtil.WriteJson("php/initFisData", ls, request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("php/initFisData", dataMap, request, response);
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * php子系统ip下拉菜单
	 * 
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/initOneData", method = RequestMethod.POST)
	public void initOneData(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String server_type = request.getParameter("server_type");
		Map resultMap = null;
		try {
			Map map = new HashMap();
			map.put("server_type", server_type);
			if (server_type.equals(SubSysConstant.PHP_ZB)) {
				resultMap = queryPHPSelectedIpServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.PHP_DB)) {
				resultMap = queryVideoSelectedIpServiceImpl.handler(map);
			} else {
				resultMap = queryPhpManagerSelectedIpServiceImpl.handler(map);
			}

			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				List ls = (List) resultMap.get("resData");
				JsonUtil.WriteJson("php/initFisData", ls, request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("php/initFisData", dataMap, request, response);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * php子系统ip对应的端口下拉菜单
	 * 
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/initSecData", method = RequestMethod.POST)
	public void initSecData(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String ip = request.getParameter("reqData");
		String server_type = request.getParameter("server_type");
		Map resultMap = null;
		try {
			Map map = new HashMap();
			map.put("ip", ip);
			map.put("server_type", server_type);
			if (server_type.equals(SubSysConstant.PHP_ZB)) {
				resultMap = queryPHPSelectedPortServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.PHP_DB)) {
				resultMap = queryVideoSelectedPortServiceImpl.handler(map);
			} else {
				resultMap = queryPhpManagerSelectedPortServiceImpl.handler(map);
			}
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				List ls = (List) resultMap.get("resData");
				JsonUtil.WriteJson("php/initSecData", ls, request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("php/queryThreshold", dataMap, request, response);
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * 查询当前时间前三十分钟数据
	 */
	@RequestMapping(value = "/queryThityData", method = RequestMethod.POST)
	public void queryThityData(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String ip = request.getParameter("ip");
		String server_type = request.getParameter("server_type");
		String port = request.getParameter("port");
		Date date = new Date(Long.parseLong(request.getParameter("time")));
		String interval=request.getParameter("interval");
		Map resultMap = null;
		try {
			Map map = new HashMap();
			map.put("ip", ip);
			map.put("server_type", server_type);
			map.put("port", port);
			map.put("time", date);
			map.put("interval", interval);
			if (server_type.equals(SubSysConstant.PHP_ZB)) {
				resultMap = queryListByDatePHPServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.PHP_DB)) {
				resultMap = queryListByDateVideoServiceImpl.handler(map);
			} else {
				resultMap = queryListByDatePhpManagerServiceImpl.handler(map);
			}
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				List ls = (List) resultMap.get("resData");
				JsonUtil.WriteJson("php/queryThityData", ls, request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("php/queryThityData", dataMap, request, response);
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
