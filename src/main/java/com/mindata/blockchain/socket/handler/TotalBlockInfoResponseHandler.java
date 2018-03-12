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
 * 对获取所有区块信息请求的回复
 * @author wuweifeng wrote on 2018/3/12.
 */
public class TotalBlockInfoResponseHandler extends AbstractBlockHandler<GenerateBlockBody> {
    private Logger logger = LoggerFactory.getLogger(TotalBlockInfoResponseHandler.class);

    @Override
    public Class<GenerateBlockBody> bodyClass() {
        return GenerateBlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, GenerateBlockBody generateBlockBody, ChannelContext channelContext) throws Exception {
        logger.info("收到<请求生成Block的回应>消息", Json.toJson(generateBlockBody));
        Block block = Json.toBean(generateBlockBody.getBlockJson(), Block.class);

        //TODO check合法性
        //TODO response

        return null;
    }
}
