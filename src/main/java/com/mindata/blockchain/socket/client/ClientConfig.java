package com.mindata.blockchain.socket.client;

import com.mindata.blockchain.socket.common.Const;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.tio.client.AioClient;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Aio;
import org.tio.core.Node;

import static com.mindata.blockchain.socket.common.Const.GROUP_NAME;

/**
 * @author wuweifeng wrote on 2018/3/12.
 */
@Configuration
public class ClientConfig {

    /**
     * 构建客户端连接的context
     * @return
     * ClientGroupContext
     */
    @Bean
    public ClientGroupContext clientGroupContext() {
        //handler, 包括编码、解码、消息处理
        ClientAioHandler clientAioHandler = new BlockClientAioHandler();
        //事件监听器，可以为null，但建议自己实现该接口
        ClientAioListener clientAioListener = new BlockClientAioListener();
        //断链后自动连接的，不想自动连接请设为null
        ReconnConf reconnConf = new ReconnConf(5000L);
        ClientGroupContext clientGroupContext = new ClientGroupContext(clientAioHandler, clientAioListener,
                reconnConf);
        
        //clientGroupContext.setHeartbeatTimeout(Const.TIMEOUT);
        return clientGroupContext;
    }

    /**
     * client在此绑定多个服务器，多个服务器为一个group，将来发消息时发给一个group
     */
    @EventListener(ApplicationReadyEvent.class)
    public void bindServerGroup() throws Exception {
        //服务器节点
        Node serverNode = new Node(Const.SERVER, Const.PORT);

        AioClient aioClient = new AioClient(clientGroupContext());

        ClientChannelContext clientChannelContext = aioClient.connect(serverNode);
        //绑group是将要连接的各个服务器节点做为一个group
        Aio.bindGroup(clientChannelContext, GROUP_NAME);
    }
}
