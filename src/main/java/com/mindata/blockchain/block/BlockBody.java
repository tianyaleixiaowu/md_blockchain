package com.mindata.blockchain.block;

import java.util.List;

/**
 * 区块body，里面存放交易的数组
 * @author wuweifeng wrote on 2018/2/28.
 */
public class BlockBody {
    private List<Instruction> instructions;

    @Override
    public String toString() {
        return "BlockBody{" +
                "instructions=" + instructions +
                '}';
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }
}
