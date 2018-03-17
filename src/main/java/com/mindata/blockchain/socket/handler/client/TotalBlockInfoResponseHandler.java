package com.mindata.blockchain.socket.handler.client;

import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.BlockBody;
import com.mindata.blockchain.socket.packet.BlockPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

/**
 * 对获取所有区块信息请求的回复
 * @author wuweifeng wrote on 2018/3/12.
 */
public class TotalBlockInfoResponseHandler extends AbstractBlockHandler<BlockBody> {
    private Logger logger = LoggerFactory.getLogger(TotalBlockInfoResponseHandler.class);

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
