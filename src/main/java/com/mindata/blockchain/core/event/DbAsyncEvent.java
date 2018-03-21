package com.mindata.blockchain.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * 同步block到sqlite事件
 * @author wuweifeng wrote on 2018/3/21.
 */
public class DbAsyncEvent extends ApplicationEvent {
    public DbAsyncEvent(Object source) {
        super(source);
    }
}
