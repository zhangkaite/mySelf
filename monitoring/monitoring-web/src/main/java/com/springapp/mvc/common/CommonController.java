package com.springapp.mvc.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/common")
public class CommonController {

	/**
	 * 数据字典列表
	 */
	
	/***
	 * 加载数据字典页面
	 */
	@RequestMapping(value = "showPage", method = RequestMethod.POST)
	public String  showPage( HttpServletRequest request, HttpServletResponse response) {
		
		
		return "/dataDict/dataDictList";
	}
	
	/**
	 * 查询数据字典
	 */
	
	@RequestMapping(value = "queryDataDict", method = RequestMethod.POST)
	public void  queryDataDict( HttpServletRequest request, HttpServletResponse response) {
		
		
	}
	
	/***
	 * 数据字典新增
	 */
	@RequestMapping(value = "addDataDict", method = RequestMethod.POST)
	public void  addDataDict( HttpServletRequest request, HttpServletResponse response) {
		
		
	}
	
	
	/**
	 * 数据字典删除
	 */
	@RequestMapping(value = "delDataDict", method = RequestMethod.POST)
	public void  delDataDict( HttpServletRequest request, HttpServletResponse response) {
		
		
	}
	
	
	
	/**
	 * 数据字典查询
	 */
	@RequestMapping(value = "showDataDict", method = RequestMethod.POST)
	public void  showDataDict( HttpServletRequest request, HttpServletResponse response) {
		
		
	}
	
	
	
	/**
	 * 数据字典更新
	 */
	
	@RequestMapping(value = "updateDataDict", method = RequestMethod.POST)
	public void  updateDataDict( HttpServletRequest request, HttpServletResponse response) {
		
		
	}
	
	
	
	
}
