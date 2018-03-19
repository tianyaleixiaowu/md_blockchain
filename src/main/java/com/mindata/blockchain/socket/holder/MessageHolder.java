package com.mindata.blockchain.socket.holder;

import com.mindata.blockchain.common.AppId;

/**
 * 用来"发出请求后，对于各响应的状态的存储"
 * @author wuweifeng wrote on 2018/3/14.
 */
public class MessageHolder {
    /**
     * 消息发出时的时间
     */
    private Long timeStamp = System.currentTimeMillis();
    /**
     * 客户的唯一标志
     */
    private String appId = AppId.value;
    /**
     * 消息的回应
     */
    private BaseResponse baseResponse;

    public MessageHolder() {
    }

    public MessageHolder(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public BaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(BaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
