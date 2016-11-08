package com.ttmv.datacenter.gromit.server.netty.tmcp;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.com.agree.tools.StringTool;

import com.ttmv.datacenter.message.tmcp.TmcpMsgInputStream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * description：netty 默认的handler处理类
 * @author Scarlett.zhou
 * @date 2015年1月6日
 */

public class TmcpMessageDecoder extends ByteToMessageDecoder {

	private static final Logger logger = LogManager.getLogger(TmcpMessageDecoder.class);

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out){		 
		 byte[] data = new byte[in.readableBytes()];
		 in.getBytes(0, data);
		 logger.debug("[原始报文] Size"+in.readableBytes()+"\n"+StringTool.toHexTable(data));
		 int cutUntil = TmcpMsgInputStream.cutMsgUntil(in);
		 switch(cutUntil){
		 case -1:
			 return;
		 case -2:
		    in.discardReadBytes();
		    break;
		 case -3:
			 in.discardReadBytes();
			 break;
	     default:
	    	 out.add(in.readBytes(cutUntil+1));
		 }
	
	}
}
