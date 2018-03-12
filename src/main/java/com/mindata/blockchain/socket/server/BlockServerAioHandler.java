package com.mindata.blockchain.socket.server;

import com.mindata.blockchain.socket.base.AbstractAioHandler;
import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.handler.*;
import com.mindata.blockchain.socket.packet.BlockPacket;
import com.mindata.blockchain.socket.packet.PacketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuweifeng wrote on 2018/3/12.
 */
public class BlockServerAioHandler extends AbstractAioHandler implements ServerAioHandler {
    private static Logger log = LoggerFactory.getLogger(BlockServerAioHandler.class);

    private static Map<Byte, AbstractBlockHandler<?>> handlerMap = new HashMap<>();

    static {
        handlerMap.put(PacketType.GENERATE_BLOCK_COMPLETE, new GenerateBlockCompleteHandler());
        handlerMap.put(PacketType.GENERATE_BLOCK_REQUEST, new GenerateBlockRequestHandler());
        handlerMap.put(PacketType.TOTAL_BLOCK_INFO_REQUEST, new TotalBlockInfoRequestHandler());
        handlerMap.put(PacketType.LAST_BLOCK_INFO_REQUEST, new LastBlockInfoRequestHandler());
        handlerMap.put(PacketType.HEART_BEAT, new HeartBeatHandler());
    }


    /**
     * 处理消息
     */
    @Override
    public void handler(Packet packet, ChannelContext channelContext) throws Exception {
        BlockPacket blockPacket = (BlockPacket) packet;
        Byte type = blockPacket.getType();
        AbstractBlockHandler<?> handler = handlerMap.get(type);
        if (handler == null) {
            log.error("{}, 找不到处理类，type:{}", channelContext, type);
            return;
        }
        handler.handler(blockPacket, channelContext);
    }
}
