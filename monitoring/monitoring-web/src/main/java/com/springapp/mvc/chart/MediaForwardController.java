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
@RequestMapping("/mf")
@SuppressWarnings({ "rawtypes", "unchecked", "restriction" })
public class MediaForwardController {

	@Resource(name = "queryMediaForwardDataServiceImpl")
	private ChartServiceInf queryMediaForwardDataServiceImpl;

	// queryMediaControlDataServiceImpl
	@Resource(name = "queryMediaControlDataServiceImpl")
	private ChartServiceInf queryMediaControlDataServiceImpl;
	@Resource(name = "querySelectedIpServiceImpl")
	private ChartServiceInf querySelectedIpServiceImpl;
	// queryMNSelectedIpServiceImpl
	@Resource(name = "queryMNSelectedIpServiceImpl")
	private ChartServiceInf queryMNSelectedIpServiceImpl;

	@Resource(name = "querySelectedPortServiceImpl")
	private ChartServiceInf querySelectedPortServiceImpl;

	// queryMNSelectedPortServiceImpl
	@Resource(name = "queryMNSelectedPortServiceImpl")
	private ChartServiceInf queryMNSelectedPortServiceImpl;

	@Resource(name = "queryServerSubSysinfoServiceImpl")
	private QueryServerSubSysinfoServiceImpl queryServerSubSysinfoServiceImpl;

	// queryListMediaForwardDataServiceImpl

	@Resource(name = "queryListByDateMediaForwardDataServiceImpl")
	private ChartServiceInf queryListByDateMediaForwardDataServiceImpl;

	// queryListByDateMNServiceImpl
	@Resource(name = "queryListByDateMNServiceImpl")
	private ChartServiceInf queryListByDateMNServiceImpl;
	// queryMediaForwardDataByIdServiceImpl

	@Resource(name = "queryMediaForwardDataByIdServiceImpl")
	private ChartServiceInf queryMediaForwardDataByIdServiceImpl;

	// queryMediaControlDataByIdServiceImpl

	@Resource(name = "queryMediaControlDataByIdServiceImpl")
	private ChartServiceInf queryMediaControlDataByIdServiceImpl;

	// queryPHPByIdServiceImpl
	@Resource(name = "queryPHPByIdServiceImpl")
	private ChartServiceInf queryPHPByIdServiceImpl;

	// queryVideoByIdServiceImpl
	@Resource(name = "queryVideoByIdServiceImpl")
	private ChartServiceInf queryVideoByIdServiceImpl;

	// queryPhpManagerByIdServiceImpl
	@Resource(name = "queryPhpManagerByIdServiceImpl")
	private ChartServiceInf queryPhpManagerByIdServiceImpl;
	// queryTDByIdServiceImpl
	@Resource(name = "queryTDByIdServiceImpl")
	private ChartServiceInf queryTDByIdServiceImpl;
	// querySSDByIdServiceImpl
	@Resource(name = "querySSDByIdServiceImpl")
	private ChartServiceInf querySSDByIdServiceImpl;
	// queryHpByIdServiceImpl
	@Resource(name = "queryHpByIdServiceImpl")
	private ChartServiceInf queryHpByIdServiceImpl;
	// queryLbsByIdServiceImpl
	@Resource(name = "queryLbsByIdServiceImpl")
	private ChartServiceInf queryLbsByIdServiceImpl;
	// queryMdsByIdServiceImpl
	@Resource(name = "queryMdsByIdServiceImpl")
	private ChartServiceInf queryMdsByIdServiceImpl;
	// queryMssByIdServiceImpl
	@Resource(name = "queryMssByIdServiceImpl")
	private ChartServiceInf queryMssByIdServiceImpl;
	// queryMtsByIdServiceImpl
	@Resource(name = "queryMtsByIdServiceImpl")
	private ChartServiceInf queryMtsByIdServiceImpl;
	// queryPrsByIdServiceImpl
	@Resource(name = "queryPrsByIdServiceImpl")
	private ChartServiceInf queryPrsByIdServiceImpl;
	// queryRmsByIdServiceImpl
	@Resource(name = "queryRmsByIdServiceImpl")
	private ChartServiceInf queryRmsByIdServiceImpl;
	// queryTasByIdServiceImpl
	@Resource(name = "queryTasByIdServiceImpl")
	private ChartServiceInf queryTasByIdServiceImpl;
	// queryUmsByIdServiceImpl
	@Resource(name = "queryUmsByIdServiceImpl")
	private ChartServiceInf queryUmsByIdServiceImpl;

	@RequestMapping(value = "/mfChart", method = RequestMethod.GET)
	public String userList(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return "/charts/mediaForward";
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
			if (server_type.equals(SubSysConstant.MEDIA_TRANS)) {
				resultMap = queryMediaForwardDataServiceImpl.handler(map);
			} else {
				resultMap = queryMediaControlDataServiceImpl.handler(map);
			}
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				JsonUtil.WriteJson("queryThreshold", resultMap.get("resData"), request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("queryThreshold", dataMap, request, response);
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 初始化媒体转发子系统下拉菜单
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
			map.put("sys_type", SubSysConstant.MEDIA);
			resultMap = queryServerSubSysinfoServiceImpl.handler(map);
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				List ls = (List) resultMap.get("resData");
				JsonUtil.WriteJson("initFisData", ls, request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("initFisData", dataMap, request, response);
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 初始化媒体转发子系统ip下拉菜单
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
			if (server_type.equals(SubSysConstant.MEDIA_TRANS)) {
				resultMap = querySelectedIpServiceImpl.handler(map);
			} else {
				resultMap = queryMNSelectedIpServiceImpl.handler(map);
			}

			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				List ls = (List) resultMap.get("resData");
				JsonUtil.WriteJson("initFisData", ls, request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("initFisData", dataMap, request, response);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 初始化媒体转发子系统ip对应的端口下拉菜单
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
			if (server_type.equals(SubSysConstant.MEDIA_TRANS)) {
				resultMap = querySelectedPortServiceImpl.handler(map);
			} else {
				resultMap = queryMNSelectedPortServiceImpl.handler(map);
			}
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				List ls = (List) resultMap.get("resData");
				JsonUtil.WriteJson("initSecData", ls, request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("queryThreshold", dataMap, request, response);
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
		String interval = request.getParameter("interval");
		Map resultMap = null;
		try {
			Map map = new HashMap();
			map.put("ip", ip);
			map.put("server_type", server_type);
			map.put("port", port);
			map.put("time", date);
			map.put("interval", interval);
			if (server_type.equals(SubSysConstant.MEDIA_TRANS)) {
				resultMap = queryListByDateMediaForwardDataServiceImpl.handler(map);
			} else {
				resultMap = queryListByDateMNServiceImpl.handler(map);
			}

			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				List ls = (List) resultMap.get("resData");
				JsonUtil.WriteJson("queryThityData", ls, request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("queryThityData", dataMap, request, response);
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * 根据某个节点id查询节点详情 参数： 当前节点的ID 当前节点的服务
	 * 
	 * @throws Exception
	 */

	@RequestMapping(value = "/queryCurrentData", method = RequestMethod.POST)
	public void queryCurrentData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String server_type = request.getParameter("server_type");
		Map resultMap = null;
		Map map = new HashMap();
		map.put("id", id);
		map.put("server_type", server_type);
		// 媒体转发
		if (server_type.equals(SubSysConstant.MEDIA_TRANS)) {
			resultMap = queryMediaForwardDataByIdServiceImpl.handler(map);
			// 媒体控制
		} else if (server_type.equals(SubSysConstant.MEDIA_CONTROL)) {
			resultMap = queryMediaControlDataByIdServiceImpl.handler(map);
		} else if (server_type.equals(SubSysConstant.IM_LBS)) {
			resultMap = queryLbsByIdServiceImpl.handler(map);
		} else if (server_type.equals(SubSysConstant.IM_MDS)) {
			resultMap = queryMdsByIdServiceImpl.handler(map);
		} else if (server_type.equals(SubSysConstant.IM_MTS)) {
			resultMap = queryMtsByIdServiceImpl.handler(map);
		} else if (server_type.equals(SubSysConstant.IM_TAS)) {
			resultMap = queryTasByIdServiceImpl.handler(map);
		} else if (server_type.equals(SubSysConstant.IM_UMS)) {
			resultMap = queryUmsByIdServiceImpl.handler(map);
		} else if (server_type.equals(SubSysConstant.IM_PRS)) {
			resultMap = queryPrsByIdServiceImpl.handler(map);
		} else if (server_type.equals(SubSysConstant.IM_MSS)) {
			resultMap = queryMssByIdServiceImpl.handler(map);
		} else if (server_type.equals(SubSysConstant.IM_RMS)) {
			resultMap = queryRmsByIdServiceImpl.handler(map);
		} else if (server_type.equals(SubSysConstant.IM_HTTPPROXY)) {
			resultMap = queryHpByIdServiceImpl.handler(map);
		} else if (server_type.equals(SubSysConstant.PHP_ZB)) {
			resultMap = queryPHPByIdServiceImpl.handler(map);
		} else if (server_type.equals(SubSysConstant.PHP_DB)) {
			resultMap = queryVideoByIdServiceImpl.handler(map);
		} else if (server_type.equals(SubSysConstant.PHP_GK)) {
			resultMap = queryPhpManagerByIdServiceImpl.handler(map);
		} else if (server_type.equals(SubSysConstant.OT_DECODE)) {
			resultMap = queryTDByIdServiceImpl.handler(map);
		} else {
			resultMap = querySSDByIdServiceImpl.handler(map);
		}
		Map dataMap = new HashMap();
		String flag = resultMap.get("resultCode").toString();
		if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
			JsonUtil.WriteJson("queryThityData", resultMap.get("resData"), request, response);
		} else {
			dataMap.put("result", ResultCode.getResultMessage(flag));
			JsonUtil.WriteJson("queryCurrentData", dataMap, request, response);
		}

	}

}
