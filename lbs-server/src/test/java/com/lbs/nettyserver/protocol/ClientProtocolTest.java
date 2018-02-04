package com.lbs.nettyserver.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by will on 17/12/17.
 */

public class ClientProtocolTest {


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
                    ch.pipeline().addLast("decoder",new LbsMsgDecoder());
                    ch.pipeline().addLast("encoder",new LbsMsgEncoder());
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
                String content = "";
                int time = 1;
                while(true){
                    content = msg+" time = " +time;
                    System.out.println("content:"+content);
                    LbsMsg message = new LbsMsg(content.length(),content.getBytes());
                    f.channel().writeAndFlush(message);

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

    public static  void main(String[] args){
        ClientProtocolTest client = new ClientProtocolTest();
        client.connect("127.0.0.1",8192);
    }

}
