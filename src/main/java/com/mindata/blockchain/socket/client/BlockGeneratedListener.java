package com.mindata.blockchain.socket.client;

import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.core.event.AddBlockEvent;
import com.mindata.blockchain.socket.body.RpcBlockBody;
import com.mindata.blockchain.socket.packet.BlockPacket;
import com.mindata.blockchain.socket.packet.PacketBuilder;
import com.mindata.blockchain.socket.packet.PacketType;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 本地新生成区块后，需要通知所有group内的节点
 * @author wuweifeng wrote on 2018/3/21.
 */
@Component
public class BlockGeneratedListener {
    @Resource
    private PacketSender packetSender;

    @Order(2)
    @EventListener(AddBlockEvent.class)
    public void blockGenerated(AddBlockEvent addBlockEvent) {
        BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.GENERATE_COMPLETE_REQUEST).setBody(new
                RpcBlockBody((Block) addBlockEvent.getSource())).build();

        //广播给其他人做验证
        packetSender.sendGroup(blockPacket);
    }
}
