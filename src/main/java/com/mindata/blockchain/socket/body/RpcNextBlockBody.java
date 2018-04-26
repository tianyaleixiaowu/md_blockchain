package com.mindata.blockchain.socket.body;

/**
 * 请求next block时用的包装类
 * @author wuweifeng wrote on 2018/4/25.
 */
public class RpcNextBlockBody extends BaseBody {
    /**
     * blockHash
     */
    private String hash;
    /**
     * 上一个hash
     */
    private String prevHash;

    public RpcNextBlockBody() {
        super();
    }

    public RpcNextBlockBody(String hash, String prevHash) {
        super();
        this.hash = hash;
        this.prevHash = prevHash;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "RpcNextBlockBody{" +
                "hash='" + hash + '\'' +
                ", prevHash='" + prevHash + '\'' +
                '}';
    }
}
