package com.mindata.blockchain.core.queue;

import cn.hutool.core.util.StrUtil;
import com.mindata.blockchain.common.AppId;
import com.mindata.blockchain.core.queue.base.BaseEvent;
import com.mindata.blockchain.core.queue.base.MessageConsumer;
import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.BaseBody;
import com.mindata.blockchain.socket.handler.client.*;
import com.mindata.blockchain.socket.holder.BaseResponse;
import com.mindata.blockchain.socket.holder.RequestResponseMap;
import com.mindata.blockchain.socket.packet.BlockPacket;
import com.mindata.blockchain.socket.packet.PacketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tio.utils.json.Json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuweifeng wrote on 2018/4/20.
 */
@Component
public class DisruptorClientConsumer implements MessageConsumer {
    private static Map<Byte, AbstractBlockHandler<?>> handlerMap = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(getClass());

    static {
        handlerMap.put(PacketType.GENERATE_BLOCK_RESPONSE, new GenerateBlockResponseHandler());
        handlerMap.put(PacketType.TOTAL_BLOCK_INFO_RESPONSE, new TotalBlockInfoResponseHandler());
        handlerMap.put(PacketType.NEXT_BLOCK_INFO_RESPONSE, new NextBlockResponseHandler());
        handlerMap.put(PacketType.GENERATE_COMPLETE_RESPONSE, new GenerateCompleteResponseHandler());
    }

    @Override
    public void receive(BaseEvent baseEvent) throws Exception {
        BlockPacket blockPacket = baseEvent.getBlockPacket();
        Byte type = blockPacket.getType();
        AbstractBlockHandler<?> blockHandler = handlerMap.get(type);
        if (blockHandler == null) {
            return;
        }

        //消费消息
        BaseBody baseBody = Json.toBean(new String(blockPacket.getBody()), BaseBody.class);
        logger.info("收到来自于<" + baseBody.getAppId() + ">针对msg<" + baseBody.getResponseMsgId() + ">的回应");

        String appId = baseBody.getAppId();
        if (StrUtil.equals(AppId.value, appId)) {
            //是本机
            //return;
        }
        String msgId = baseBody.getResponseMsgId();
        List<BaseResponse> responseList = RequestResponseMap.get(msgId);
        //如果回应的消息msgId key自己不曾保存过，或者已被删除，说明针对该消息已有结果，则不再处理
        if (responseList == null) {
            return;
        }

        blockHandler.handler(blockPacket, baseEvent.getChannelContext());
    }
}
