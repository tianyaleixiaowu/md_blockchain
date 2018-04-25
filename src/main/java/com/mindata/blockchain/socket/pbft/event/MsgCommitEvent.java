package com.mindata.blockchain.socket.pbft.event;

import com.mindata.blockchain.socket.pbft.msg.VoteMsg;
import org.springframework.context.ApplicationEvent;

/**
 * 消息已被验证，进入到Commit集合中
 * @author wuweifeng wrote on 2018/4/25.
 */
public class MsgCommitEvent extends ApplicationEvent {
    public MsgCommitEvent(VoteMsg source) {
        super(source);
    }
}
