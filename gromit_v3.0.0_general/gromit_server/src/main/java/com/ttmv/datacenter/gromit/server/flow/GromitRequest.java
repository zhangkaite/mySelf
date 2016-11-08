package com.ttmv.datacenter.gromit.server.flow;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.ttmv.datacenter.message.tmcp.TmcpMessage;
import com.ttmv.datacenter.utils.letter.UpperCase;

/**
 * 
 * @author Scarlett.zhou
 * @date 2015年1月9日
 */
public class GromitRequest implements HttpServletRequest {

	private static Logger logger = LogManager.getLogger(GromitRequest.class);

	private TmcpMessage message;
	private FullHttpRequest nettyHttpRequest;
	
	private ApplicationContext context;
	
	private HttpPostRequestDecoder postDecoder;

	public GromitRequest(TmcpMessage message) {
		this.message = message;
	}

	public GromitRequest(FullHttpRequest nettyHttpRequest) {
		this.nettyHttpRequest = nettyHttpRequest;
		// 对post进行解析
		 postDecoder = new HttpPostRequestDecoder(
				new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE),
				nettyHttpRequest);
		if (postDecoder != null) {
			postDecoder.offer(nettyHttpRequest);
		}
	}	

	public AsyncContext getAsyncContext() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getAttribute(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Enumeration<String> getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getContentLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	public DispatcherType getDispatcherType() {
		// TODO Auto-generated method stub
		return null;
	}

	public ServletInputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLocalAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLocalName() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getLocalPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	public Enumeration<Locale> getLocales() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, String[]> getParameterMap() {
		// TODO Auto-generated method stub
		return null;
	}

	public Enumeration<String> getParameterNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getParameterValues(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

	public BufferedReader getReader() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRealPath(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRemoteAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRemoteHost() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getRemotePort() {
		// TODO Auto-generated method stub
		return 0;
	}

	public RequestDispatcher getRequestDispatcher(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getScheme() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getServerName() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getServerPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isAsyncStarted() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAsyncSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeAttribute(String arg0) {
		// TODO Auto-generated method stub

	}

	public void setAttribute(String arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	public void setCharacterEncoding(String arg0)
			throws UnsupportedEncodingException {
		// TODO Auto-generated method stub

	}

	public AsyncContext startAsync() throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	public AsyncContext startAsync(ServletRequest arg0, ServletResponse arg1)
			throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean authenticate(HttpServletResponse arg0) throws IOException,
			ServletException {
		// TODO Auto-generated method stub
		return false;
	}

	public String getAuthType() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getContextPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public Cookie[] getCookies() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getDateHeader(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getHeader(String arg0) {
		if (message != null)
			return null;
		try {
			Method m = TmcpMessage.class.getMethod("get"
					+ UpperCase.firstUpperCase(arg0));
			Object object = m.invoke(message.getClass());
			if (object instanceof Byte)
				return ((Byte) object).toString();
			else
				return String.valueOf(object);
		} catch (Exception e) {
			logger.warn("Parse failure -- [TmcpMessage header]"
					+ e.getMessage());
		}
		return null;
	}

	public Enumeration<String> getHeaderNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public Enumeration<String> getHeaders(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getIntHeader(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	public Part getPart(String arg0) throws IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Part> getParts() throws IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPathInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPathTranslated() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getQueryString() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRemoteUser() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRequestURI() {
		// TODO Auto-generated method stub
		return null;
	}

	public StringBuffer getRequestURL() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRequestedSessionId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getServletPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	public HttpSession getSession(boolean arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Principal getUserPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isRequestedSessionIdFromCookie() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRequestedSessionIdFromURL() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRequestedSessionIdFromUrl() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRequestedSessionIdValid() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isUserInRole(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public void login(String arg0, String arg1) throws ServletException {
		// TODO Auto-generated method stub

	}

	public void logout() throws ServletException {
		// TODO Auto-generated method stub

	}

	public String getParameter(String name) {
        if(null == name || "".equals(name)){
            return null;
        }
        try{
        if(message != null){
            //根据名字反射调用get方法
        	Method m =TmcpMessage.class.getMethod("get"+UpperCase.firstUpperCase(name));
			Object o = m.invoke(message);
			return String.valueOf(o);
        }else if(nettyHttpRequest != null && postDecoder !=null){	
        	//根据名字反射调用get方法
        	String attribute = null;
        	InterfaceHttpData data = postDecoder.getBodyHttpData(name);
    		if (data != null || data.getHttpDataType() == HttpDataType.Attribute) {
    			try {
    				attribute = ((Attribute) data).getValue();
    			} catch (IOException e) {
    				// 如果有异常把attribute值设置为null;
    				attribute = null;
    			}
    		}
            return attribute;
        }
        }catch(Exception e){
        	return null;
        }
      return null;
	}

	public TmcpMessage getMessage() {
		return message;
	}

	public void setMessage(TmcpMessage message) {
		this.message = message;
	}

	public FullHttpRequest getNettyHttpRequest() {
		return nettyHttpRequest;
	}

	public void setNettyHttpRequest(FullHttpRequest nettyHttpRequest) {
		this.nettyHttpRequest = nettyHttpRequest;
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}

}
