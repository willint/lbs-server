package com.lbs.nettyserver; 



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lbs.nettyserver.dispatcher.HandlerDispatcher;
import com.lbs.nettyserver.handler.ChannelInitialHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
/** 
* @project:		nettygame
* @Title:		ClientInboundHandler.java
* @Package:		cpgame.nettygame
  @author: 		chenpeng
* @email: 		46731706@qq.com
* @date:		2015年8月27日 上午9:48:49 
* @description:
* @version:
*/
public class ClientInboundHandler extends ChannelInboundHandlerAdapter {
	private static final Log logger = LogFactory.getLog(ClientInboundHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if (msg instanceof HttpResponse) {
			HttpResponse response = (HttpResponse) msg;
		}
		if (msg instanceof HttpContent) {
			HttpContent content = (HttpContent) msg;
			ByteBuf buf = content.content();
			System.out.println(buf.toString(io.netty.util.CharsetUtil.UTF_8));
			buf.release();
		}
		if (msg instanceof ByteBuf) {
			ByteBuf messageData = (ByteBuf) msg;
			int commandId = messageData.readInt();
			int length = messageData.readInt();
			byte[] c = new byte[length];
			messageData.readBytes(c);
		}
	}
}

