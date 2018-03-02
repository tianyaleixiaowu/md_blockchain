package com.mindata.blockchain.block;

/**
 * 区块头
 * @author wuweifeng wrote on 2018/2/27.
 */
public class BlockHeader {
    /**
     * 版本号
     */
    private int version;
    /**
     * 上一区块的hash
     */
    private String hashPreviousBlock;
    /**
     * merkle tree根节点hash
     */
    private String hashMerkleRoot;
    /**
     * 生成该区块的账号地址
     */
    private CoinBase coinbase;
    /**
     * 区块的序号
     */
    private int number;
    /**
     * 时间戳
     */
    private long timeStamp;
    /**
     * 32位随机数
     */
    private long nonce;

    @Override
    public String toString() {
        return "BlockHeader{" +
                "version=" + version +
                ", hashPreviousBlock='" + hashPreviousBlock + '\'' +
                ", hashMerkleRoot='" + hashMerkleRoot + '\'' +
                ", timeStamp=" + timeStamp +
                ", nonce=" + nonce +
                '}';
    }
}
