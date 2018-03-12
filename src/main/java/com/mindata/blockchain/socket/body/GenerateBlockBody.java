package com.mindata.blockchain.socket.body;

/**
 * @author wuweifeng wrote on 2018/3/12.
 */
public class GenerateBlockBody extends BaseBody {
    /**
     * blockJson
     */
    private String blockJson;

    public GenerateBlockBody() {
    }

    public GenerateBlockBody(String blockJson) {
        this.blockJson = blockJson;
    }

    public String getBlockJson() {
        return blockJson;
    }

    public void setBlockJson(String blockJson) {
        this.blockJson = blockJson;
    }
}
