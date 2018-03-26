package com.mindata.blockchain.block.check.local;

import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.block.check.BlockChecker;
import com.mindata.blockchain.core.manager.DbBlockManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/3/13.
 */
@Component
public class DbBlockChecker implements BlockChecker {
    @Resource
    private DbBlockManager dbBlockManager;

    @Override
    public int checkNum(Block block) {
        Block localBlock = getLastBlock();
        //本地区块大于请求生成的号，则拒绝，并返回自己的区块号
        //if (localBlock != null && localBlock.getBlockHeader().getNumber() >= block.getBlockHeader().getNumber()) {
        //    //同意生成区块
        //    return localBlock.getBlockHeader().getNumber();
        //}

        //同意
        return 0;
    }

    @Override
    public int checkPermission(Block block) {
        return 0;
    }

    @Override
    public int checkHash(Block block) {
        return 0;
    }

    @Override
    public int checkTime(Block block) {
        Block localBlock = getLastBlock();
        //新区块的生成时间比本地的还小
        if (localBlock != null && block.getBlockHeader().getTimeStamp() <= localBlock.getBlockHeader().getTimeStamp()) {
            //拒绝
            return -1;
        }
        return 0;
    }

    private Block getLastBlock() {
        return dbBlockManager.getLastBlock();
    }
}
