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
@RequestMapping("/otherService")
@SuppressWarnings({ "rawtypes", "unchecked", "restriction" })
public class OtherServiceController {

	@Resource(name = "querySSDServerDataServiceImpl")
	private ChartServiceInf querySSDServerDataServiceImpl;

	@Resource(name = "querySSDSelectedIpServiceImpl")
	private ChartServiceInf querySSDSelectedIpServiceImpl;
	// queryMNSelectedIpServiceImpl
	@Resource(name = "queryTDSelectedIpServiceImpl")
	private ChartServiceInf queryTDSelectedIpServiceImpl;

	@Resource(name = "querySSDSelectedPortServiceImpl")
	private ChartServiceInf querySSDSelectedPortServiceImpl;

	// queryMNSelectedPortServiceImpl
	@Resource(name = "queryTDSelectedPortServiceImpl")
	private ChartServiceInf queryTDSelectedPortServiceImpl;
	
	//<!-- 截图服务：预置列表查询 -->
	@Resource(name = "queryListByDateSSDServiceImpl")
	private ChartServiceInf queryListByDateSSDServiceImpl;
    //<!-- 转码服务：预置列表查询 --> queryListTDServiceImpl
	@Resource(name = "queryListByDateTDServiceImpl")
	private ChartServiceInf queryListByDateTDServiceImpl;
	
	/**
	 * 查询子系统依赖
	 */
	@Resource(name = "queryServerSubSysinfoServiceImpl")
	private QueryServerSubSysinfoServiceImpl queryServerSubSysinfoServiceImpl;

	@RequestMapping(value = "/mfChart", method = RequestMethod.GET)
	public String userList(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return "/charts/otherService";
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
			resultMap = querySSDServerDataServiceImpl.handler(map);
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				JsonUtil.WriteJson("queryThreshold", resultMap.get("resData"), request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("other/queryThreshold", dataMap, request, response);
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 初始化其他服务子系统下拉菜单
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
			map.put("sys_type", SubSysConstant.OT);
			resultMap = queryServerSubSysinfoServiceImpl.handler(map);
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				List ls = (List) resultMap.get("resData");
				JsonUtil.WriteJson("other/initFisData", ls, request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("other/initFisData", dataMap, request, response);
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 初始化其他服务子系统ip下拉菜单
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
			//截图服务
			if (server_type.equals(SubSysConstant.OT_PIC)) {
				resultMap = querySSDSelectedIpServiceImpl.handler(map);
			} else {
				resultMap = queryTDSelectedIpServiceImpl.handler(map);
			}

			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				List ls = (List) resultMap.get("resData");
				JsonUtil.WriteJson("other/initFisData", ls, request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("other/initFisData", dataMap, request, response);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 初始化其他服务子系统ip对应的端口下拉菜单
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
			if (server_type.equals(SubSysConstant.OT_PIC)) {
				resultMap = querySSDSelectedPortServiceImpl.handler(map);
			} else {
				resultMap = queryTDSelectedPortServiceImpl.handler(map);
			}
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				List ls = (List) resultMap.get("resData");
				JsonUtil.WriteJson("other/initSecData", ls, request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("other/initSecData", dataMap, request, response);
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
		String interval=request.getParameter("interval");
		Date date = new Date(Long.parseLong(request.getParameter("time")));
		Map resultMap = null;
		try {
			Map map = new HashMap();
			map.put("ip", ip);
			map.put("server_type", server_type);
			map.put("port", port);
			map.put("time", date);
			map.put("interval", interval);
			if (server_type.equals(SubSysConstant.OT_PIC)) {
				resultMap = queryListByDateSSDServiceImpl.handler(map);
			} else {
				resultMap = queryListByDateTDServiceImpl.handler(map);
			}
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				List ls = (List) resultMap.get("resData");
				JsonUtil.WriteJson("other/queryThityData", ls, request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("other/queryThityData", dataMap, request, response);
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	

}
