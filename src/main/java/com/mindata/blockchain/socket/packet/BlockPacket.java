package com.mindata.blockchain.socket.packet;

import org.tio.core.intf.Packet;

/**
 * @author wuweifeng wrote on 2018/3/9.
 */
public class BlockPacket extends Packet {
    private static final long serialVersionUID = -5481926483435771100L;
    /**
     *  消息头的长度 1+4
     */
    public static final int HEADER_LENGTH = 5;
    /**
     * 消息类型，其值在Type中定义
     */
    private byte type;

    private byte[] body;

    public BlockPacket() {
        super();
    }

    /**
     * @param type type
     * @param body body
     * @author tanyaowu
     */
    public BlockPacket(byte type, byte[] body) {
        super();
        this.type = type;
        this.body = body;
    }

    /**
     * @return the body
     */
    public byte[] getBody() {
        return body;
    }

    /**
     * @return the type
     */
    public byte getType() {
        return type;
    }

    @Override
    public String logstr() {
        return "" + type;
    }

    /**
     * @param body
     *         the body to set
     */
    public void setBody(byte[] body) {
        this.body = body;
    }

    /**
     * @param type
     *         the type to set
     */
    public void setType(byte type) {
        this.type = type;
    }
}
