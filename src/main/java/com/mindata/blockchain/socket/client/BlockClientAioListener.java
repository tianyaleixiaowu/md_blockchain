package com.mindata.blockchain.socket.client;

import org.tio.client.intf.ClientAioListener;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;

import static com.mindata.blockchain.socket.common.Const.GROUP_NAME;

/**
 * client端对各个server连接的情况回调。</p>
 * 当某个server的心跳超时（2min）时，Aio会从group里remove掉该连接，需要在重新connect后重新加入group
 * @author wuweifeng wrote on 2018/3/12.
 */
public class BlockClientAioListener implements ClientAioListener {
    @Override
    public void onAfterClose(ChannelContext channelContext, Throwable throwable, String s, boolean b) throws Exception {
        System.out.println("onAfterClose");
        Aio.unbindGroup(channelContext);
    }

    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean b, boolean b1) throws Exception {
        System.out.println("onAfterConnected");
        Aio.bindGroup(channelContext, GROUP_NAME);
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
