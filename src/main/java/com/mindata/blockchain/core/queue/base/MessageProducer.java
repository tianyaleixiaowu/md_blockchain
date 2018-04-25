package com.mindata.blockchain.core.queue.base;

/**
 * @author wuweifeng wrote on 2018/4/20.
 */
public interface MessageProducer {
    void publish(BaseEvent baseEvent);
}
