package com.mindata.blockchain.socket.pbft.msg;

/**
 * pbft算法传输prepare和commit消息的载体
 * @author wuweifeng wrote on 2018/4/23.
 */
public class VoteMsg {
    /**
     * 当前投票状态（Prepare，commit）
     */
    private byte voteType;
    /**
     * 区块的hash
     */
    private String hash;
    /**
     * 区块的number
     */
    private int number;
    /**
     * 是哪个节点传来的
     */
    private String appId;
    /**
     * 是否同意
     */
    private boolean agree;

    @Override
    public String toString() {
        return "VoteMsg{" +
                "voteType=" + voteType +
                ", hash='" + hash + '\'' +
                ", number=" + number +
                ", appId='" + appId + '\'' +
                ", agree=" + agree +
                '}';
    }

    public byte getVoteType() {
        return voteType;
    }

    public void setVoteType(byte voteType) {
        this.voteType = voteType;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }
}
