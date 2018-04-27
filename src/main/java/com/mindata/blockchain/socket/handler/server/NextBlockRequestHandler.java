package com.mindata.blockchain.socket.handler.server;

import com.mindata.blockchain.ApplicationContextProvider;
import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.core.manager.DbBlockManager;
import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.RpcNextBlockBody;
import com.mindata.blockchain.socket.body.RpcSimpleBlockBody;
import com.mindata.blockchain.socket.packet.BlockPacket;
import com.mindata.blockchain.socket.packet.PacketBuilder;
import com.mindata.blockchain.socket.packet.PacketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

/**
 * 获取某个区块下一块的请求，发起方带着自己的lastBlock hash，接收方则将自己的区块中，在传来的hash后面的那块返回回去。<p>
 * 如A传来了3，而我本地有5个区块，则返回区块4。
 * @author wuweifeng wrote on 2018/3/16.
 */
public class NextBlockRequestHandler extends AbstractBlockHandler<RpcSimpleBlockBody> {
    private Logger logger = LoggerFactory.getLogger(TotalBlockInfoRequestHandler.class);

    @Override
    public Class<RpcSimpleBlockBody> bodyClass() {
        return RpcSimpleBlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, RpcSimpleBlockBody rpcBlockBody, ChannelContext channelContext) {
        logger.info("收到来自于<" + rpcBlockBody.getAppId() + ">的<请求下一Block>消息，请求者的block hash为：" + Json.toJson
                (rpcBlockBody.getHash()));
        //传来的Block，如果为null，说明发起方连一个Block都没有
        String hash = rpcBlockBody.getHash();

        //查询自己的next block hash，返回给对方，让对方搜集2f+1后确定哪个是对的
        Block nextBlock = ApplicationContextProvider.getBean(DbBlockManager.class).getNextBlockByHash(hash);
        String nextHash = null;
        if (nextBlock != null) {
            nextHash = nextBlock.getHash();
        }
        RpcNextBlockBody respBody = new RpcNextBlockBody(nextHash, hash);
        respBody.setResponseMsgId(rpcBlockBody.getMessageId());
        BlockPacket blockPacket = new PacketBuilder<RpcNextBlockBody>().setType(PacketType
                .NEXT_BLOCK_INFO_RESPONSE).setBody(respBody).build();
        Aio.send(channelContext, blockPacket);
        logger.info("回复给<" + rpcBlockBody.getAppId() + ">，我的nextBlock是" + respBody.toString());

        return null;
    }
}
