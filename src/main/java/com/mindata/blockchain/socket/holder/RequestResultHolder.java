package com.mindata.blockchain.socket.holder;

import com.mindata.blockchain.block.Block;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用来"发出请求后，对于各响应的状态的存储"
 * @author wuweifeng wrote on 2018/3/14.
 */
public class RequestResultHolder {
    private static ConcurrentHashMap<String, Set<String>> requestMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Block> blockMap = new ConcurrentHashMap<>();

    public static void putTempBlock(Block block) {
        blockMap.put(block.getHash(), block);
    }

    public static Block getTempBlock(String hash) {
        return blockMap.get(hash);
    }

    public static void clearTempBlock(String hash) {
        blockMap.remove(hash);
    }

    /**                                                                                                         
     *
     * @param requestId
     */
    public static void hold(Long requestId) {
        //requestMap.put(Constants.KEY_REQUEST_PREFIX + requestId, );
    }




}
