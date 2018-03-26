package com.mindata.blockchain.socket.holder;

import com.mindata.blockchain.socket.body.BaseBody;

/**
 * 收到请求后的回复，base类
 * @author wuweifeng wrote on 2018/3/17.
 */
public class BaseResponse<T> {
    /**
     * 请求方请求的msg的id
     */
    private String msgId;
    /**
     * 回复的时间戳
     */
    private Long timeStamp;
    /**
     * 自己是谁
     */
    private String appId;
    /**
     * 是否同意
     */
    private boolean agree;
    /**
     * 回复时带的对象
     */
    private T object;

    public BaseResponse(BaseBody baseBody) {
        this.msgId = baseBody.getResponseMsgId();
        this.timeStamp = baseBody.getTime();
        this.appId = baseBody.getAppId();
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "msgId='" + msgId + '\'' +
                ", timeStamp=" + timeStamp +
                ", appId='" + appId + '\'' +
                ", agree=" + agree +
                '}';
    }
}
