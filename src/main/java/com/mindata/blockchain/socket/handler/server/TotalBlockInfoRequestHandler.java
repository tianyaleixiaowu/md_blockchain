package com.mindata.blockchain.socket.handler.server;

import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.BlockBody;
import com.mindata.blockchain.socket.packet.BlockPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

/**
 * 获取全部区块信息的请求，全网广播
 * @author wuweifeng wrote on 2018/3/12.
 */
public class TotalBlockInfoRequestHandler extends AbstractBlockHandler<BlockBody> {
    private Logger logger = LoggerFactory.getLogger(TotalBlockInfoRequestHandler.class);

    @Override
    public Class<BlockBody> bodyClass() {
        return BlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, BlockBody blockBody, ChannelContext channelContext) throws Exception {
        logger.info("收到<请求生成Block的回应>消息", Json.toJson(blockBody));

        //TODO check合法性
        //TODO response

        return null;
    }
}
