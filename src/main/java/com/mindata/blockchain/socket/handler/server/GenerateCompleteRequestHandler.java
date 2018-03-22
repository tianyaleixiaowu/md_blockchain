package com.mindata.blockchain.socket.handler.server;

import com.mindata.blockchain.ApplicationContextProvider;
import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.BaseBody;
import com.mindata.blockchain.socket.body.RpcBlockBody;
import com.mindata.blockchain.socket.client.PacketSender;
import com.mindata.blockchain.socket.packet.BlockPacket;
import com.mindata.blockchain.socket.packet.NextBlockPacketBuilder;
import com.mindata.blockchain.socket.packet.PacketBuilder;
import com.mindata.blockchain.socket.packet.PacketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;

/**
 * 已生成了新区块的全网广播
 * @author wuweifeng wrote on 2018/3/12.
 */
public class GenerateCompleteRequestHandler extends AbstractBlockHandler<RpcBlockBody> {
    private Logger logger = LoggerFactory.getLogger(GenerateCompleteRequestHandler.class);

    @Override
    public Class<RpcBlockBody> bodyClass() {
        return RpcBlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, RpcBlockBody rpcBlockBody, ChannelContext channelContext) {
        logger.info("收到来自于<" + rpcBlockBody.getAppId() + "><生成了新的Block>消息，block信息为[" + rpcBlockBody.getBlock() + "]");

        ////向对方请求下一块
        //Aio.send(channelContext, NextBlockPacketBuilder.build(rpcBlockBody.getMessageId()));
        //回应我收到了，马上就去请求新Block
        BaseBody baseBody = new BaseBody();
        baseBody.setResponseMsgId(rpcBlockBody.getMessageId());
        BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.GENERATE_COMPLETE_RESPONSE).setBody(baseBody).build();
        Aio.send(channelContext, blockPacket);

        //在这里发请求，去获取对方的新区块
        BlockPacket nextBlockPacket = NextBlockPacketBuilder.build();
        ApplicationContextProvider.getBean(PacketSender.class).sendGroup(nextBlockPacket);
        return null;
    }
}
