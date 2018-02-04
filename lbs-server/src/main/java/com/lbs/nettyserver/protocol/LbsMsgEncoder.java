package com.lbs.nettyserver.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by will on 17/12/17.
 *
 *  * <pre>
 * 自己定义的协议
 *  数据包格式
 * +——----——+——-----——+——----——+
 * |协议开始标志|  长度  | 数据   |
 * +——----——+——-----——+——----——+
 * 1.协议开始标志head_data，为int类型的数据，16进制表示为0X76
 * 2.传输数据的长度contentLength，int类型
 * 3.要传输的数据
 * </pre>
 */
public class LbsMsgEncoder extends MessageToByteEncoder<LbsMsg>{




    @Override
    protected void encode(ChannelHandlerContext ctx, LbsMsg msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getHead_data());
        out.writeInt(msg.getContentLength());
        out.writeBytes(msg.getContent());

    }
}
