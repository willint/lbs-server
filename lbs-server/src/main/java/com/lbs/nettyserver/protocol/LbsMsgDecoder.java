package com.lbs.nettyserver.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by will on 17/12/17.
 * * <pre>
 * 自己定义的协议
 *  数据包格式
 * +——----——+——-----——+——----——+
 * |协议开始标志|  长度             |   数据       |
 * +——----——+——-----——+——----——+
 * 1.协议开始标志head_data，为int类型的数据，16进制表示为0X76
 * 2.传输数据的长度contentLength，int类型
 * 3.要传输的数据,长度不应该超过2048，防止socket流的攻击
 * </pre>
 */
public class LbsMsgDecoder extends ByteToMessageDecoder {


    public final int BASE_LENGTH = 4 + 4;


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        if(in.readableBytes() >= BASE_LENGTH){

            if(in.readableBytes() > 2048){
                in.skipBytes(in.readableBytes());
            }

            int beginReader;

            while(true){
                beginReader = in.readerIndex();
                in.markReaderIndex();

                //读到协议标志
                if(in.readInt() == 0X76){
                    break;
                }

                in.resetReaderIndex();
                in.readByte();

                if(in.readableBytes() < BASE_LENGTH){
                    return;
                }
            }
            int length = in.readInt();
            if(in.readableBytes() < length){
                in.readerIndex(beginReader);
                return;
            }
            byte[] data = new byte[length];
            in.readBytes(data);
            LbsMsg message = new LbsMsg(data.length,data);
            out.add(message);
        }

    }
}
