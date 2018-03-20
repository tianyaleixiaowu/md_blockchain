package com.mindata.blockchain.socket.handler.server;

import com.mindata.blockchain.ApplicationContextProvider;
import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.core.manager.DbBlockManager;
import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.BlockBody;
import com.mindata.blockchain.socket.packet.BlockPacket;
import com.mindata.blockchain.socket.packet.PacketBuilder;
import com.mindata.blockchain.socket.packet.PacketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;

/**
 * 请求别人获取最后一个区块的信息
 * @author wuweifeng wrote on 2018/3/12.
 */
public class LastBlockInfoRequestHandler extends AbstractBlockHandler<BlockBody> {
    private Logger logger = LoggerFactory.getLogger(LastBlockInfoRequestHandler.class);

    @Override
    public Class<BlockBody> bodyClass() {
        return BlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, BlockBody blockBody, ChannelContext channelContext) {
        logger.info("收到<请求获取最后一个Block>消息");
        Block block = ApplicationContextProvider.getBean(DbBlockManager.class).getLastBlock();

        BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.LAST_BLOCK_INFO_RESPONSE).setBody(new
                BlockBody(block)).build();
        Aio.send(channelContext, blockPacket);

        return null;
    }
}
