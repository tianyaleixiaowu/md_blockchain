package com.mindata.blockchain.socket.holder;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用来"发出请求后，对于各响应的状态的存储"
 * @author wuweifeng wrote on 2018/3/14.
 */
public class RequestHolder {
    private ConcurrentHashMap<String, Set<String>> requestMap = new ConcurrentHashMap<>();

}
