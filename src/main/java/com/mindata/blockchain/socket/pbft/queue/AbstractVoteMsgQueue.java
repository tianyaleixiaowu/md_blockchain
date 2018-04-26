package com.mindata.blockchain.socket.pbft.queue;

import cn.hutool.core.collection.CollectionUtil;
import com.mindata.blockchain.socket.pbft.msg.VoteMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuweifeng wrote on 2018/4/26.
 */
public abstract class AbstractVoteMsgQueue extends BaseMsgQueue {
    /**
     * 存储所有的hash的投票集合
     */
    protected ConcurrentHashMap<String, List<VoteMsg>> voteMsgConcurrentHashMap = new ConcurrentHashMap<>();
    /**
     * 存储本节点已确认状态的hash的集合，即本节点已对外广播过允许commit或拒绝commit的消息了
     */
    protected ConcurrentHashMap<String, Boolean> voteStateConcurrentHashMap = new ConcurrentHashMap<>();

    private Logger logger = LoggerFactory.getLogger(getClass());

    abstract void deal(VoteMsg voteMsg, List<VoteMsg> voteMsgs);

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
        //如果我已经对该hash的commit投过票了，就不再继续
        if (voteStateConcurrentHashMap.get(hash) != null) {
            return;
        }

        deal(voteMsg, voteMsgs);
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
            if (voteStateConcurrentHashMap.get(key) != null && voteStateConcurrentHashMap.get(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 清理旧的block的hash
     */
    protected void clearOldBlockHash(int number) {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (String key : voteMsgConcurrentHashMap.keySet()) {
                if (voteMsgConcurrentHashMap.get(key).get(0).getNumber() <= number) {
                    voteMsgConcurrentHashMap.remove(key);
                    voteStateConcurrentHashMap.remove(key);
                }
            }
            return null;
        });
    }
}
