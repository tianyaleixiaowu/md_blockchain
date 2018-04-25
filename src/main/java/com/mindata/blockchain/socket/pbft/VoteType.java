package com.mindata.blockchain.socket.pbft;

/**
 * pbft算法的各状态
 *
 * 算法流程如下：
 *
 * 当前时间片的锻造者将收集到的交易打包成block并进行广播（这一步与之前的算法一致）
 * 收到block的委托人节点如果还没有收到过这个block并且验证合法后，就广播一个prepare<h, d, s>消息，其中h为block的高度，d是block的摘要，s是本节点签名
 * 收到prepare消息后，节点开始在内存中累加消息数量，当收到超过f+1不同节点的prepare消息后，节点进入prepared状态，之后会广播一个commit<h, d, s>消息
 * 每个节点收到超过2f+1个不同节点的commit消息后，就认为该区块已经达成一致，进入committed状态，并将其持久化到区块链数据库中
 * 系统在在收到第一个高度为h的block时，启动一个定时器，当定时到期后，如果还没达成一致，就放弃本次共识。
 * @author wuweifeng wrote on 2018/4/23.
 */
public class VoteType {
    /**
     * 节点自己打算生成Block
     */
    public static final byte PREPREPARE = 1;
    /**
     * 节点收到请求生成block消息，进入准备状态，对外广播该状态
     */
    public static final byte PREPARE = 2;
    /**
     * 每个节点收到超过2f+1个不同节点的commit消息后，就认为该区块已经达成一致，进入committed状态，并将其持久化到区块链数据库中
     */
    public static final byte COMMIT = 3;
}
