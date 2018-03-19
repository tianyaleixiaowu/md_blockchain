package com.mindata.blockchain.socket.holder;

import com.mindata.blockchain.block.Block;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuweifeng wrote on 2018/3/17.
 */
public class BlockHolder {
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
}
