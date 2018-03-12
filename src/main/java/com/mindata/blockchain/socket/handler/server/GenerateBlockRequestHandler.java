package com.mindata.blockchain.socket.handler.server;

import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.CheckBlockBody;
import com.mindata.blockchain.socket.body.GenerateBlockBody;
import com.mindata.blockchain.socket.packet.BlockPacket;
import com.mindata.blockchain.socket.packet.PacketBuilder;
import com.mindata.blockchain.socket.packet.PacketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

/**
 * 请求生成区块，全网广播，等待大家校验回应
 * @author wuweifeng wrote on 2018/3/12.
 */
public class GenerateBlockRequestHandler extends AbstractBlockHandler<GenerateBlockBody> {
    private Logger logger = LoggerFactory.getLogger(GenerateBlockRequestHandler.class);

    @Override
    public Class<GenerateBlockBody> bodyClass() {
        return GenerateBlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, GenerateBlockBody generateBlockBody, ChannelContext channelContext) throws Exception {
        logger.info("收到<生成Block>请求消息", Json.toJson(generateBlockBody));

        //TODO check合法性
        //TODO response

        //返回同意、拒绝的响应
        BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.GENERATE_BLOCK_REPONSE).setBody(new
                CheckBlockBody(true, "同意")).build();
        Aio.send(channelContext, blockPacket);

        return null;
    }
}
