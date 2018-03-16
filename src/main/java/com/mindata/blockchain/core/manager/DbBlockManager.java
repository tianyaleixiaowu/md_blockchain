package com.mindata.blockchain.core.manager;

import cn.hutool.core.util.StrUtil;
import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.block.db.DbTool;
import com.mindata.blockchain.common.Constants;
import com.mindata.blockchain.common.FastJsonUtil;
import com.mindata.blockchain.core.event.AddBlockEvent;
import com.mindata.blockchain.socket.holder.RequestResultHolder;
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
     * 获取最后一个区块信息
     * @return
     * 最后一个区块
     */
    public Block getLastBlock() {
        String lastBlockHash = dbTool.get(Constants.KEY_LAST_BLOCK);
        if (StrUtil.isEmpty(lastBlockHash)) {
            return null;
        }
        return getBlockByHash(lastBlockHash);
    }

    private Block getBlockByHash(String hash) {
        String blockJson = dbTool.get(hash);
        return FastJsonUtil.toBean(blockJson, Block.class);
    }

    /**
     * 数据库里添加一个新的区块
     * @param addBlockEvent
     * addBlockEvent
     */
    @Order(1)
    @EventListener(AddBlockEvent.class)
    public void addBlock(AddBlockEvent addBlockEvent) {
        String hash = (String) addBlockEvent.getSource();
        //存入rocksDB
        dbTool.put(hash, Json.toJson(RequestResultHolder.getTempBlock(hash)));
    }

}
