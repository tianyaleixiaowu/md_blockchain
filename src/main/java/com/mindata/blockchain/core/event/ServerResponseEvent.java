package com.mindata.blockchain.core.event;

import com.mindata.blockchain.socket.packet.BlockPacket;
import org.springframework.context.ApplicationEvent;

/**
 * 客户端对外发请求时会触发该Event
 * @author wuweifeng wrote on 2018/3/17.
 */
public class ServerResponseEvent extends ApplicationEvent {
    public ServerResponseEvent(BlockPacket blockPacket) {
        super(blockPacket);
    }
}
