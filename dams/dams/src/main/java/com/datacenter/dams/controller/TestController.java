package com.datacenter.dams.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	  	@RequestMapping(value="/addLevel",method= RequestMethod.GET)
	    public @ResponseBody String addLevel(@ModelAttribute("data")String data) throws Exception {
		  	System.out.println("获取到的数据是:" +data);
	        return "ok";
	    }
}
