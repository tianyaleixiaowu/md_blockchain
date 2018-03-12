package com.mindata.blockchain.socket.handler.client;

import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.CheckBlockBody;
import com.mindata.blockchain.socket.packet.BlockPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

/**
 * 对别人请求生成区块的回应
 * @author wuweifeng wrote on 2018/3/12.
 */
public class GenerateBlockResponseHandler extends AbstractBlockHandler<CheckBlockBody> {
    private Logger logger = LoggerFactory.getLogger(GenerateBlockResponseHandler.class);

    @Override
    public Class<CheckBlockBody> bodyClass() {
        return CheckBlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, CheckBlockBody checkBlockBody, ChannelContext channelContext) throws Exception {
        logger.info("收到<请求生成Block的回应>消息：" + Json.toJson(checkBlockBody));


        return null;
    }
}
