package com.lbs.nettyserver.protocol;

/**
 * Created by will on 17/12/26.
 * netty私有消息协议头
 */
public class LbsHeader {

    /**
     * 校验码
     */
    private int verCode;


    /**
     * 消息长度，不包含header部分
     */
    private int length;


    /**
     * 消息类型
     */
    private byte type;


    /**
     * 优先级
     */
    private byte priority;


    /**
     * 身份识别码
     */
    private long privateID;

    public final int getVerCode() {
        return verCode;
    }

    public final  void setVerCode(int verCode) {
        this.verCode = verCode;
    }

    public final int getLength() {
        return length;
    }

    public final void setLength(int length) {
        this.length = length;
    }

    public final byte getType() {
        return type;
    }

    public final void setType(byte type) {
        this.type = type;
    }

    public final byte getPriority() {
        return priority;
    }

    public final void setPriority(byte priority) {
        this.priority = priority;
    }

    public final long getPrivateID() {
        return privateID;
    }

    public final void setPrivateID(long privateID) {
        this.privateID = privateID;
    }
}
