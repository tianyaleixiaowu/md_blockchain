package com.mindata.blockchain.socket.handler.server;

import com.mindata.blockchain.ApplicationContextProvider;
import com.mindata.blockchain.block.check.CheckerManager;
import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.RpcCheckBlockBody;
import com.mindata.blockchain.socket.body.RpcBlockBody;
import com.mindata.blockchain.socket.packet.BlockPacket;
import com.mindata.blockchain.socket.packet.PacketBuilder;
import com.mindata.blockchain.socket.packet.PacketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;

/**
 * 请求生成区块，全网广播，等待大家校验回应
 *
 * @author wuweifeng wrote on 2018/3/12.
 */
public class GenerateBlockRequestHandler extends AbstractBlockHandler<RpcBlockBody> {
    private Logger logger = LoggerFactory.getLogger(GenerateBlockRequestHandler.class);


    @Override
    public Class<RpcBlockBody> bodyClass() {
        return RpcBlockBody.class;
    }

    @Override
    public synchronized Object handler(BlockPacket packet, RpcBlockBody rpcBlockBody, ChannelContext channelContext) {
        logger.info("收到来自于<" + rpcBlockBody.getAppId() + "><请求生成Block>消息，block信息为[" + rpcBlockBody.getBlock() + "]");

        CheckerManager checkerManager = ApplicationContextProvider.getBean(CheckerManager.class);
        RpcCheckBlockBody rpcCheckBlockBody = checkerManager.check(rpcBlockBody.getBlock());
        rpcCheckBlockBody.setResponseMsgId(rpcBlockBody.getMessageId());
        rpcCheckBlockBody.setBlock(rpcBlockBody.getBlock());

        //返回同意、拒绝的响应
        BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.GENERATE_BLOCK_RESPONSE).setBody
                (rpcCheckBlockBody).build();
        Aio.send(channelContext, blockPacket);
        logger.info("回复是否同意:" + rpcBlockBody.toString());

        return null;
    }
}
