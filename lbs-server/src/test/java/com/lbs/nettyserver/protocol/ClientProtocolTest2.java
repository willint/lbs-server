package com.lbs.nettyserver.protocol;

import com.lbs.nettyserver.utils.sysutils.ByteUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.sf.json.JSONObject;

/**
 * Created by will on 17/12/17.
 */

public class ClientProtocolTest2 {


    public void connect (String host,int port){
        EventLoopGroup workergroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
        bootstrap.group(workergroup).channel(NioSocketChannel.class);
        ChannelFuture future = null;

        try{

            bootstrap.handler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel ch) throws Exception {
//                    ch.pipeline().addLast("decoder",new LbsMessageDecoder(Integer.MAX_VALUE,6,4,0,0,false));
                    ch.pipeline().addLast("encoder",new LbsMessageEncoder());
                    ch.pipeline().addLast("handler",new ClientProtocolInHandler());
                }
            });

            future = bootstrap.connect(host,port).sync();
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
            new Thread(new MySocketProcess("client test private protocol", future)).start();


        }catch (Exception e){
            e.printStackTrace();

        }finally {
//            if(null != future){
//                future.channel().closeFuture();
//            }
//            workergroup.shutdownGracefully();
        }

    }

    public class MySocketProcess implements Runnable{

        private String msg;
        private ChannelFuture f;

        public MySocketProcess(String msg, ChannelFuture f){
            this.msg = msg;
            this.f = f;
            System.out.println(msg);
        }

        @Override
        public void run() {
            try {
                JSONObject body = new JSONObject();
                JSONObject fromInfo = new JSONObject();
                JSONObject msgContent = new JSONObject();
                fromInfo.put("userId","123");
                fromInfo.put("loginId","123");
                fromInfo.put("nickName","熊猫");

                msgContent.put("type","12");
                msgContent.put("text","来自客户端的消息");
                body.put("fromInfo",fromInfo);
                body.put("msgContent",msgContent);
                String content = "";
                int time = 1;
                while(true){
                    content = msg+" time = " +time;
                    LbsMessage message = new LbsMessage();
                    message.setVerCode(0xbcdacd01);
                    message.setType((byte)0);
                    message.setPriority((byte)1);
                    body.put("content",content);
                    int length = ByteUtil.object2bytes(body).length;
                    message.setLength(length);

                    message.setBody(body);

                    f.channel().writeAndFlush(message);

                    try {
                        time+=1;
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    public static  void main(String[] args){
        ClientProtocolTest2 client = new ClientProtocolTest2();
        client.connect("127.0.0.1",8192);
    }

}
