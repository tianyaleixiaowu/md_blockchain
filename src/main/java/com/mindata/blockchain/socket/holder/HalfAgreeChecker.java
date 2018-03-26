package com.mindata.blockchain.socket.holder;

import cn.hutool.core.util.StrUtil;
import com.mindata.blockchain.ApplicationContextProvider;
import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.socket.client.ClientStarter;

import java.util.ArrayList;
import java.util.List;

/**
 * 过半校验
 * @author wuweifeng wrote on 2018/3/26.
 */
public class HalfAgreeChecker {
    /**
     * 某个群发的请求，收到新的回复，在此做处理。加锁避免多个同时回复时计算错误
     *
     * @param key
     *         发出的消息的key
     * @param baseResponse
     *         baseResponse
     */
    public static void halfCheck(String key, BaseResponse<Block> baseResponse, HalfAgreeCallback halfAgreeCallback) {
        if (halfAgreeCallback == null) {
            halfAgreeCallback = new EmptyHalfAgree();
        }
        List<BaseResponse> baseResponses = RequestResponseMap.get(key);
        if (baseResponse == null) {
            baseResponses = new ArrayList<>();
        }
        //避免同一个机构多次投票
        for (BaseResponse oldResponse : baseResponses) {
            if (StrUtil.equals(oldResponse.getAppId(), baseResponse.getAppId())) {
                return;
            }
        }
        baseResponses.add(baseResponse);

        ClientStarter clientStarter = ApplicationContextProvider.getBean(ClientStarter.class);
        int halfSize = clientStarter.halfGroupSize();

        int agreeCount = (int) baseResponses.stream().filter(BaseResponse::isAgree).count();
        int unAgreeCount = baseResponses.size() - agreeCount;

        halfAgreeCallback.agreeCount(agreeCount, unAgreeCount);
        //判断是否过半
        if (agreeCount > halfSize) {
            halfAgreeCallback.agree();

            //处理完毕移除掉key
            RequestResponseMap.remove(key);
        } else if (unAgreeCount >= halfSize) {
            halfAgreeCallback.reject();
            //拒绝的过半
            RequestResponseMap.remove(key);
        }

    }

    private static class EmptyHalfAgree implements HalfAgreeCallback {

        @Override
        public void agree() {

        }

        @Override
        public void reject() {

        }

        @Override
        public void agreeCount(int count, int rejectCount) {

        }
    }
}
