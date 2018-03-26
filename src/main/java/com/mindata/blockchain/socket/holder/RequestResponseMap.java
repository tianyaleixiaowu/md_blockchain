package com.mindata.blockchain.socket.holder;

import com.mindata.blockchain.common.CommonUtil;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuweifeng wrote on 2018/3/17.
 */
public class RequestResponseMap {
    private static ConcurrentHashMap<String, List<BaseResponse>> requestMap = new ConcurrentHashMap<>();
    /**
     * 每个请求发起时都记录一下时间，后续隔一段时间清理一下过期的key，避免一个请求发出后石沉大海，map越来越大
     */
    private static ConcurrentHashMap<String, Long> requestTimerMap = new ConcurrentHashMap<>();

    public static List<BaseResponse> get(String key) {
        return requestMap.get(key);
    }

    public static void put(String key, List<BaseResponse> responses) {
        requestMap.put(key, responses);
        requestTimerMap.put(key, CommonUtil.getNow());
    }

    public static void remove(String key) {
        requestMap.remove(key);
        requestTimerMap.remove(key);
    }

    /**
     * 每隔一段时间，清理一下已经过期的key
     */
    public static void clearTimeOutKey() {
        for (String key : requestTimerMap.keySet()) {
             Long createTime = requestTimerMap.get(key);
             //超过10分钟了，key就被删掉
             if (CommonUtil.getNow() - createTime > 1000 * 10 * 60) {
                 requestMap.remove(key);
                 requestTimerMap.remove(key);
             }
        }
    }

}
