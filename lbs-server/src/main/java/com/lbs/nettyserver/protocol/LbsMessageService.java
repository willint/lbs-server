package com.lbs.nettyserver.protocol;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by will on 18/1/2.
 * 消息核心服务
 */
public interface LbsMessageService {

    //创建通道链接
    public void createConnectChannel(LbsMessage message,ChannelHandlerContext ctx) throws Exception;

    //断开通道
    public void disconnectChannel()throws Exception;

    //心跳检测
    public void heartbeatCheck(LbsMessage message,ChannelHandlerContext ctx)throws Exception;

    //广播消息
    public void broadcastMessage(LbsMessage message)throws Exception;



}
