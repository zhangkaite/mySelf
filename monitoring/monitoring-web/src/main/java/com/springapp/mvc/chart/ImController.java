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
@RequestMapping("/im")
@SuppressWarnings({ "rawtypes", "unchecked", "restriction" })
public class ImController {

	// HP
	@Resource(name = "queryHpServerDataServiceImpl")
	private ChartServiceInf queryHpServerDataServiceImpl;
	@Resource(name = "queryHpSelectedIpServiceImpl")
	private ChartServiceInf queryHpSelectedIpServiceImpl;
	@Resource(name = "queryHpSelectedPortServiceImpl")
	private ChartServiceInf queryHpSelectedPortServiceImpl;
	@Resource(name = "queryListByDateHpServiceImpl")
	private ChartServiceInf queryListByDateHpServiceImpl;

	// LBS
	@Resource(name = "queryLbsServerDataServiceImpl")
	private ChartServiceInf queryLbsServerDataServiceImpl;
	@Resource(name = "queryLbsSelectedIpServiceImpl")
	private ChartServiceInf queryLbsSelectedIpServiceImpl;
	@Resource(name = "queryLbsSelectedPortServiceImpl")
	private ChartServiceInf queryLbsSelectedPortServiceImpl;
	@Resource(name = "queryListByDateLbsServiceImpl")
	private ChartServiceInf queryListByDateLbsServiceImpl;

	// Mds
	@Resource(name = "queryMdsServerDataServiceImpl")
	private ChartServiceInf queryMdsServerDataServiceImpl;
	@Resource(name = "queryMdsSelectedIpServiceImpl")
	private ChartServiceInf queryMdsSelectedIpServiceImpl;
	@Resource(name = "queryMdsSelectedPortServiceImpl")
	private ChartServiceInf queryMdsSelectedPortServiceImpl;
	@Resource(name = "queryListByDateMdsServiceImpl")
	private ChartServiceInf queryListByDateMdsServiceImpl;

	// Mss
	@Resource(name = "queryMssServerDataServiceImpl")
	private ChartServiceInf queryMssServerDataServiceImpl;
	@Resource(name = "queryMssSelectedIpServiceImpl")
	private ChartServiceInf queryMssSelectedIpServiceImpl;
	@Resource(name = "queryMssSelectedPortServiceImpl")
	private ChartServiceInf queryMssSelectedPortServiceImpl;
	@Resource(name = "queryListByDateMssServiceImpl")
	private ChartServiceInf queryListByDateMssServiceImpl;

	// Mts
	@Resource(name = "queryMtsServerDataServiceImpl")
	private ChartServiceInf queryMtsServerDataServiceImpl;
	@Resource(name = "queryMtsSelectedIpServiceImpl")
	private ChartServiceInf queryMtsSelectedIpServiceImpl;
	@Resource(name = "queryMtsSelectedPortServiceImpl")
	private ChartServiceInf queryMtsSelectedPortServiceImpl;
	@Resource(name = "queryListByDateMtsServiceImpl")
	private ChartServiceInf queryListByDateMtsServiceImpl;

	// Prs
	@Resource(name = "queryPrsServerDataServiceImpl")
	private ChartServiceInf queryPrsServerDataServiceImpl;
	@Resource(name = "queryPrsSelectedIpServiceImpl")
	private ChartServiceInf queryPrsSelectedIpServiceImpl;
	@Resource(name = "queryPrsSelectedPortServiceImpl")
	private ChartServiceInf queryPrsSelectedPortServiceImpl;
	@Resource(name = "queryListByDatePrsServiceImpl")
	private ChartServiceInf queryListByDatePrsServiceImpl;

	// Rms
	@Resource(name = "queryRmsServerDataServiceImpl")
	private ChartServiceInf queryRmsServerDataServiceImpl;
	@Resource(name = "queryRmsSelectedIpServiceImpl")
	private ChartServiceInf queryRmsSelectedIpServiceImpl;
	@Resource(name = "queryRmsSelectedPortServiceImpl")
	private ChartServiceInf queryRmsSelectedPortServiceImpl;
	@Resource(name = "queryListByDateRmsServiceImpl")
	private ChartServiceInf queryListByDateRmsServiceImpl;

	// Tas
	@Resource(name = "queryTasServerDataServiceImpl")
	private ChartServiceInf queryTasServerDataServiceImpl;
	@Resource(name = "queryTasSelectedIpServiceImpl")
	private ChartServiceInf queryTasSelectedIpServiceImpl;
	@Resource(name = "queryTasSelectedPortServiceImpl")
	private ChartServiceInf queryTasSelectedPortServiceImpl;
	@Resource(name = "queryListByDateTasServiceImpl")
	private ChartServiceInf queryListByDateTasServiceImpl;

	// Ums

	@Resource(name = "queryUmsServerDataServiceImpl")
	private ChartServiceInf queryUmsServerDataServiceImpl;
	@Resource(name = "queryUmsSelectedIpServiceImpl")
	private ChartServiceInf queryUmsSelectedIpServiceImpl;
	@Resource(name = "queryUmsSelectedPortServiceImpl")
	private ChartServiceInf queryUmsSelectedPortServiceImpl;
	@Resource(name = "queryListByDateUmsServiceImpl")
	private ChartServiceInf queryListByDateUmsServiceImpl;

	@Resource(name = "queryServerSubSysinfoServiceImpl")
	private QueryServerSubSysinfoServiceImpl queryServerSubSysinfoServiceImpl;

	@RequestMapping(value = "/mfChart", method = RequestMethod.GET)
	public String userList(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return "/charts/imForward";
	}

	/***
	 * 查询IM当前最新节点信息
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
			if (server_type.equals(SubSysConstant.IM_HTTPPROXY)) {
				resultMap=queryHpServerDataServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_LBS)) {
				resultMap=queryLbsServerDataServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_MDS)) {
				resultMap=queryMdsServerDataServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_MSS)) {
				resultMap=queryMssServerDataServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_MTS)) {
				resultMap=queryMtsServerDataServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_PRS)) {
				resultMap=queryPrsServerDataServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_RMS)) {
				resultMap=queryRmsServerDataServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_TAS)) {
				resultMap=queryTasServerDataServiceImpl.handler(map);
			} else {
				resultMap=queryUmsServerDataServiceImpl.handler(map);
			}

			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				JsonUtil.WriteJson("im/queryThreshold", resultMap.get("resData"), request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("im/queryThreshold", dataMap, request, response);
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 初始化IM子系统下拉菜单
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
			map.put("sys_type", SubSysConstant.IM);
			resultMap = queryServerSubSysinfoServiceImpl.handler(map);
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				List ls = (List) resultMap.get("resData");
				JsonUtil.WriteJson("im/initFisData", ls, request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("im/initFisData", dataMap, request, response);
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 初始化IM子系统ip下拉菜单
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
			if (server_type.equals(SubSysConstant.IM_HTTPPROXY)) {
				resultMap=queryHpSelectedIpServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_LBS)) {
				resultMap=queryLbsSelectedIpServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_MDS)) {
				resultMap=queryMdsSelectedIpServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_MSS)) {
				resultMap=queryMssSelectedIpServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_MTS)) {
				resultMap=queryMtsSelectedIpServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_PRS)) {
				resultMap=queryPrsSelectedIpServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_RMS)) {
				resultMap=queryRmsSelectedIpServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_TAS)) {
				resultMap=queryTasSelectedIpServiceImpl.handler(map);
			} else {
				resultMap=queryUmsSelectedIpServiceImpl.handler(map);
			}
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				List ls = (List) resultMap.get("resData");
				JsonUtil.WriteJson("im/initFisData", ls, request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("im/initFisData", dataMap, request, response);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 初始化IM子系统ip对应的端口下拉菜单
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
			if (server_type.equals(SubSysConstant.IM_HTTPPROXY)) {
				resultMap=queryHpSelectedPortServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_LBS)) {
				resultMap=queryLbsSelectedPortServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_MDS)) {
				resultMap=queryMdsSelectedPortServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_MSS)) {
				resultMap=queryMssSelectedPortServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_MTS)) {
				resultMap=queryMtsSelectedPortServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_PRS)) {
				resultMap=queryPrsSelectedPortServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_RMS)) {
				resultMap=queryRmsSelectedPortServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_TAS)) {
				resultMap=queryTasSelectedPortServiceImpl.handler(map);
			} else {
				resultMap=queryUmsSelectedPortServiceImpl.handler(map);
			}
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				List ls = (List) resultMap.get("resData");
				JsonUtil.WriteJson("im/initSecData", ls, request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("im/queryThreshold", dataMap, request, response);
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
			if (server_type.equals(SubSysConstant.IM_HTTPPROXY)) {
				resultMap=queryListByDateHpServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_LBS)) {
				resultMap=queryListByDateLbsServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_MDS)) {
				resultMap=queryListByDateMdsServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_MSS)) {
				resultMap=queryListByDateMssServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_MTS)) {
				resultMap=queryListByDateMtsServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_PRS)) {
				resultMap=queryListByDatePrsServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_RMS)) {
				resultMap=queryListByDateRmsServiceImpl.handler(map);
			} else if (server_type.equals(SubSysConstant.IM_TAS)) {
				resultMap=queryListByDateTasServiceImpl.handler(map);
			} else {
				resultMap=queryListByDateUmsServiceImpl.handler(map);
			}
			Map dataMap = new HashMap();
			String flag = resultMap.get("resultCode").toString();
			if (ResultCodeConstant.RESULTCODE_SUCCESS.equals(flag)) {
				List ls = (List) resultMap.get("resData");
				JsonUtil.WriteJson("queryThityData", ls, request, response);
			} else {
				dataMap.put("result", ResultCode.getResultMessage(flag));
				JsonUtil.WriteJson("im/queryThityData", dataMap, request, response);
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	

}
