package com.mindata.blockchain.block;

import java.util.List;

/**
 * 区块body，里面存放交易的数组
 * @author wuweifeng wrote on 2018/2/28.
 */
public class BlockBody {
    private List<Instruction> instructions;
    private List<InstructionReverse> instructionReverses;

    @Override
    public String toString() {
        return "BlockBody{" +
                "instructions=" + instructions +
                ", instructionReverses=" + instructionReverses +
                '}';
    }

    public List<InstructionReverse> getInstructionReverses() {
        return instructionReverses;
    }

    public void setInstructionReverses(List<InstructionReverse> instructionReverses) {
        this.instructionReverses = instructionReverses;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }
}
