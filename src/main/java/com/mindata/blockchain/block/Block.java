package com.mindata.blockchain.block;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * 区块
 * @author wuweifeng wrote on 2018/2/27.
 */
public class Block {
    /**
     * 区块头
     */
    private BlockHeader blockHeader;
    /**
     * 区块body
     */
    private BlockBody blockBody;
    /**
     * 该区块的hash
     */
    private String hash;

    /**
     * 根据该区块所有属性计算sha256
     * @return
     * sha256hex
     */
    private String calculateHash() {
        return DigestUtil.sha256Hex(
                        blockHeader.toString() + blockBody.toString()
        );
    }

    public BlockHeader getBlockHeader() {
        return blockHeader;
    }

    public void setBlockHeader(BlockHeader blockHeader) {
        this.blockHeader = blockHeader;
    }

    public BlockBody getBlockBody() {
        return blockBody;
    }

    public void setBlockBody(BlockBody blockBody) {
        this.blockBody = blockBody;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "Block{" +
                "blockHeader=" + blockHeader +
                ", blockBody=" + blockBody +
                ", hash='" + hash + '\'' +
                '}';
    }
}
