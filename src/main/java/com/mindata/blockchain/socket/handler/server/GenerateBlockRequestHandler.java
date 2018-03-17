package com.mindata.blockchain.socket.handler.server;

import com.mindata.blockchain.ApplicationContextProvider;
import com.mindata.blockchain.block.check.CheckerManager;
import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.CheckBlockBody;
import com.mindata.blockchain.socket.body.BlockBody;
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
public class GenerateBlockRequestHandler extends AbstractBlockHandler<BlockBody> {
    private Logger logger = LoggerFactory.getLogger(GenerateBlockRequestHandler.class);


    @Override
    public Class<BlockBody> bodyClass() {
        return BlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, BlockBody blockBody, ChannelContext channelContext)
            throws Exception {
        logger.info("收到<生成Block>请求消息，消息id为：" + packet.getId() + "，block信息为[" + blockBody.getBlock() + "]");

        CheckerManager checkerManager = ApplicationContextProvider.getBean(CheckerManager.class);
        CheckBlockBody checkBlockBody = checkerManager.check(blockBody.getBlock());
        checkBlockBody.setResponseMsgId(blockBody.getMessageId());

        //返回同意、拒绝的响应
        BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.GENERATE_BLOCK_RESPONSE).setBody
                (checkBlockBody).build();
        Aio.send(channelContext, blockPacket);

        return null;
    }
}
