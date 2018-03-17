package com.mindata.blockchain.socket.client;

import com.mindata.blockchain.ApplicationContextProvider;
import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.core.manager.DbBlockManager;
import com.mindata.blockchain.socket.base.AbstractAioHandler;
import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.BlockBody;
import com.mindata.blockchain.socket.handler.client.GenerateBlockResponseHandler;
import com.mindata.blockchain.socket.handler.client.LastBlockInfoResponseHandler;
import com.mindata.blockchain.socket.handler.client.NextBlockResponseHandler;
import com.mindata.blockchain.socket.handler.client.TotalBlockInfoResponseHandler;
import com.mindata.blockchain.socket.packet.BlockPacket;
import com.mindata.blockchain.socket.packet.PacketBuilder;
import com.mindata.blockchain.socket.packet.PacketType;
import org.tio.client.intf.ClientAioHandler;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuweifeng wrote on 2018/3/12.
 */
public class BlockClientAioHandler extends AbstractAioHandler implements ClientAioHandler {
    private static Map<Byte, AbstractBlockHandler<?>> handlerMap = new HashMap<>();

    static {
        handlerMap.put(PacketType.GENERATE_BLOCK_RESPONSE, new GenerateBlockResponseHandler());
        handlerMap.put(PacketType.TOTAL_BLOCK_INFO_RESPONSE, new TotalBlockInfoResponseHandler());
        handlerMap.put(PacketType.LAST_BLOCK_INFO_RESPONSE, new LastBlockInfoResponseHandler());
        handlerMap.put(PacketType.NEXT_BLOCK_INFO_RESPONSE, new NextBlockResponseHandler());
    }

    @Override
    public BlockPacket heartbeatPacket() {
        //心跳包的内容就是隔一段时间向别的server获取一次别的最新的区块
        Block block = ApplicationContextProvider.getBean(DbBlockManager.class).getLastBlock();
        return new PacketBuilder<>().setType(PacketType.NEXT_BLOCK_INFO_REQUEST).setBody(new
                BlockBody(block)).build();
    }

    @Override
    public void handler(Packet packet, ChannelContext channelContext) throws Exception {
        BlockPacket blockPacket = (BlockPacket) packet;
        Byte type = blockPacket.getType();
        AbstractBlockHandler<?> blockHandler = handlerMap.get(type);
        blockHandler.handler(blockPacket, channelContext);
    }
}
