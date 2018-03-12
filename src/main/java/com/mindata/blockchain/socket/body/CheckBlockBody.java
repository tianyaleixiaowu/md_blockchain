package com.mindata.blockchain.socket.body;

/**
 * 校验block是否合法，同意、拒绝区块生成请求
 * @author wuweifeng wrote on 2018/3/12.
 */
public class CheckBlockBody extends BaseBody {
    /**
     * 是否OK
     */
    private boolean ok;
    /**
     * 附带的message
     */
    private String message;

    public CheckBlockBody() {
    }

    public CheckBlockBody(boolean ok, String message) {
        this.ok = ok;
        this.message = message;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CheckBlockBody{" +
                "ok=" + ok +
                ", message='" + message + '\'' +
                '}';
    }
}
