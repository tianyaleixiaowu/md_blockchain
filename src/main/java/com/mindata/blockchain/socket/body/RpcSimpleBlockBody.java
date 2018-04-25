package com.mindata.blockchain.socket.body;

/**
 * @author wuweifeng wrote on 2018/4/25.
 */
public class RpcSimpleBlockBody extends BaseBody {
    /**
     * blockHash
     */
    private String hash;

    public RpcSimpleBlockBody() {
        super();
    }

    public RpcSimpleBlockBody(String hash) {
        super();
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "RpcSimpleBlockBody{" +
                "hash='" + hash + '\'' +
                '}';
    }
}
