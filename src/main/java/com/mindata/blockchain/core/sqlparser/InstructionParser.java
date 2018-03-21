package com.mindata.blockchain.core.sqlparser;

import com.mindata.blockchain.block.Instruction;

/**
 * @author wuweifeng wrote on 2018/3/21.
 */
public interface InstructionParser {
    boolean parse(Instruction instruction);
}
