package com.ttmv.datacenter.gromit.server.netty.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.gromit.server.flow.Dispatcher;
import com.ttmv.datacenter.gromit.server.flow.ServerContainer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * 
 * @author Scarlett.zhou
 * @date 2015年1月13日
 */
public class HttpInitializer extends ChannelInitializer<SocketChannel>{
	
	private static final Logger logger = LogManager.getLogger(HttpInitializer.class);
    
    private EventExecutorGroup group;    

    private	ServerContainer serverContainer;	

	public HttpInitializer(EventExecutorGroup group, ServerContainer serverContainer){
        this.group = group;
		this.serverContainer = serverContainer;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		   Dispatcher dispacher = new Dispatcher(this.serverContainer);
           ch.pipeline().addLast(new HttpRequestDecoder());	
           ch.pipeline().addLast(new HttpObjectAggregator(65536));	
           ch.pipeline().addLast(new HttpResponseEncoder());	
           //ch.pipeline().addLast(new ChunkedWriteHandler());	//我们没有大数据的传输需要，所以可以不用这个handler
           ch.pipeline().addLast(group,new HttpChannelHandler(dispacher));		
	}
	

}
