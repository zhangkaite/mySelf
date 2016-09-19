package com.springapp.mvc.chart;

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
import com.ttmv.monitoring.imDataList.ImServiceDataEntity;



/***
 * 查询IM各种业务最新数据
 * @author kate
 *
 */
@Controller
@RequestMapping("/im")
@SuppressWarnings({ "rawtypes", "unchecked", "restriction" })
public class ImDataListController {

	@Resource
	private com.ttmv.monitoring.imDataList.ShowImDataListDao showImDataListDao;
	/**
	 * 加载列表页面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/imDataList", method = RequestMethod.GET)
	public String userList(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return "/charts/imDataList";
	}
	
	
	/**
	 * 异步加载查询的数据
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/showDataList", method = RequestMethod.POST)
	public void queryThreshold(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		try {
			List<ImServiceDataEntity> lsDataList=showImDataListDao.showImDataList();
			Map dataMap = new HashMap();
			dataMap.put("result", lsDataList);
			JsonUtil.WriteJson("dataList", dataMap, request, response);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
	}

}
