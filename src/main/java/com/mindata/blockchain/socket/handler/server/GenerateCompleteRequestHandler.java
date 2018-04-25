package com.mindata.blockchain.socket.handler.server;

import com.mindata.blockchain.ApplicationContextProvider;
import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.core.manager.DbBlockManager;
import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.RpcSimpleBlockBody;
import com.mindata.blockchain.socket.client.PacketSender;
import com.mindata.blockchain.socket.packet.BlockPacket;
import com.mindata.blockchain.socket.packet.NextBlockPacketBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

import java.util.concurrent.CompletableFuture;

/**
 * 已生成了新区块的全网广播
 * @author wuweifeng wrote on 2018/3/12.
 */
public class GenerateCompleteRequestHandler extends AbstractBlockHandler<RpcSimpleBlockBody> {
    private Logger logger = LoggerFactory.getLogger(GenerateCompleteRequestHandler.class);

    @Override
    public Class<RpcSimpleBlockBody> bodyClass() {
        return RpcSimpleBlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, RpcSimpleBlockBody rpcBlockBody, ChannelContext channelContext) {
        logger.info("收到来自于<" + rpcBlockBody.getAppId() + "><生成了新的Block>消息，block hash为[" + rpcBlockBody.getHash() +
                "]");

        //延迟2秒校验一下本地是否有该区块，如果没有，则发请求去获取新Block
        //延迟的目的是可能刚好自己也马上就要生成同样的Block了，就可以省一次请求
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                Block block = ApplicationContextProvider.getBean(DbBlockManager.class).getBlockByHash(rpcBlockBody
                        .getHash());
                //本地有了
                if (block == null) {
                    logger.info("开始去获取别人的新区块");
                    //在这里发请求，去获取group别人的新区块
                    BlockPacket nextBlockPacket = NextBlockPacketBuilder.build();
                    ApplicationContextProvider.getBean(PacketSender.class).sendGroup(nextBlockPacket);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        });

        return null;
    }
}
