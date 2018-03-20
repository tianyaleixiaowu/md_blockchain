package com.mindata.blockchain.block;

/**
 * 一条指令用来回滚时所用
 * @author wuweifeng wrote on 2018/3/2.
 */
public class InstructionReverse extends Instruction {

    @Override
    public String toString() {
        return "Instruction{" +
                "operation=" + getOperation() +
                ", table='" + getTable() + '\'' +
                ", json='" + getJson() + '\'' +
                ", instructionId='" + getInstructionId() + '\'' +
                ", timeStamp=" + getTimeStamp() +
                ", publicKey='" + getPublicKey() + '\'' +
                ", sign='" + getSign() + '\'' +
                ", hash='" + getHash() + '\'' +
                '}';
    }
}
