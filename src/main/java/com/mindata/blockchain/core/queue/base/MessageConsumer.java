package com.mindata.blockchain.core.queue.base;

/**
 * @author wuweifeng wrote on 2018/4/20.
 */
public interface MessageConsumer {
    void receive(BaseEvent baseEvent) throws Exception;
}
