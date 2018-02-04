package com.lbs.nettyserver.protocol;

/**
 * Created by will on 17/12/17.
 *
 * * <pre>
 * 自己定义的协议
 *  数据包格式
 * +——----——+——-----——+——----——+
 * |协议开始标志|  长度             |   数据       |
 * +——----——+——-----——+——----——+
 * 1.协议开始标志head_data，为int类型的数据，16进制表示为0X76
 * 2.传输数据的长度contentLength，int类型
 * 3.要传输的数据
 * </pre>
 */
public class LbsMsg {

    /**
     * 消息头标志
     */
    private int head_data = 0X76;

    /**
     * 消息长度
     */
    private int contentLength ;


    /**
     * 消息内容byte
     */
    private byte[] content;


    public LbsMsg(int contentLength, byte[] content){
        this.contentLength = contentLength;
        this.content = content;
    }

    public int getHead_data() {
        return head_data;
    }

    public void setHead_data(int head_data) {
        this.head_data = head_data;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
