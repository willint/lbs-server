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
public class ClientTest1 {
	public  void connect(String host, int port,int userID ,String type) throws Exception {
			EventLoopGroup workerGroup = new NioEventLoopGroup();
			String msg = "hi~ everybody, i'm test client 1. type = "+type;
			
			Bootstrap b = new Bootstrap();
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.group(workerGroup).channel(NioSocketChannel.class);
			
			if("http".equals(type)){
				try {
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
					URI uri = new URI("http://:" + port+"?userID="+userID);
					new Thread(new MyRunProcess(f,uri,msg)).start();
				} catch (Exception e) {
				}
			}
			
			if("socket".equals(type) ){
				try {
					
					b.handler(new ChannelInitializer<Channel>() {
						@Override
						protected void initChannel(Channel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							pipeline.addLast("frameDecoder",
									new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
							pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
							pipeline.addLast("handler", new ClientInboundHandler());
						}
					});
					
					ChannelFuture f = b.connect(host, port).sync();
					new Thread(new MySocketProcess(msg, f)).start();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
	}
	
	public static void main(String[] args) throws Exception {
			ClientTest1 client = new ClientTest1();
			int userid = client.hashCode();
			System.out.println("userid="+userid);
			String type = "http";
//			String type = "socket";
			client.connect("127.0.0.1", 8080,userid,type);
		
	}
	
	public class MySocketProcess implements Runnable{
		private String msg;
		private ChannelFuture f;
		
		public MySocketProcess(String msg, ChannelFuture f){
			this.msg = msg;
			this.f = f;
		}

		@Override
		public void run() {
			ByteBuf messageBuf = Unpooled.buffer();
			try {
				String content = "";
				int time = 1;
				while(true){
					content = msg+" time = " +time;
					
					messageBuf.writeBytes(content.getBytes());
					f.channel().writeAndFlush(messageBuf);
					
					try {
						time+=1;
						Thread.sleep(2000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
	}
	
	public class MyRunProcess implements Runnable{
		private ChannelFuture future;
		private URI uri;
		private String content ;
		
		public MyRunProcess(ChannelFuture f,URI uri,String msg){
			this.future = f;
			this.uri = uri;
			this.content= msg;
		}
		@Override
		public void run() {
			int times = 0;
			while(true){
				try {
					DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,
							uri.toASCIIString(), Unpooled.copiedBuffer(content+" times = "+times, CharsetUtil.UTF_8));
					// 构建http请求
					request.headers().set(HttpHeaderNames.HOST, "120.76.221.92");
					request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
					request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
					// 发送http请求
					future.channel().write(request);
					future.channel().flush();
//					future.channel().closeFuture().sync();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
					times+=1;
					Thread.sleep(2000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
