package com.lbs.nettyserver;

import java.net.InetSocketAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lbs.nettyserver.handler.ChannelInitialHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
	private static final Log logger =  LogFactory.getLog(NettyServer.class);
	/** 用于分配处理业务线程的线程组个数 */
	protected static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors() * 2; 
	/** 业务出现线程大小 */
	protected static final int BIZTHREADSIZE = 4;

	private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);
	private static final EventLoopGroup workerGroup = new NioEventLoopGroup(BIZTHREADSIZE);

	private ChannelInitialHandler initializer;
	private final int bizPort;
	private final int filePort;
	private final int msgPort;

	public NettyServer(int bizPort, int filePort, int msgPort) {
		this.bizPort = bizPort;
		this.filePort = filePort;
		this.msgPort = msgPort;
	}

	public void setInitializer(ChannelInitialHandler initializer) {
		this.initializer = initializer;
	}

	public void run() throws Exception {

		try {
			ServerBootstrap b = new ServerBootstrap();
			((ServerBootstrap) b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class))
					.option(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(this.initializer);
			Channel bizCh, fileCh,protocolCh;
			bizCh = b.bind(new InetSocketAddress(this.bizPort)).sync().channel();
			fileCh = b.bind(filePort).sync().channel();
			protocolCh = b.bind(msgPort).sync().channel();
			bizCh.closeFuture().sync();
			fileCh.closeFuture().sync();
			protocolCh.closeFuture().sync();
			logger.info("server start...");
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
