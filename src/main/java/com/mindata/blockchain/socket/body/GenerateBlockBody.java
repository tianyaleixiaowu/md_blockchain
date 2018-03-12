package com.mindata.blockchain.socket.body;

import com.mindata.blockchain.block.Block;

/**
 * @author wuweifeng wrote on 2018/3/12.
 */
public class GenerateBlockBody extends BaseBody {
    /**
     * blockJson
     */
    private Block block;

    public GenerateBlockBody() {
        super();
    }

    public GenerateBlockBody(Block block) {
        super();
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    @Override
    public String toString() {
        return "GenerateBlockBody{" +
                "block=" + block +
                '}';
    }
}
