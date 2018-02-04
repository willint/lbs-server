package com.lbs.nettyserver.protocol;

import java.util.List;

import com.lbs.nettyserver.utils.sysutils.ByteUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Created by will on 17/12/26.
 * 
 */
public class LbsMessageDecoder extends ByteToMessageDecoder  {


    //判断传送客户端传送过来的数据是否按照协议传输，头部信息的大小应该是 int+byte+byte+int = 4+1+1+4 = 6
    private static final int HEADER_SIZE = 10;


    /**
     *
     * @param maxFrameLength 解码时，处理每个帧数据的最大长度
     * @param lengthFieldOffset 该帧数据中，存放该帧数据的长度的数据的起始位置
     * @param lengthFieldLength 记录该帧数据长度的字段本身的长度
     * @param lengthAdjustment 修改帧数据长度字段中定义的值，可以为负数
     * @param initialBytesToStrip 解析的时候需要跳过的字节数
     * @param failFast 为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，为false，读取完整个帧再报异常
     */
//    public LbsMessageDecoderV2(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
//        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
//    }


	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		if(in.readableBytes() >= HEADER_SIZE){
			int verCode ;

            if(in.readableBytes() > 2048){
                in.skipBytes(in.readableBytes());
            }

            int beginReader;

            while(true){
                beginReader = in.readerIndex();
                in.markReaderIndex();

                //读到协议标志
                if(in.readInt() == LbsMessageConst.VER_CODE){
                	verCode = LbsMessageConst.VER_CODE;
                    break;
                }

                in.resetReaderIndex();
                in.readByte();

                if(in.readableBytes() < HEADER_SIZE){
                    return;
                }
            }
            //业务类型
            byte type = in.readByte();
            
            //优先级
            byte priority = in.readByte();
            
            //body消息的长度
            int length = in.readInt();
            
            if(in.readableBytes() < length){
                in.readerIndex(beginReader);
                return;
            }
            byte[] data = new byte[length];
            in.readBytes(data);
            Object body = ByteUtil.byte2object(data);
            LbsMessage message = new LbsMessage(verCode,type,priority,length,body);
            out.add(message);
		}
		
	}
}
