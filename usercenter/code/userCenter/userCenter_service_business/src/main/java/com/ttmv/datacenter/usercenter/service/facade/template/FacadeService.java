package com.ttmv.datacenter.usercenter.service.facade.template;

import java.util.Map;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;

import cn.com.agree.tools.StringTool;

import com.ttmv.datacenter.gromit.gromitService.GromitService;
import com.ttmv.datacenter.gromit.server.flow.GromitRequest;
import com.ttmv.datacenter.gromit.server.flow.GromitResponse;
import com.ttmv.datacenter.message.tmcp.TmcpMessage;
import com.ttmv.datacenter.usercenter.domain.protocol.GeneralPro;
import com.ttmv.datacenter.usercenter.service.processor.invoke.BeanInvoke;
import com.ttmv.datacenter.utils.check.CheckParameterUtil;
import com.ttmv.datacenter.utils.message.DefaultTmcpMessage;


/**
 * 
 * @author Scarlett.zhou
 * @date 2015年1月13日
 */
@SuppressWarnings({ "rawtypes","static-access"})
public abstract class FacadeService implements GromitService {

	private static final Logger logger = LogManager.getLogger(FacadeService.class);
	private static final String exceData = "{\"resultCode\":\"Error500\",\"errorMsg\":\"OP503\"}";

	public void service(HttpServletRequest request, HttpServletResponse response) {
		GromitResponse gromitResponse = (GromitResponse) response;
		GromitRequest gromitRequest = (GromitRequest) request;
		//从request中获取开始时间
		long startTime = gromitRequest.getStartTime();
		logger.info("\n===[收到请求数据]====\n"+gromitRequest.getParameter("data"));
		try {
	        //--- 调用 service的execute方法返回Map
	        Map str = callExecute(gromitRequest.getParameter("data"),gromitRequest.getContext());
	        //--- 转换Map为Json串
			String data = new ObjectMapper().writeValueAsString(str);
			logger.info("[返回数据] --->"+data);
			if (gromitRequest.getMessage() != null) {
				// 如果是tmcp请求构造message的包头
				TmcpMessage message = createMessage(gromitRequest.getMessage(),data);
				logger.debug("[TCP响应报文 报文头] \n "+message.formatString());
				logger.debug("[发送TCP响应报文] Size:" + message.formatBytes().length + "\n"+ StringTool.toHexTable(message.formatBytes()));
				gromitResponse.getChannel().writeAndFlush(Unpooled.copiedBuffer(message.formatBytes()));
				//结束时间
				long stopTime = System.currentTimeMillis();
				logger.info("[***TCP类型交易完成！！！***]  -- 用时："+(stopTime-startTime));
			} else if (gromitRequest.getNettyHttpRequest() != null) {
				// 如果是netty http请求用netty的http方式丢出去
				FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK);
				fullHttpResponse.content().writeBytes(Unpooled.copiedBuffer(data.getBytes()));
				gromitResponse.getChannel().writeAndFlush(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
				//结束时间
				long stopTime = System.currentTimeMillis();
				logger.info("[***HTTP类型交易完成！！！***]  -- 用时："+(stopTime-startTime));
			}else{
		        logger.error("Invalid parameter -- [gromitRequest] or [gromitResponse]");
		        throw new Exception("Invalid parameter -- [gromitRequest] or [gromitResponse]");
			}
		} catch (Exception e) {
			logger.error("程序出现异常，异常原因:",e);
			if (gromitRequest.getMessage() != null) {
				DefaultTmcpMessage dtm = new DefaultTmcpMessage(0x1000,0x1999,1);
				TmcpMessage message = dtm.requestErrowTmcpMessage(exceData,gromitRequest.getMessage().getSequence());
				try {		   
				   gromitResponse.getChannel().writeAndFlush(Unpooled.copiedBuffer(message.formatBytes()));
				   logger.debug("[程序出现异常—返回客户端报文]:"+message.formatString());
				} catch (Exception e1) {
					logger.fatal("默认返回包头转码出错，无法对客户端进行数据返回，请检查代码和配置文件.");
					gromitResponse.getChannel().writeAndFlush(null);
				}
			} else if (gromitRequest.getNettyHttpRequest() != null) {
				FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK);
				fullHttpResponse.content().writeBytes(Unpooled.copiedBuffer(exceData.getBytes()));
				gromitResponse.getChannel().writeAndFlush(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
		    }
	   }
	}
	
	
	private Map callExecute(String data,ApplicationContext context) throws Exception{
	    GeneralPro generalPro = (GeneralPro) BeanInvoke.getServiceObject(data,GeneralPro.class,false);
		if(generalPro==null || generalPro.getReqData() == null || CheckParameterUtil.checkIsEmpty(generalPro.getService())){
			throw new Exception("Invalid json --- "+data);
		}
		Object obj = generalPro.getReqData();
		Class<?> serviceClass = context.getBean(generalPro.getService() + "Bean").getClass();
		Object serviceObject = BeanInvoke.getServiceObject(obj,serviceClass,true);
		if(serviceObject==null){
			throw new Exception("Invalid json --- "+data);
		}	
	    return execute(generalPro,serviceObject);
	    
	    
	}

	/**
	 * 获得传入的包头，按照不同的系统构造新的包头
	 * 
	 * */
	public TmcpMessage createMessage(TmcpMessage oldMessage, String data)throws Exception {
		if (CheckParameterUtil.checkIsEmpty(data)) {
			logger.error("data为空");
			data = exceData;
		}
		DefaultTmcpMessage dtm = new DefaultTmcpMessage(0x1000,0x1999,1);
		TmcpMessage newMessage = dtm.requestTmcpMessage(oldMessage, data);
	    return newMessage;
	}

	public abstract Map execute(GeneralPro generalPro,Object object);
}
