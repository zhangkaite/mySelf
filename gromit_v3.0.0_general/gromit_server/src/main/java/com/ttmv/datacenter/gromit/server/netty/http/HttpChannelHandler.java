package com.ttmv.datacenter.gromit.server.netty.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.gromit.server.flow.Dispatcher;
import com.ttmv.datacenter.gromit.server.flow.GromitRequest;
import com.ttmv.datacenter.gromit.server.flow.GromitResponse;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;


public class HttpChannelHandler extends SimpleChannelInboundHandler<FullHttpRequest>{
	
	private final static Logger logger = LogManager.getLogger(HttpChannelHandler.class);
    
    private final static String CONTENT_TYPE = "content_type";
    
    private final static String exceData = "{\"resultCode\":\"Error500\",\"errorMsg\":\"OP503\"}";

    
    private Dispatcher dispatcher;
    
    public HttpChannelHandler(Dispatcher dispatcher){
    	this.dispatcher = dispatcher;
    }
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request)	throws Exception {
		//如果request为空，解码不成功，不是post请求，返回错误信息
		if(request == null || !request.getDecoderResult().isSuccess() || request.getMethod() != HttpMethod.POST){
			logger.error("传入的参数有误,或者非POST请求." );
			sendError(ctx,HttpResponseStatus.FORBIDDEN);
		}
		String uri = request.getUri();
		//对post请求进行解析
		GromitRequest gromitRequest = new GromitRequest(request);
		gromitRequest.setContext(dispatcher.getServerContainer().getContext());
	    GromitResponse response = new GromitResponse(gromitRequest,ctx);
	    dispatcher.handleRequestHTTP(gromitRequest, response, uri);
	}
	
	/**
	 *  发生异常时候的处理
	 * */

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.error("出现异常，异常原因："+cause.getMessage());
		//对post请求进行解析
		sendError(ctx,HttpResponseStatus.PRECONDITION_FAILED);
	}

	private static void sendError(ChannelHandlerContext ctx,HttpResponseStatus status){	
		ByteBuf b = Unpooled.copiedBuffer(exceData,CharsetUtil.UTF_8);
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,status,b);
		response.headers().set(CONTENT_TYPE,"text/plain; charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
}