package com.lbs.nettyserver.handler;

import com.lbs.nettyserver.exception.BusinessException;
import com.lbs.nettyserver.protocol.LbsHeader;
import com.lbs.nettyserver.protocol.LbsMessage;
import com.lbs.nettyserver.protocol.LbsMessageConst;
import com.lbs.nettyserver.protocol.LbsMsg;
import com.lbs.nettyserver.service.sysservice.LbsMessageHandleSerivce;
import com.lbs.nettyserver.utils.sysutils.ByteUtil;
import io.netty.channel.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by will on 17/12/17.
 */

@ChannelHandler.Sharable
@Component
public class LbsMsgHandler extends SimpleChannelInboundHandler<Object> {

    @Autowired
    private LbsMessageHandleSerivce lbsMessageHandleSerivce;

    private static final Log logger = LogFactory.getLog(LbsMsgHandler.class);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

//        LbsMsg message = (LbsMsg) msg;
//        logger.info("Server receive lbs message:"+new String(message.getContent()));
//
//        String sts= "message accepted..";
//        LbsMsg response = new LbsMsg(sts.length(),sts.getBytes());
//
//        ctx.writeAndFlush(response);



        if(msg instanceof  LbsMessage){

            LbsMessage message = (LbsMessage) msg;
            logger.info("Server receive lbs message:"+"vercode:"+message.getVerCode()
                    +"length:"+message.getLength()+"type:"+message.getType()
                    +"priority:"+message.getPriority());

            //处理链接请求
            if(message.getType() == LbsMessageConst.API_TYPE.CONNECT_REQUEST){
                connectChannelHandle(message,ctx);
            }else if(message.getType() == LbsMessageConst.API_TYPE.HEARTBEAT_REQUEST){
                //心跳请求处理
                heartBeatHandle(message,ctx);
            }else if(message.getType() == LbsMessageConst.API_TYPE.BIZ_REQUEST ){
            	//消息广播
            	broadcastMsgHandle(message,ctx);
            }

        }else{
            //消息解析异常，关闭连接
            ctx.close();
            throw new BusinessException("消息解析实体失败");


        }


    }

    /**
     * 处理链接
     * @param message
     * @param ctx
     */
    private void connectChannelHandle(LbsMessage message ,ChannelHandlerContext ctx ){
        try{
            lbsMessageHandleSerivce.createConnectChannel(message,ctx);
        }catch (Exception e){
            ctx.close();
            logger.error(e.getStackTrace());
        }
    }

    /**
     *
     * @param message
     * @param ctx
     */
    private void heartBeatHandle(LbsMessage message ,ChannelHandlerContext ctx){

        try{
            lbsMessageHandleSerivce.heartbeatCheck(message,ctx);
        }catch (Exception e){
            logger.error(e.getStackTrace());
        }

    }
    
    
    /**
     * 
     * @param message
     * @param ctx
     */
    private void broadcastMsgHandle(LbsMessage message ,ChannelHandlerContext ctx){
    	try {
    		lbsMessageHandleSerivce.broadcastMessage(message);
			
		} catch (Exception e) {
			logger.error(e);
		}
    }
}
