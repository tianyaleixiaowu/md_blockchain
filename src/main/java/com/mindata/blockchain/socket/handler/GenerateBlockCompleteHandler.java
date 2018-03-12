package com.mindata.blockchain.socket.handler;

import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.GenerateBlockBody;
import com.mindata.blockchain.socket.packet.BlockPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

/**
 * 已生成了新区块的全网广播
 * @author wuweifeng wrote on 2018/3/12.
 */
public class GenerateBlockCompleteHandler extends AbstractBlockHandler<GenerateBlockBody> {
    private Logger logger = LoggerFactory.getLogger(GenerateBlockCompleteHandler.class);

    @Override
    public Class<GenerateBlockBody> bodyClass() {
        return GenerateBlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, GenerateBlockBody generateBlockBody, ChannelContext channelContext) throws Exception {
        logger.info("收到<新生成Block>消息，开始拉取新区块", Json.toJson(generateBlockBody));
        logger.info(generateBlockBody.toString());
        //TODO check合法性
        //TODO response

        return null;
    }
}
