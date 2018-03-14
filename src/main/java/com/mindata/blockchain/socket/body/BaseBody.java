package com.mindata.blockchain.socket.body;

import java.util.UUID;

/**
 *
 * @author tanyaowu
 * 2017年3月27日 上午12:12:17
 */
public class BaseBody {

	/**
	 * 消息发送时间
	 */
	private Long time = System.currentTimeMillis();
    /**
     * 每条消息的唯一id
     */
	private String messageId = UUID.randomUUID().toString();
    /**
     * 回复的哪条消息
     */
	private String responseMsgId;

    public BaseBody() {
    }

    /**
	 * @return the time
	 */
	public Long getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Long time) {
		this.time = time;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

    public String getResponseMsgId() {
        return responseMsgId;
    }

    public void setResponseMsgId(String responseMsgId) {
        this.responseMsgId = responseMsgId;
    }

    @Override
    public String toString() {
        return "BaseBody{" +
                "time=" + time +
                ", messageId='" + messageId + '\'' +
                ", responseMsgId='" + responseMsgId + '\'' +
                '}';
    }
}
