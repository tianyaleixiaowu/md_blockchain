package com.mindata.blockchain.block;

import java.util.List;

/**
 * 区块body，里面存放交易的数组
 * @author wuweifeng wrote on 2018/2/28.
 */
public class BlockBody {
    private List<Instruction> transactions;

    public List<Instruction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Instruction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "BlockBody{" +
                "transactions=" + transactions +
                '}';
    }
}
