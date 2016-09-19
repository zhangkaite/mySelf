package com.ttmv.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HelloController {

	@RequestMapping(value="request", method = RequestMethod.GET)
	public void printWelcome(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getParameter("data");
			response.getWriter().print("hahahaha");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}