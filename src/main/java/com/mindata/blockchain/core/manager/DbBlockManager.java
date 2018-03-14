package com.mindata.blockchain.core.manager;

import cn.hutool.core.util.StrUtil;
import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.block.db.DbTool;
import com.mindata.blockchain.common.Constants;
import com.mindata.blockchain.common.FastJsonUtil;
import org.springframework.stereotype.Service;

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

}
