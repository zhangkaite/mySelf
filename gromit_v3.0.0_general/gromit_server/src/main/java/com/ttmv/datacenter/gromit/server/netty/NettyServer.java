package com.ttmv.datacenter.gromit.server.netty;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.gromit.server.GromitServer;
import com.ttmv.datacenter.gromit.server.flow.ServerContainer;
import com.ttmv.datacenter.gromit.server.netty.http.HttpInitializer;
import com.ttmv.datacenter.gromit.server.netty.tmcp.TmcpInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * description： create by Scarlett.zhou on 2015年1月6日
 */
public class NettyServer implements GromitServer {

	private static final Logger logger = LogManager
			.getLogger(NettyServer.class);

	private Map serverConfig;

	private ServerContainer serverContainer;
	
	private Thread tmcpThread;
	private Thread httpThread;

	public NettyServer(Map serverConfig, ServerContainer serverContainer) {
		this.serverConfig = serverConfig;
		this.serverContainer = serverContainer;
	}

	public void start() {
		 tmcpThread = new Thread(new Runnable() {
			public void run() {
				startTmcpServer();
			}
		});
		 httpThread = new Thread(new Runnable() {
			public void run() {
				startHttpServer();
			}
		});
		tmcpThread.start();
		httpThread.start();
	}
    /**
     *  开启tmcp服务
     * */
	private void startTmcpServer() {
		NioEventLoopGroup bossGroup = null;
		NioEventLoopGroup workerGroup = null;
		if (serverConfig == null || serverConfig.get("tmcp_port") == null
				|| serverConfig.get("tmcp_executor_size") == null) {
			logger.error("Invalid parameter : tmcp_port");
			return;
		}
		try {
			int port = (Integer) serverConfig.get("tmcp_port");
			EventExecutorGroup group = new DefaultEventExecutorGroup(
					Integer.valueOf((String) serverConfig
							.get("tmcp_executor_size")));

			bossGroup = new NioEventLoopGroup();
			workerGroup = new NioEventLoopGroup();
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.option(ChannelOption.SO_BACKLOG, 1024);// 设置等待队列的大小
			b.channel(NioServerSocketChannel.class);
			b.childHandler(new TmcpInitializer(group, serverContainer));

			ChannelFuture f = b.bind(port).sync();
			logger.info("[开启Netty TCP长链接监听端口] ---> " + port);
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			logger.error("Failed to start server.", e);
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
    /**
     *  开启http服务
     * */
	private void startHttpServer() {
		NioEventLoopGroup bossGroup = null;
		NioEventLoopGroup workerGroup = null;
		if (serverConfig == null || serverConfig.get("http_port") == null
				|| serverConfig.get("http_executor_size") == null) {
			logger.error("Invalid parameter : http_port");
			return;
		}
		try {
			int port = (Integer) serverConfig.get("http_port");
			EventExecutorGroup group = new DefaultEventExecutorGroup(
					Integer.valueOf((String) serverConfig
							.get("http_executor_size")));
			bossGroup = new NioEventLoopGroup();
			workerGroup = new NioEventLoopGroup();
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.option(ChannelOption.SO_BACKLOG, 1024);// 设置等待队列的大小
			b.channel(NioServerSocketChannel.class);
			b.childHandler(new HttpInitializer(group, serverContainer));

			ChannelFuture f = b.bind(port).sync();
			logger.info("[开启Netty Http监听端口] --->" + port);

			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			logger.error("Failed to start server.", e);
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
	
	public void stop(){
		tmcpThread.stop();;
		httpThread.stop();
	}

}
