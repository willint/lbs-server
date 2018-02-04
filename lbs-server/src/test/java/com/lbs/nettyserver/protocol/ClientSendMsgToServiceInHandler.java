package com.lbs.nettyserver.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import net.sf.json.JSONObject;

import com.lbs.nettyserver.protocol.ClientProtocolInHandler.HeartbeatTask;

/**
 * Created by will on 18/1/4.
 */
public class ClientSendMsgToServiceInHandler extends SimpleChannelInboundHandler {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        if(msg != null && (msg instanceof  LbsMessage)){
        	
        	LbsMessage receiveMsg = (LbsMessage) msg;
            if(receiveMsg.getType() == LbsMessageConst.API_TYPE.BIZ_RESPONSE){
            	System.out.println("broad cast  content from server:"+ receiveMsg.getBody());
            	
            }else{
                //响应消息类型不是链接成功提示，传递给下个handler
                ctx.fireChannelRead(msg);

            }
        }else{
            ctx.close();
        }

    


    }
}
