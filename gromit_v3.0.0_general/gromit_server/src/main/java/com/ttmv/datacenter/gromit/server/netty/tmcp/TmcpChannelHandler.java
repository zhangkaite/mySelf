package com.ttmv.datacenter.gromit.server.netty.tmcp;

import java.io.ByteArrayInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cn.com.agree.tools.StringTool;
import com.ttmv.datacenter.gromit.server.flow.Dispatcher;
import com.ttmv.datacenter.gromit.server.flow.GromitRequest;
import com.ttmv.datacenter.gromit.server.flow.GromitResponse;
import com.ttmv.datacenter.message.tmcp.TmcpMsgInputStream;
import com.ttmv.datacenter.message.tmcp.TmcpMessage;
import com.ttmv.datacenter.utils.message.DefaultTmcpMessage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 分配service
 * 
 * @author Scarlett.zhou
 * @date 2015年1月9日
 */
public class TmcpChannelHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LogManager.getLogger(TmcpChannelHandler.class);
	private final static String exceData = "{\"resultCode\":\"Error500\",\"errorMsg\":\"OP503\"}";
	private Dispatcher dispatcher;

	public TmcpChannelHandler(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@Override
	public void channelActive(final ChannelHandlerContext ctx) {
		logger.debug("Client: " + ctx.channel().remoteAddress() + " connected.");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	String dd;
		ByteBuf buf = (ByteBuf) msg;
		if (!buf.hasArray()) {
			logger.error("传入的参数有错误 [msg]="+msg);
			throw new Exception("Invalid is msg !");
		}
		byte[] data = buf.array();
		buf.getBytes(0, data);
		buf.discardReadBytes();
		if (null == data || data.length == 0) {
			logger.error("传入的数据为空.");
			throw new Exception("There is nothing in the direct buffer.");
		}
		logger.info("[处理后报文] Size" + data.length + "\n"+ StringTool.toHexTable(data));
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		TmcpMessage message = new TmcpMsgInputStream(in).readMsg();
		GromitRequest request = new GromitRequest(message);
		request.setContext(dispatcher.getServerContainer().getContext());
		GromitResponse response = new GromitResponse(request, ctx);
		dispatcher.handleRequestTMCP(request, response);
	}

	/**
	 * 发生异常时候的处理
	 * */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		DefaultTmcpMessage dtm = (DefaultTmcpMessage)dispatcher.getServerContainer().getContext().getBean("DefaultTmcpMessage");	
		TmcpMessage message = dtm.requestErrowTmcpMessage(exceData);
		logger.error("出现异常，异常原因："+cause.getMessage());
		try {
			ctx.writeAndFlush(Unpooled.copiedBuffer(message.formatBytes()));
		} catch (Exception e) {
			logger.fatal("默认返回包头转码出错，请检查代码和配置文件.");
			ctx.writeAndFlush(null);
		}
	}
}
