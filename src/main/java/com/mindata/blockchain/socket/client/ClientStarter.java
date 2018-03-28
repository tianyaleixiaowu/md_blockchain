package com.mindata.blockchain.socket.client;

import com.mindata.blockchain.common.AppId;
import com.mindata.blockchain.common.CommonUtil;
import com.mindata.blockchain.core.bean.Member;
import com.mindata.blockchain.core.bean.MemberData;
import com.mindata.blockchain.socket.common.Const;
import com.mindata.blockchain.socket.packet.BlockPacket;
import com.mindata.blockchain.socket.packet.NextBlockPacketBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.tio.client.AioClient;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.core.Aio;
import org.tio.core.Node;
import org.tio.utils.lock.SetWithLock;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

import static com.mindata.blockchain.socket.common.Const.GROUP_NAME;

/**
 * @author wuweifeng wrote on 2018/3/18.
 */
@Component
public class ClientStarter {
    @Resource
    private ClientGroupContext clientGroupContext;
    @Resource
    private PacketSender packetSender;
    @Resource
    private RestTemplate restTemplate;
    @Value("${managerUrl}")
    private String managerUrl;
    @Value("${appId}")
    private String appId;
    @Value("${name}")
    private String name;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 从麦达区块链管理端获取已登记的各服务器ip
     * 隔5分钟去获取一次
     */
    @Scheduled(fixedRate = 300000)
    public void fetchOtherServer() {
        String localIp = CommonUtil.getLocalIp();
        try {
            //如果连不上服务器，就不让启动
            MemberData memberData = restTemplate.getForEntity(managerUrl + "member?name=" + name + "&appId=" + AppId
                            .value +
                            "&ip=" +
                            localIp,
                    MemberData.class).getBody();
            //合法的客户端
            if (memberData.getCode() == 0) {
                List<Member> memberList = memberData.getMembers();
                logger.info("共有" + memberList.size() + "个成员需要连接：" + memberList.toString());
                for (Member member : memberList) {
                    bindServerGroup(member.getIp(), member.getPort());
                }
            } else {
                logger.error("不是合法有效的已注册的客户端");
                System.exit(0);
            }
        } catch (Exception e) {
            System.exit(0);
        }

    }

    @EventListener(ApplicationReadyEvent.class)
    public void fetchNextBlock() throws InterruptedException {
        logger.info("开始群发信息获取next Block");
        Thread.sleep(6000);
        //在这里发请求，去获取group别人的新区块
        BlockPacket nextBlockPacket = NextBlockPacketBuilder.build();
        packetSender.sendGroup(nextBlockPacket);
    }

    /**
     * client在此绑定多个服务器，多个服务器为一个group，将来发消息时发给一个group
     */
    private void bindServerGroup(String ip, int port) {
        //服务器节点
        //Node serverNode = new Node(Const.SERVER, Const.PORT);
        Node serverNode = new Node(ip, port);

        try {
            AioClient aioClient = new AioClient(clientGroupContext);
            logger.info("开始绑定" + ip + ":" + port);
            ClientChannelContext clientChannelContext = aioClient.connect(serverNode);
            //绑group是将要连接的各个服务器节点做为一个group
            Aio.bindGroup(clientChannelContext, GROUP_NAME);
        } catch (Exception e) {
            logger.info("绑定" + ip + "失败");
        }

    }

    public int halfGroupSize() {
        SetWithLock setWithLock = clientGroupContext.groups.clients(clientGroupContext, Const.GROUP_NAME);
        return ((Set) setWithLock.getObj()).size() / 2;
    }
}
