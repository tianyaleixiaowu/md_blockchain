package com.mindata.blockchain.socket.body;

/**
 * @author wuweifeng wrote on 2018/4/26.
 */
public class BlockHash {
    private String hash;
    private String prevHash;
    private String appId;

    public BlockHash() {
    }

    public BlockHash(String hash, String prevHash, String appId) {
        this.hash = hash;
        this.prevHash = prevHash;
        this.appId = appId;
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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
