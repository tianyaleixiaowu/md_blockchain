package com.mindata.blockchain.core.requestbody;

/**
 * @author wuweifeng wrote on 2018/3/7.
 */
public class InstructionBody {
    /**
     * 指令的操作，增删改
     */
    private byte operation;
    /**
     * 操作的model对象，对应一张表
     */
    private String table;
    /**
     * 具体内容
     */
    private String json;
    /**
     * 私钥
     */
    private String privateKey;
    /**
     * 公钥
     */
    private String publicKey;

    @Override
    public String toString() {
        return "InstructionBody{" +
                "operation=" + operation +
                ", table='" + table + '\'' +
                ", json='" + json + '\'' +
                ", privateKey='" + privateKey + '\'' +
                ", publicKey='" + publicKey + '\'' +
                '}';
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public byte getOperation() {
        return operation;
    }

    public void setOperation(byte operation) {
        this.operation = operation;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
