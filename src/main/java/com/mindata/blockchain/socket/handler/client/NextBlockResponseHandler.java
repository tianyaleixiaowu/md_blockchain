package com.mindata.blockchain.socket.handler.client;

import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.BlockBody;
import com.mindata.blockchain.socket.packet.BlockPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

/**
 * @author wuweifeng wrote on 2018/3/16.
 */
public class NextBlockResponseHandler extends AbstractBlockHandler<BlockBody> {
    private Logger logger = LoggerFactory.getLogger(TotalBlockInfoResponseHandler.class);

    @Override
    public Class<BlockBody> bodyClass() {
        return BlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, BlockBody blockBody, ChannelContext channelContext) throws Exception {
        logger.info("收到<请求下一个Block的回应>消息：" + Json.toJson(blockBody));

        Block block = blockBody.getBlock();
        //如果为null，说明已经是最新的块了
        if (block == null) {
             logger.info("已是最新块了");
        } else {
            logger.info("下一块是" + block);
            //TODO 更新本地
        }

        return null;
    }
}
