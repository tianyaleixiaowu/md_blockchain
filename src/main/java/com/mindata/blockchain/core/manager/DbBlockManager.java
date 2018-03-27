package com.mindata.blockchain.core.manager;

import cn.hutool.core.util.StrUtil;
import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.block.db.DbStore;
import com.mindata.blockchain.common.Constants;
import com.mindata.blockchain.common.FastJsonUtil;
import com.mindata.blockchain.core.event.AddBlockEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.tio.utils.json.Json;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/3/13.
 */
@Service
public class DbBlockManager {
    @Resource
    private DbStore dbStore;

    /**
     * 查找第一个区块
     *
     * @return 第一个Block
     */
    public Block getFirstBlock() {
        String firstBlockHash = dbStore.get(Constants.KEY_FIRST_BLOCK);
        if (StrUtil.isEmpty(firstBlockHash)) {
            return null;
        }
        return getBlockByHash(firstBlockHash);
    }

    /**
     * 获取最后一个区块信息
     *
     * @return 最后一个区块
     */
    public Block getLastBlock() {
        String lastBlockHash = dbStore.get(Constants.KEY_LAST_BLOCK);
        if (StrUtil.isEmpty(lastBlockHash)) {
            return null;
        }
        return getBlockByHash(lastBlockHash);
    }

    /**
     * 获取最后一个区块的hash
     *
     * @return hash
     */
    public String getLastBlockHash() {
        Block block = getLastBlock();
        if (block != null) {
            return block.getHash();
        }
        return null;
    }

    /**
     * 获取某一个block的下一个Block
     *
     * @param block
     *         block
     * @return block
     */
    public Block getNextBlock(Block block) {
        if (block == null) {
            return getFirstBlock();
        }
        String nextHash = dbStore.get(Constants.KEY_BLOCK_NEXT_PREFIX + block.getHash());
        if (nextHash == null) {
            return null;
        }
        return getBlockByHash(nextHash);
    }

    public Block getBlockByHash(String hash) {
        String blockJson = dbStore.get(hash);
        return FastJsonUtil.toBean(blockJson, Block.class);
    }

    /**
     * 校验该block能否做为本地的next区块
     * @param block block
     * @return true false
     */
    public boolean checkCanBeNextBlock(Block block) {
        Block localLast = getLastBlock();
        //创世块可以，或者新块的prev等于本地的last hash也可以
        if (localLast == null && block.getBlockHeader().getHashPreviousBlock() == null) {
            return true;
        }
        if (localLast != null && StrUtil.equals(localLast.getHash(), block.getBlockHeader().getHashPreviousBlock())) {
            return true;
        }
        return false;
    }

    /**
     * 数据库里添加一个新的区块
     *
     * @param addBlockEvent
     *         addBlockEvent
     */
    @Order(1)
    @EventListener(AddBlockEvent.class)
    public synchronized void addBlock(AddBlockEvent addBlockEvent) {
        Block block = (Block) addBlockEvent.getSource();
        String hash = block.getHash();
        //如果已经存在了，说明已经更新过该Block了
        if (dbStore.get(hash) != null) {
            return;
        }
        //校验本地最后一个区块，是否等于该block prevHash
        if (!checkCanBeNextBlock(block)) {
            return;
        }

        //如果没有上一区块，说明该块就是创世块
        if (block.getBlockHeader().getHashPreviousBlock() == null) {
            dbStore.put(Constants.KEY_FIRST_BLOCK, hash);
        } else {
            //保存上一区块对该区块的key value映射
            dbStore.put(Constants.KEY_BLOCK_NEXT_PREFIX + block.getBlockHeader().getHashPreviousBlock(), hash);
        }
        //存入rocksDB
        dbStore.put(hash, Json.toJson(block));
        //设置最后一个block的key value
        dbStore.put(Constants.KEY_LAST_BLOCK, hash);

    }

}
