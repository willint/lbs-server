package com.lbs.nettyserver.protocol;

import com.lbs.nettyserver.utils.sysutils.ByteUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by will on 17/12/17.
 */
public class ClientProtocolInHandler extends SimpleChannelInboundHandler {

    private static final Log logger = LogFactory.getLog(ClientProtocolInHandler.class);

    private volatile ScheduledFuture<?> heartbeat;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        LbsMessage receiveMsg = (LbsMessage) msg;

        if(msg != null && (msg instanceof  LbsMessage)){
            System.out.println("receive content from server:"+ receiveMsg.getBody());
            if(receiveMsg.getType() == LbsMessageConst.API_TYPE.CONNECT_RESPONSE){
                //通道链接成功
                System.out.println("channel connection succeed!");
                //发起心跳检测
                heartbeat = ctx.executor().scheduleAtFixedRate( new HeartbeatTask(ctx),
                        0,20*1000, TimeUnit.MILLISECONDS);
            }else if(receiveMsg.getType() == LbsMessageConst.API_TYPE.HEARTBEAT_RESPONSE){
                JSONObject heartResp = JSONObject.fromObject(receiveMsg.getBody());
                logger.info("heartbeat check task :"+heartResp.getString("timeStamp"));
            }if(receiveMsg.getType() == LbsMessageConst.API_TYPE.BIZ_RESPONSE){
            	System.out.println("broad cast  content from server:"+ receiveMsg.getBody());
            	
            }else{
                //响应消息类型不是链接成功提示，传递给下个handler
                ctx.fireChannelRead(msg);

            }
        }else{
//            ctx.close();
        }

    }

    public class HeartbeatTask implements Runnable {
        private final ChannelHandlerContext heartbeatContext;

        public HeartbeatTask(ChannelHandlerContext c){
            this.heartbeatContext = c;
        }

        @Override
        public void run() {
            LbsMessage message = buildHeartbeatMsg();
            heartbeatContext.writeAndFlush(message);
        }
    }

    private LbsMessage buildHeartbeatMsg(){
        LbsMessage heartbeatMessage = new LbsMessage();
        heartbeatMessage.setVerCode(LbsMessageConst.VER_CODE);
        heartbeatMessage.setType(LbsMessageConst.API_TYPE.HEARTBEAT_REQUEST);
        heartbeatMessage.setPriority(LbsMessageConst.DEFAULT_PRIORITY);

        JSONObject body = new JSONObject();
        String loginId = "76d0e2ea3bd74e2d9586fd81fd4f0a97";
        String sessionCode = "1234qwert";
        SimpleDateFormat timeFormat=new SimpleDateFormat("yyyyMMDDHHmmss");
        String timeStamp = timeFormat.format(new Date());
        body.put("loginId",loginId);
        body.put("sessionCode",sessionCode);
        body.put("timeStamp",timeStamp);
        heartbeatMessage.setBody(body);

        return heartbeatMessage;
    }
}
