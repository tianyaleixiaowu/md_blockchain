package com.mindata.blockchain.socket.client;

import cn.hutool.core.util.StrUtil;
import com.mindata.blockchain.ApplicationContextProvider;
import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.core.event.AddBlockEvent;
import com.mindata.blockchain.socket.holder.BaseResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuweifeng wrote on 2018/3/17.
 */
public class RequestResponseMap {
    private static ConcurrentHashMap<String, List<BaseResponse>> requestMap = new ConcurrentHashMap<>();

    public static List<BaseResponse> get(String key) {
        return requestMap.get(key);
    }

    public static void put(String key, List<BaseResponse> responses) {
        requestMap.put(key, responses);
    }

    public static void remove(String key) {
        requestMap.remove(key);
    }

    /**
     * 某个群发的请求，收到新的回复，在此做处理。加锁避免多个同时回复时计算错误
     * @param key 发出的消息的key
     * @param baseResponse  baseResponse
     */
    public static synchronized void add(String key, BaseResponse<Block> baseResponse) {
        List<BaseResponse> baseResponses = get(key);
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
        //判断是否过半
        if (agreeCount > halfSize) {
            ApplicationContextProvider.publishEvent(new AddBlockEvent(baseResponse.getObject()));
            //处理完毕移除掉key
            RequestResponseMap.remove(key);
        } else if (unAgreeCount >= halfSize) {
            //拒绝的过半
            RequestResponseMap.remove(key);
        }

    }


}
