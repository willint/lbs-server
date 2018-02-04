package com.lbs.nettyserver;

import java.net.URI;

import org.springframework.expression.spel.ast.IntLiteral;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

/**
 */
public class ClientTest2 {
	public void connect(String host, int port,int userID,String type) throws Exception {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
			String msg = "yo, i'm test client 2";
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			
			try {
				if("http".equals(type)){
					b.option(ChannelOption.SO_KEEPALIVE, true);
					b.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {

							// 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
							ch.pipeline().addLast(new HttpResponseDecoder());
							// 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
							ch.pipeline().addLast(new HttpRequestEncoder());
							ch.pipeline().addLast(new ClientInboundHandler());

						}
					});
					ChannelFuture f = b.connect(host, port).sync();
					b.option(ChannelOption.TCP_NODELAY, true);
					b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);

					URI uri = new URI("http://" + host + ":" + port+"?userID="+userID);

					DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,
							uri.toASCIIString(), Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
					// 构建http请求
					request.headers().set(HttpHeaderNames.HOST, host);
					request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
					request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
					// 发送http请求
					f.channel().write(request);
					f.channel().flush();
					f.channel().closeFuture().sync();
				}
				
				if("socket".equals(type)){
					b.handler(new ChannelInitializer<Channel>() {

						@Override
						protected void initChannel(Channel ch) throws Exception {
							ch.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
							// 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
							ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(4));
							ch.pipeline().addLast("handler", new ClientInboundHandler());
						}
					});
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
	}
	
	public static void main(String[] args) throws Exception {
		ClientTest2 client = new ClientTest2();
		int userid = client.hashCode();
		System.out.println("userid="+userid);
//		client.connect("127.0.0.1", 8080,userid);
		String type = "http";
//		String type = "http";
		client.connect("127.0.0.1", 8080,userid,type);
	}
}
