package com.mindata.blockchain.core.manager;

import cn.hutool.core.util.StrUtil;
import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.block.db.DbTool;
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
    private DbTool dbTool;

    /**
     * 查找第一个区块
     *
     * @return 第一个Block
     */
    public Block getFirstBlock() {
        String firstBlockHash = dbTool.get(Constants.KEY_FIRST_BLOCK);
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
        String lastBlockHash = dbTool.get(Constants.KEY_LAST_BLOCK);
        if (StrUtil.isEmpty(lastBlockHash)) {
            return null;
        }
        return getBlockByHash(lastBlockHash);
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
        String nextHash = dbTool.get(Constants.KEY_BLOCK_NEXT_PREFIX + block.getHash());
        if (nextHash == null) {
            return null;
        }
        return getBlockByHash(nextHash);
    }

    private Block getBlockByHash(String hash) {
        String blockJson = dbTool.get(hash);
        return FastJsonUtil.toBean(blockJson, Block.class);
    }

    /**
     * 数据库里添加一个新的区块
     *
     * @param addBlockEvent
     *         addBlockEvent
     */
    @Order(1)
    @EventListener(AddBlockEvent.class)
    public void addBlock(AddBlockEvent addBlockEvent) {
        Block block = (Block) addBlockEvent.getSource();
        String hash = block.getHash();
        //存入rocksDB
        dbTool.put(hash, Json.toJson(block));
        //设置最后一个block的key value
        dbTool.put(Constants.KEY_LAST_BLOCK, hash);
        //如果没有上一区块，说明该块就是创世块
        if (block.getBlockHeader().getHashPreviousBlock() == null) {
            dbTool.put(Constants.KEY_FIRST_BLOCK, hash);
        } else {
            //保存上一区块对该区块的key value映射
            dbTool.put(Constants.KEY_BLOCK_NEXT_PREFIX + block.getBlockHeader().getHashPreviousBlock(), hash);
        }

    }

}
