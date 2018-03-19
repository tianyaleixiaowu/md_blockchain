package com.mindata.blockchain.socket.client;

import com.mindata.blockchain.socket.common.Const;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.tio.client.AioClient;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.core.Aio;
import org.tio.core.Node;
import org.tio.utils.lock.SetWithLock;

import javax.annotation.Resource;
import java.util.Set;

import static com.mindata.blockchain.socket.common.Const.GROUP_NAME;

/**
 * @author wuweifeng wrote on 2018/3/18.
 */
@Component
public class ClientStarter {
    @Resource
    private ClientGroupContext clientGroupContext;
    /**
     * client在此绑定多个服务器，多个服务器为一个group，将来发消息时发给一个group
     */
    @EventListener(ApplicationReadyEvent.class)
    public void bindServerGroup() throws Exception {
        //服务器节点
        Node serverNode = new Node(Const.SERVER, Const.PORT);

        AioClient aioClient = new AioClient(clientGroupContext);

        ClientChannelContext clientChannelContext = aioClient.connect(serverNode);
        //绑group是将要连接的各个服务器节点做为一个group
        Aio.bindGroup(clientChannelContext, GROUP_NAME);
    }

    public int halfGroupSize() {
        SetWithLock setWithLock = clientGroupContext.groups.clients(clientGroupContext, Const.GROUP_NAME);
        return ((Set)setWithLock.getObj()).size() / 2;
    }
}
