package com.mindata.blockchain.socket.handler.client;

import com.mindata.blockchain.ApplicationContextProvider;
import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.block.check.CheckerManager;
import com.mindata.blockchain.core.event.AddBlockEvent;
import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.BlockBody;
import com.mindata.blockchain.socket.body.CheckBlockBody;
import com.mindata.blockchain.socket.client.RequestResponseMap;
import com.mindata.blockchain.socket.packet.BlockPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

/**
 * 对方根据我们传的hash，给我们返回的next block
 *
 * @author wuweifeng wrote on 2018/3/16.
 */
public class NextBlockResponseHandler extends AbstractBlockHandler<BlockBody> {
    private Logger logger = LoggerFactory.getLogger(TotalBlockInfoResponseHandler.class);

    @Override
    public Class<BlockBody> bodyClass() {
        return BlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, BlockBody blockBody, ChannelContext channelContext) {
        logger.info("收到来自于<" + blockBody.getAppId() + ">的回复，下一个Block为：" + Json.toJson(blockBody));

        Block block = blockBody.getBlock();
        //如果为null，说明对方根据我们传过去的hash，找不到next block。说明要么已经是最新了，要么对方的block比自己的少
        if (block == null) {
            logger.info("已是最新块了");
            return null;
        }
        //此处校验传过来的block的合法性，如果合法，则更新到本地，作为next区块
        CheckerManager checkerManager = ApplicationContextProvider.getBean(CheckerManager.class);
        CheckBlockBody checkBlockBody = checkerManager.checkIsNextBlock(block);
        //校验通过，则存入本地DB，保存新区块
        if (checkBlockBody.getCode() == 0) {
            ApplicationContextProvider.publishEvent(new AddBlockEvent(block));
        }

        //需要清除掉原来的key
        RequestResponseMap.remove(blockBody.getResponseMsgId());
        logger.info("下一块是" + block);

        return null;
    }
}
