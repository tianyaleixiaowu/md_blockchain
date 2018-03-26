package com.mindata.blockchain.socket.holder;

/**
 * @author wuweifeng wrote on 2018/3/26.
 */
public interface HalfAgreeCallback {
    /**
     * 已过半同意
     */
    void agree();
    /**
     * 已过半拒绝
     */
    void reject();
    /**
     * 当前同意的数量
     * @param agreeCount agreeCount
     * @param rejectCount rejectCount
     */
    void agreeCount(int agreeCount, int rejectCount);
}
