package com.mindata.blockchain.socket.pbft.listener;

import com.mindata.blockchain.common.AppId;
import com.mindata.blockchain.socket.body.VoteBody;
import com.mindata.blockchain.socket.client.PacketSender;
import com.mindata.blockchain.socket.packet.BlockPacket;
import com.mindata.blockchain.socket.packet.PacketBuilder;
import com.mindata.blockchain.socket.packet.PacketType;
import com.mindata.blockchain.socket.pbft.VoteType;
import com.mindata.blockchain.socket.pbft.event.MsgPrepareEvent;
import com.mindata.blockchain.socket.pbft.msg.VoteMsg;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/4/25.
 */
@Component
public class PrepareEventListener {
    @Resource
    private PacketSender packetSender;

    /**
     * block已经开始进入Prepare状态
     *
     * @param msgPrepareEvent
     *         msgIsPrepareEvent
     */
    @EventListener
    public void msgIsPrepare(MsgPrepareEvent msgPrepareEvent) {
        VoteMsg voteMsg = (VoteMsg) msgPrepareEvent.getSource();
        voteMsg.setVoteType(VoteType.PREPARE);
        voteMsg.setAppId(AppId.value);

        //群发消息，通知别的节点，我已对该Block Prepare
        BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.PBFT_VOTE).setBody(new
                VoteBody(voteMsg)).build();

        //广播给所有人我已Prepare
        packetSender.sendGroup(blockPacket);
    }
}
