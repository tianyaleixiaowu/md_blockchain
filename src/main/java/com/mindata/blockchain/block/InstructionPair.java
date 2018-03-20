package com.mindata.blockchain.block;

/**
 * 请求生成指令时，直接生成一对pair
 * @author wuweifeng wrote on 2018/3/20.
 */
public class InstructionPair {
    private Instruction instruction;
    private InstructionReverse instructionReverse;

    @Override
    public String toString() {
        return "InstructionPair{" +
                "instruction=" + instruction +
                ", instructionReverse=" + instructionReverse +
                '}';
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public InstructionReverse getInstructionReverse() {
        return instructionReverse;
    }

    public void setInstructionReverse(InstructionReverse instructionReverse) {
        this.instructionReverse = instructionReverse;
    }
}
