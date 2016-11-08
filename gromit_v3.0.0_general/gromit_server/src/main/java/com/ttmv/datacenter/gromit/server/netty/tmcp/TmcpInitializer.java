package com.ttmv.datacenter.gromit.server.netty.tmcp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.gromit.server.flow.Dispatcher;
import com.ttmv.datacenter.gromit.server.flow.ServerContainer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * description：
 * create by Scarlett.zhou on 2015年1月6日
 */
public class TmcpInitializer extends ChannelInitializer<SocketChannel>{
	
	private static final Logger logger = LogManager.getLogger(TmcpInitializer.class);
    
    private EventExecutorGroup group;    

    private	ServerContainer serverContainer;	

	public TmcpInitializer(EventExecutorGroup group, ServerContainer serverContainer){
        this.group = group;
		this.serverContainer = serverContainer;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		Dispatcher dispacher = new Dispatcher(this.serverContainer);
	    ch.pipeline().addLast("tmcpMessageDecoder", new TmcpMessageDecoder());
		ch.pipeline().addLast(group,"tmcpChannelHandler",new TmcpChannelHandler(dispacher));
	}

}
