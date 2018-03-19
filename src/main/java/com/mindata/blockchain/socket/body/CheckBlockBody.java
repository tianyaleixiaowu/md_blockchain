package com.mindata.blockchain.socket.body;

import com.mindata.blockchain.block.Block;

/**
 * 校验block是否合法，同意、拒绝区块生成请求
 * @author wuweifeng wrote on 2018/3/12.
 */
public class CheckBlockBody extends BaseBody {
    /**
     * 0是正常同意，-1区块number错误，-2没有权限，-3hash错误，-4时间错误，-10不合法的next block
     */
    private int code;
    /**
     * 附带的message
     */
    private String message;
    /**
     * block的hash，用来确定是哪一个block
     */
    private Block block;

    public CheckBlockBody() {
    }

    public CheckBlockBody(int code, String message) {
        this(code, message, null);
    }

    public CheckBlockBody(int code, String message, Block block) {
        this.code = code;
        this.message = message;
        this.block = block;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    @Override
    public String toString() {
        return "CheckBlockBody{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", block='" + block + '\'' +
                '}';
    }
}
