package org.jam.dataflow.web;



import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.util.concurrent.EventExecutorGroup;

public class HttpInitializer extends ChannelInitializer<SocketChannel>{
	

    private EventExecutorGroup group;    


	public HttpInitializer(EventExecutorGroup group){
        this.group = group;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
           ch.pipeline().addLast(new HttpRequestDecoder());
           ch.pipeline().addLast(new HttpObjectAggregator(65536));	
           ch.pipeline().addLast(new HttpResponseEncoder());	
           ch.pipeline().addLast(group, new DataflowManageHandler());
	}
	

}
