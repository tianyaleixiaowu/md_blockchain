package com.mindata.blockchain.socket.client;

import org.tio.client.intf.ClientAioListener;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;

/**
 * @author wuweifeng wrote on 2018/3/12.
 */
public class BlockClientAioListener implements ClientAioListener {
    @Override
    public void onAfterClose(ChannelContext channelContext, Throwable throwable, String s, boolean b) throws Exception {

    }

    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean b, boolean b1) throws Exception {

    }

    @Override
    public void onAfterReceived(ChannelContext channelContext, Packet packet, int i) throws Exception {

    }

    @Override
    public void onAfterSent(ChannelContext channelContext, Packet packet, boolean b) throws Exception {

    }

    @Override
    public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String s, boolean b) {

    }
}
