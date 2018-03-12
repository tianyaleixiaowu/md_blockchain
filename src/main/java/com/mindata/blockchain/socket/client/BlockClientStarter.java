package com.mindata.blockchain.socket.client;

import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.socket.body.GenerateBlockBody;
import com.mindata.blockchain.socket.common.Const;
import com.mindata.blockchain.socket.packet.BlockPacket;
import com.mindata.blockchain.socket.packet.PacketBuilder;
import com.mindata.blockchain.socket.packet.PacketType;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.tio.client.AioClient;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.core.Aio;
import org.tio.core.Node;

import javax.annotation.Resource;

import static com.mindata.blockchain.socket.common.Const.GROUP_NAME;

/**
 * @author wuweifeng wrote on 2018/3/9.
 */
@Component
public class BlockClientStarter {
    @Resource
    private ClientGroupContext clientGroupContext;

    /**
     * 启动程序入口
     */
    @EventListener(ApplicationReadyEvent.class)
    public void start() throws Exception {
        //服务器节点
        Node serverNode = new Node(Const.SERVER, Const.PORT);

        AioClient aioClient = new AioClient(clientGroupContext);

        ClientChannelContext clientChannelContext = aioClient.connect(serverNode);
        //绑group是将要连接的各个服务器节点做为一个group
        Aio.bindGroup(clientChannelContext, GROUP_NAME);
        //连上后，发条消息玩玩
        send();
    }

    private void send() throws Exception {
        Block block = new Block();
        block.setHash("123456");

        BlockPacket packet = new PacketBuilder<GenerateBlockBody>()
                .setType(PacketType.GENERATE_BLOCK_COMPLETE)
                .setBody(new GenerateBlockBody(block)).build();

        //发送到某一个server
        //Aio.send(clientChannelContext, packet);
        //发送到一个group
        Aio.sendToGroup(clientGroupContext, GROUP_NAME, packet);
    }
}
