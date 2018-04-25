package com.mindata.blockchain.socket.handler.client;

import com.mindata.blockchain.ApplicationContextProvider;
import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.block.check.CheckerManager;
import com.mindata.blockchain.core.event.AddBlockEvent;
import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.RpcBlockBody;
import com.mindata.blockchain.socket.body.RpcCheckBlockBody;
import com.mindata.blockchain.socket.holder.RequestResponseMap;
import com.mindata.blockchain.socket.packet.BlockPacket;
import com.mindata.blockchain.socket.packet.NextBlockPacketBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

/**
 * 对方根据我们传的hash，给我们返回的next block
 *
 * @author wuweifeng wrote on 2018/3/16.
 */
public class NextBlockResponseHandler extends AbstractBlockHandler<RpcBlockBody> {
    private Logger logger = LoggerFactory.getLogger(TotalBlockInfoResponseHandler.class);

    @Override
    public Class<RpcBlockBody> bodyClass() {
        return RpcBlockBody.class;
    }

    @Override
    public synchronized Object handler(BlockPacket packet, RpcBlockBody rpcBlockBody, ChannelContext channelContext) {
        logger.info("收到来自于<" + rpcBlockBody.getAppId() + ">的回复，下一个Block为：" + Json.toJson(rpcBlockBody));

        Block block = rpcBlockBody.getBlock();
        //如果为null，说明对方根据我们传过去的hash，找不到next block。说明要么已经是最新了，要么对方的block比自己的少
        if (block == null) {
            logger.info("和<" + rpcBlockBody.getAppId() + ">相比，本地已是最新块了");
        } else {
            //此处校验传过来的block的合法性，如果合法，则更新到本地，作为next区块
            CheckerManager checkerManager = ApplicationContextProvider.getBean(CheckerManager.class);
            RpcCheckBlockBody rpcCheckBlockBody = checkerManager.check(block);
            //校验通过，则存入本地DB，保存新区块
            if (rpcCheckBlockBody.getCode() == 0) {
                ApplicationContextProvider.publishEvent(new AddBlockEvent(block));
            }
            logger.info("下一块是" + block);
            //继续请求下一块
            Aio.send(channelContext, NextBlockPacketBuilder.build());
        }

        //需要清除掉原来的key
        RequestResponseMap.remove(rpcBlockBody.getResponseMsgId());

        return null;
    }
}
