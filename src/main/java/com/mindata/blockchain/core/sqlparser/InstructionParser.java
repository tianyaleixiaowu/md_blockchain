package com.mindata.blockchain.core.sqlparser;

import com.mindata.blockchain.block.InstructionBase;

/**
 * @author wuweifeng wrote on 2018/3/21.
 */
public interface InstructionParser {
    boolean parse(InstructionBase instructionBase);
}
