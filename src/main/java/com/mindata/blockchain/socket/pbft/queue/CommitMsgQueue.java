package com.mindata.blockchain.socket.pbft.queue;

import cn.hutool.core.collection.CollectionUtil;
import com.mindata.blockchain.ApplicationContextProvider;
import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.core.event.AddBlockEvent;
import com.mindata.blockchain.socket.pbft.msg.VoteMsg;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Confirm阶段的消息队列
 * 每个节点收到超过2f+1个不同节点（包括自己）的commit消息后，就认为该区块已经达成一致，进入committed状态，并将其持久化到区块链数据库中
 *
 * @author wuweifeng wrote on 2018/4/25.
 */
@Component
public class CommitMsgQueue extends BaseMsgQueue {
    @Resource
    private PreMsgQueue preMsgQueue;
    /**
     * 对同一个Block hash的投票集合
     */
    private ConcurrentHashMap<String, List<VoteMsg>> voteMsgConcurrentHashMap = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, Boolean> voteStateConcurrentHashMap = new ConcurrentHashMap<>();


    @Override
    protected void push(VoteMsg voteMsg) {
        String hash = voteMsg.getHash();
        List<VoteMsg> voteMsgs = voteMsgConcurrentHashMap.get(hash);
        if (CollectionUtil.isEmpty(voteMsgs)) {
            voteMsgs = new ArrayList<>();
            voteMsgs.add(voteMsg);
            voteMsgConcurrentHashMap.put(hash, voteMsgs);
        }
        //判断本地集合是否已经存在完全相同的voteMsg了
        for (VoteMsg temp : voteMsgs) {
            if (temp.getNumber() == voteMsg.getNumber() && temp.getAppId().equals(voteMsg.getAppId())) {
                return;
            }
        }
        //添加进去
        voteMsgs.add(voteMsg);

        //如果已经落地过了
        if (voteStateConcurrentHashMap.get(hash) != null && voteStateConcurrentHashMap.get(hash)) {
            return;
        }

        //通过校验agree数量，来决定是否在本地生成Block
        long count = voteMsgs.stream().filter(VoteMsg::isAgree).count();
        if (count >= pbftSize() * 2 + 1) {
            Block block = preMsgQueue.findByHash(hash);
            if (block == null) {
                return;
            }
            //本地落地
            voteStateConcurrentHashMap.put(hash, true);
            ApplicationContextProvider.publishEvent(new AddBlockEvent(block));
        }
    }

    /**
     * 校验队列中是否存在number相同，hash不同，且被同意数量已经超过过2f+1的记录
     *
     * @param hash
     *         hash
     * @return 是否超过
     */
    public boolean hasOtherConfirm(String hash, int number) {
        for (String key : voteMsgConcurrentHashMap.keySet()) {
            if (hash.equals(key)) {
                continue;
            }
            if (voteMsgConcurrentHashMap.get(key).get(0).getNumber() < number) {
                continue;
            }
            //如果有别的>=number的Block已经达成共识了，则返回true
            if (voteStateConcurrentHashMap.get(key)) {
                return true;
            }
        }
        return false;
    }
}
