package com.mindata.blockchain.socket.client;

import com.mindata.blockchain.core.event.ClientRequestEvent;
import com.mindata.blockchain.socket.body.BaseBody;
import com.mindata.blockchain.socket.holder.RequestResponseMap;
import com.mindata.blockchain.socket.packet.BlockPacket;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.tio.utils.json.Json;

import java.util.ArrayList;

/**
 * client对外发请求时，先保存一下key
 * @author wuweifeng wrote on 2018/3/18.
 */
@Component
public class ClientRequestEventListener {
    @EventListener
    public void clientRequest(ClientRequestEvent clientRequestEvent) {
        BlockPacket blockPacket = (BlockPacket) clientRequestEvent.getSource();
        BaseBody baseBody = Json.toBean(new String(blockPacket.getBody()), BaseBody.class);
        RequestResponseMap.put(baseBody.getMessageId(), new ArrayList<>());
    }
}
