package com.mindata.blockchain.block;

/**
 * 区块body内一条指令
 * @author wuweifeng wrote on 2018/3/2.
 */
public class Instruction {
    /**
     * 指令的操作，增删改
     */
    private Operation operation;
    /**
     * 操作的model对象，对应一张表
     */
    private String table;
    /**
     * 具体内容
     */
    private String json;
    /**
     * 时间戳
     */
    private Long timeStamp;
    /**
     * 操作人的公钥
     */
    private String publicKey;

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "operation=" + operation +
                ", table='" + table + '\'' +
                ", json='" + json + '\'' +
                ", timeStamp=" + timeStamp +
                ", publicKey='" + publicKey + '\'' +
                '}';
    }
}
