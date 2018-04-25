package com.mindata.blockchain.socket.pbft.queue;

import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.socket.pbft.event.MsgPrepareEvent;
import com.mindata.blockchain.socket.pbft.msg.VoteMsg;
import com.mindata.blockchain.socket.pbft.msg.VotePreMsg;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * preprepare消息的存储，但凡收到请求生成Block的信息，都在这里处理
 * @author wuweifeng wrote on 2018/4/23.
 */
@Component
public class PreMsgQueue extends BaseMsgQueue {
    @Resource
    private PrepareMsgQueue prepareMsgQueue;
    @Resource
    private ApplicationEventPublisher eventPublisher;

    private ConcurrentHashMap<String, VotePreMsg> blockConcurrentHashMap = new ConcurrentHashMap<>();

    @Override
    protected void push(VoteMsg voteMsg) {
        //该队列里的是votePreMsg
        VotePreMsg votePreMsg = (VotePreMsg) voteMsg;
        String hash = votePreMsg.getHash();
        //避免收到重复消息
        if (blockConcurrentHashMap.get(hash) != null) {
            return;
        }
        //但凡是能进到该push方法的，都是通过基本校验的，但在并发情况下可能会相同number的block都进到投票队列中
        //需要对新进来的Vote信息的number进行校验，如果在比prepre阶段靠后的阶段中，已经出现了认证OK的同number的vote，则拒绝进入该队列
        if (prepareMsgQueue.hasOtherConfirm(hash, voteMsg.getNumber())) {
            return;
        }
        //存入Pre集合中
        blockConcurrentHashMap.put(hash, votePreMsg);
        //TODO 启动定时器，清理该hash

        //加入Prepare行列，推送给所有人
        eventPublisher.publishEvent(new MsgPrepareEvent(voteMsg));
    }

    public Block findByHash(String hash) {
        VotePreMsg votePreMsg = blockConcurrentHashMap.get(hash);
        if (votePreMsg != null) {
            return votePreMsg.getBlock();
        }
        return null;
    }
}
