package com.mindata.blockchain.socket.handler.client;

import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.RpcBlockBody;
import com.mindata.blockchain.socket.packet.BlockPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

/**
 * 对别人获取最后一个区块的信息请求的回复
 * @author wuweifeng wrote on 2018/3/12.
 */
public class LastBlockInfoResponseHandler extends AbstractBlockHandler<RpcBlockBody> {
    private Logger logger = LoggerFactory.getLogger(LastBlockInfoResponseHandler.class);

    @Override
    public Class<RpcBlockBody> bodyClass() {
        return RpcBlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, RpcBlockBody rpcBlockBody, ChannelContext channelContext) throws Exception {
        logger.info("收到<获取最后一个区块的信息的回应>" + Json.toJson(rpcBlockBody));
        //别人回应的最新的区块信息，多个server会回复多个，筛选后，选择链最长的，并更新到本地
        Block block = rpcBlockBody.getBlock();


        return null;
    }
}
