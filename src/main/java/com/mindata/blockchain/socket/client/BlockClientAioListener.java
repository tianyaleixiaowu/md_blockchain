package com.mindata.blockchain.socket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;

import static com.mindata.blockchain.socket.common.Const.GROUP_NAME;

/**
 * client端对各个server连接的情况回调。</p>
 * 当某个server的心跳超时（2min）时，Aio会从group里remove掉该连接，需要在重新connect后重新加入group
 *
 * @author wuweifeng wrote on 2018/3/12.
 */
public class BlockClientAioListener implements ClientAioListener {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) throws Exception {
        if (isConnected) {
            logger.info("连接成功：server地址为-" + channelContext.getServerNode());
            Aio.bindGroup(channelContext, GROUP_NAME);
        } else {
            logger.info("连接失败：server地址为-" + channelContext.getServerNode());
        }
    }

    @Override
    public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String s, boolean b) {
        logger.info("连接关闭：server地址为-" + channelContext.getServerNode());
        Aio.unbindGroup(channelContext);
    }

    @Override
    public void onAfterDecoded(ChannelContext channelContext, Packet packet, int i) throws Exception {

    }

    @Override
    public void onAfterReceivedBytes(ChannelContext channelContext, int i) throws Exception {

    }

    @Override
    public void onAfterSent(ChannelContext channelContext, Packet packet, boolean b) throws Exception {

    }

    @Override
    public void onAfterHandled(ChannelContext channelContext, Packet packet, long l) throws Exception {

    }

}
