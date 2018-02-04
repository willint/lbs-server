package com.lbs.nettyserver.protocol;

import java.math.BigDecimal;

import com.lbs.nettyserver.model.common.FromInfo;
import com.lbs.nettyserver.model.sys.MediaTypeConstants;
import com.lbs.nettyserver.model.sys.MessageSourceConstants;
import com.lbs.nettyserver.utils.sysutils.ByteUtil;
import com.lbs.nettyserver.utils.sysutils.JsonPluginsUtil;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.sf.json.JSONObject;

/**
 * Created by will on 17/12/17.
 */

public class ClientProtocolTestConnect {


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
                    ch.pipeline().addLast("timeout",new ReadTimeoutHandler(50));
                    ch.pipeline().addLast("decoder",new LbsMessageDecoder());
//                    ch.pipeline().addLast("decoder",new LbsMessageDecoder(Integer.MAX_VALUE,6,4,0,0,false));
                    ch.pipeline().addLast("encoder",new LbsMessageEncoder());
                    ch.pipeline().addLast("handler",new ClientProtocolInHandler());//心跳消息处理
//                    ch.pipeline().addLast("pushMsgHandler",new ClientSendMsgToServiceInHandler());//广播消息接收
                }
            });

            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
            future = bootstrap.connect(host,port).sync();
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
                int time = 1;
                while(true){
                    LbsMessage message = new LbsMessage();
                    message.setVerCode(LbsMessageConst.VER_CODE);
                    message.setType(LbsMessageConst.API_TYPE.CONNECT_REQUEST);
                    message.setPriority(LbsMessageConst.DEFAULT_PRIORITY);
                    body.put("loginId","76d0e2ea3bd74e2d9586fd81fd4f0a97");
                    body.put("userId","7ca167e581b04840a631a805515d8e3c");
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
                
                /**
                 * 发起第一次广播消息 
                 */
                int count = 1;
                while(true){
                	LbsMessage message = new LbsMessage();
                	message.setVerCode(LbsMessageConst.VER_CODE);
                    message.setType(LbsMessageConst.API_TYPE.BIZ_REQUEST);
                    message.setPriority(LbsMessageConst.DEFAULT_PRIORITY);
                    
                    JSONObject msgbody = new JSONObject();
                    JSONObject messageContent = new JSONObject();
                    FromInfo from2 = new FromInfo();
                    from2.setLoginId("76d0e2ea3bd74e2d9586fd81fd4f0a97");
                    from2.setUserId("7ca167e581b04840a631a805515d8e3c");
                    
                    from2.setJd(new BigDecimal(102.74154));
                    from2.setWd(new BigDecimal(25.062706));
                    from2.setLocationName("location address");
                    from2.setNickName("testUser2");
                    msgbody.put("msgSource", MessageSourceConstants.MSG_VOMIT.FREE_CHAT);
                    
                    messageContent.put("type", MediaTypeConstants.TXT);
                    messageContent.put("txt", "this is test text .count="+count);
                    msgbody.put("msgContent", messageContent);
                    msgbody.put("fromInfo", JSONObject.fromObject(JsonPluginsUtil.beanToJson(from2)));
                    
                    message.setBody(msgbody);
                    f.channel().writeAndFlush(message);
                    
                    try {
                    	count+=8;
                        Thread.sleep(8*1000);
//                        break;
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
        ClientProtocolTestConnect client = new ClientProtocolTestConnect();
        client.connect("127.0.0.1",8191);
//        client.connect("120.76.28.160",8191);
    }

}
