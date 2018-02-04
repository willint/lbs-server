package com.lbs.nettyserver.handler;


import com.lbs.nettyserver.protocol.LbsMessageDecoder;
import com.lbs.nettyserver.protocol.LbsMessageEncoder;

import io.netty.handler.codec.http.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lbs.nettyserver.dispatcher.HandlerDispatcher;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

@Component
public class ChannelInitialHandler extends ChannelInitializer<SocketChannel>  {
	private static final Log logger = LogFactory.getLog(ChannelInitialHandler.class);
	private int timeout = 3600;
	private HandlerDispatcher handlerDispatcher;
	private int bizPort;
	private int filePort;
	private int msgPort;
	
	@Autowired
	private ChannelCommunicateHandler channelCommunicateHandler;

	@Autowired
	private LbsMsgHandler lbsMsgHandler;

	public void init() {
		
//		if(null == channelCommunicateHandler){
//			channelCommunicateHandler = new ChannelCommunicateHandler(this.handlerDispatcher);
//		}
		new Thread(this.handlerDispatcher).start();
		if(null != channelCommunicateHandler){
			channelCommunicateHandler.setHandlerDispatcher(this.handlerDispatcher);
		}
	}
	

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		logger.info("create new connection");
		int port = ch.localAddress().getPort();
		ChannelPipeline pipeline = ch.pipeline();
		logger.info("msgPort:"+msgPort);
		if (port == bizPort){
			pipeline.addLast("codec-http", new HttpServerCodec());
			pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
			pipeline.addLast("timeout", new ReadTimeoutHandler(this.timeout));
			pipeline.addLast("handler", channelCommunicateHandler);
		}else if (port == filePort){
			pipeline.addLast("decoder", new HttpRequestDecoder());
			pipeline.addLast("encoder", new HttpResponseEncoder());
			pipeline.addLast("deflater", new HttpContentCompressor());
			pipeline.addLast("handler", new FileServerHandler());
		}else if(port == msgPort){
			pipeline.addLast("timeout",new ReadTimeoutHandler(this.timeout));
			pipeline.addLast("encoder",new LbsMessageEncoder());
			pipeline.addLast("decoder",new LbsMessageDecoder());
			pipeline.addLast("handler" ,lbsMsgHandler);
		}

	}
	public int getTimeout() {
		return timeout;
	}


	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}


	public HandlerDispatcher getHandlerDispatcher() {
		return handlerDispatcher;
	}


	public void setHandlerDispatcher(HandlerDispatcher handlerDispatcher) {
		this.handlerDispatcher = handlerDispatcher;
	}
	
	public ChannelCommunicateHandler getChannelCommunicateHandler() {
		return channelCommunicateHandler;
	}
	public void setChannelCommunicateHandler(
			ChannelCommunicateHandler channelCommunicateHandler) {
		this.channelCommunicateHandler = channelCommunicateHandler;
	}

	public int getFilePort() {
		return filePort;
	}

	public void setFilePort(int filePort) {
		this.filePort = filePort;
	}

	public int getBizPort() {
		return bizPort;
	}

	public void setBizPort(int bizPort) {
		this.bizPort = bizPort;
	}

	public int getMsgPort() {
		return msgPort;
	}

	public void setMsgPort(int msgPort) {
		this.msgPort = msgPort;
	}
}
