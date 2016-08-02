package org.jam.dataflow.web;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.jam.dataflow.Dataflow;

/**
 * Created by James on 16/5/13.
 */
public class HttpServer {

    private Integer port;

    private Thread listening;

    public HttpServer() throws Exception{
        port = Integer.valueOf(Dataflow.getProperty("http-port"));
    }

    public void start() throws Exception {
        listening = new Thread(new Runnable() {
            public void run() {
                startHttpServer();
            }
        });
        listening.start();
    }

    private void startHttpServer(){
        NioEventLoopGroup bossGroup = null;
        NioEventLoopGroup workerGroup = null;
        try {
            EventExecutorGroup group = new DefaultEventExecutorGroup(10);
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.option(ChannelOption.SO_BACKLOG, 1024);// 设置等待队列的大小
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new HttpInitializer(group));

            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            Dataflow.logger().error("Failed to start http manage server.", e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }

    public void close() throws Exception {
    }
}
