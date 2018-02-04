package com.lbs.nettyserver.protocol;

import com.lbs.nettyserver.utils.sysutils.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by will on 17/12/26.
 */
public class LbsMessageEncoder extends MessageToByteEncoder<LbsMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, LbsMessage msg, ByteBuf out) throws Exception {
    	
    	byte[] data = ByteUtil.object2bytes(msg.getBody());
    	msg.setLength(data.length);
    	
        out.writeInt(msg.getVerCode());
        out.writeByte(msg.getType());
        out.writeByte(msg.getPriority());
        out.writeInt(msg.getLength());
        out.writeBytes(data);


    }
}
