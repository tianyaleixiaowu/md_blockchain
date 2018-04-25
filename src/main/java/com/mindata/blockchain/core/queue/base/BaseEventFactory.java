package com.mindata.blockchain.core.queue.base;

import com.lmax.disruptor.EventFactory;

/**
 * @author wuweifeng wrote on 2018/4/20.
 */
public class BaseEventFactory implements EventFactory<BaseEvent> {
    @Override
    public BaseEvent newInstance() {
        return new BaseEvent();
    }

}
