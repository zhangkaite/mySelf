package com.springapp.mvc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}
	
	public static String[] dataReport = new String[]{"alertingDataReport",
			"avServerControlServiceDataReport",
			"avServerTransmitServiceDataReport",
			"screenshotServiceDataReport",
			"transcodingServiceDataReport",
			"phpLiveServiceDataReport",
			"phpVideoServiceDataReport",
			"phpManageServiceDataReport",
			"imShowLbsServiceDataReport",
			"imShowMdsServiceDataReport",
			"imShowMtsServiceDataReport",
			"imShowTasServiceDataReport",
			"imShowUmsServiceDataReport",
			"imShowPrsServiceDataReport",
			"imShowRmsServiceDataReport",
			"imShowMssServiceDataReport",
			"imShowHttpProxyServiceDataReport"};

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		// /css/bootstrap.min.css
		String requestURI = req.getRequestURI();
		String[] notFilter = new String[] { "css", "js", "images", "fonts", "userLogin","generate","test"};
		boolean doFilter = true;
		//合并数组
		String[] notFilters= new String[dataReport.length + notFilter.length];  
		   System.arraycopy(dataReport, 0, notFilters, 0, dataReport.length);  
		   System.arraycopy(notFilter, 0, notFilters, dataReport.length, notFilter.length);  
		
		
		for (String s : notFilters) {
			if (requestURI.indexOf(s) != -1 || requestURI.length() == 1) {
				// 如果uri中包含不过滤的uri，则不进行过滤
				doFilter = false;
				break;
			}
		}

		if (!doFilter) {
			chain.doFilter(request, response);
			return;
		} else {
			if (null == session.getAttribute("userName")) {
				// String url=requestURI.split("/")[0];
				// 未登录用户，重定向到登录页面
				((HttpServletResponse) response).sendRedirect("/burglar/test");
				return;
			} else {
				// 已登录用户，允许访问
				chain.doFilter(request, response);
			}
		}

	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
