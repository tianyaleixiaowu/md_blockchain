package com.mindata.blockchain.socket.handler;

import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.GenerateBlockBody;
import com.mindata.blockchain.socket.packet.BlockPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        Block block = Json.toBean(generateBlockBody.getBlockJson(), Block.class);

        //TODO check合法性
        //TODO response

        return null;
    }
}
