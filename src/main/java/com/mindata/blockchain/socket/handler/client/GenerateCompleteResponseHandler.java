package com.mindata.blockchain.socket.handler.client;

import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.BaseBody;
import com.mindata.blockchain.socket.packet.BlockPacket;
import org.tio.core.ChannelContext;

/**
 * @author wuweifeng wrote on 2018/3/21.
 */
public class GenerateCompleteResponseHandler extends AbstractBlockHandler<BaseBody> {

    @Override
    public Class<BaseBody> bodyClass() {
        return BaseBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, BaseBody rpcCheckBlockBody, ChannelContext channelContext) {
        //什么也不干，如果闲着没事，也可以在这里看看有多少人收到了消息
        return null;
    }
}
