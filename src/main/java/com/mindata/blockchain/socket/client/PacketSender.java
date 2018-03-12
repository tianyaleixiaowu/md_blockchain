package com.mindata.blockchain.socket.client;

import com.mindata.blockchain.socket.packet.BlockPacket;
import org.springframework.stereotype.Component;
import org.tio.client.ClientGroupContext;
import org.tio.core.Aio;

import javax.annotation.Resource;

import static com.mindata.blockchain.socket.common.Const.GROUP_NAME;

/**
 * 发送消息的工具类
 * @author wuweifeng wrote on 2018/3/12.
 */
@Component
public class PacketSender {
    @Resource
    private ClientGroupContext clientGroupContext;

    public void sendGroup(BlockPacket blockPacket) {
        //发送到一个group
        Aio.sendToGroup(clientGroupContext, GROUP_NAME, blockPacket);
    }

    public void sendSingle() {

    }

}
