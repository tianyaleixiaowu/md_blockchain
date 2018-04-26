package com.mindata.blockchain.socket.pbft.queue;

import cn.hutool.core.bean.BeanUtil;
import com.mindata.blockchain.common.AppId;
import com.mindata.blockchain.socket.pbft.VoteType;
import com.mindata.blockchain.socket.pbft.event.MsgCommitEvent;
import com.mindata.blockchain.socket.pbft.msg.VoteMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Prepare阶段的消息队列
 *
 * @author wuweifeng wrote on 2018/4/25.
 */
@Component
public class PrepareMsgQueue extends AbstractVoteMsgQueue {
    @Resource
    private CommitMsgQueue commitMsgQueue;
    @Resource
    private ApplicationEventPublisher eventPublisher;
    private Logger logger = LoggerFactory.getLogger(getClass());



    /**
     * 收到节点（包括自己）针对某Block的Prepare消息
     *
     * @param voteMsg
     *         voteMsg
     */
    @Override
    protected void deal(VoteMsg voteMsg, List<VoteMsg> voteMsgs) {
        String hash = voteMsg.getHash();
        //如果我已经对该hash的commit投过票了，就不再继续
        if (voteStateConcurrentHashMap.get(hash) != null) {
            return;
        }

        VoteMsg commitMsg = new VoteMsg();
        BeanUtil.copyProperties(voteMsg, commitMsg);
        commitMsg.setVoteType(VoteType.COMMIT);
        commitMsg.setAppId(AppId.value);
        //开始校验并决定是否进入commit阶段
        //校验该vote是否合法
        if (commitMsgQueue.hasOtherConfirm(hash, voteMsg.getNumber())) {
             agree(commitMsg, false);
        } else {
            //开始校验拜占庭数量，如果agree的超过2f + 1，就commit
            long agreeCount = voteMsgs.stream().filter(VoteMsg::isAgree).count();
            long unAgreeCount = voteMsgs.size() - agreeCount;

            //开始发出commit的同意or拒绝的消息
            if (agreeCount >= pbftSize() * 2 + 1) {
                agree(commitMsg, true);
            } else if (unAgreeCount >= pbftSize() + 1) {
                agree(commitMsg, false);
            }
        }

    }

    private void agree(VoteMsg commitMsg, boolean flag) {
        logger.info("Prepare阶段完毕，是否进入commit的标志是：" + flag);
        //发出拒绝commit的消息
        commitMsg.setAgree(flag);
        eventPublisher.publishEvent(new MsgCommitEvent(commitMsg));
        voteStateConcurrentHashMap.put(commitMsg.getHash(), flag);
    }



    /**
     * 判断大家是否已对其他的Block达成共识，如果true，则拒绝即将进入队列的Block
     *
     * @param hash
     *         hash
     * @return 是否存在
     */
    public boolean otherConfirm(String hash, int number) {
        if (commitMsgQueue.hasOtherConfirm(hash, number)) {
            return true;
        }
        return hasOtherConfirm(hash, number);
    }
}
