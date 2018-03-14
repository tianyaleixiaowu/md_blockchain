package com.mindata.blockchain.block.check.local;

import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.block.check.BlockChecker;

/**
 * @author wuweifeng wrote on 2018/3/13.
 */
public class DbBlockChecker implements BlockChecker {
    private Block localBlock;

    public DbBlockChecker(Block localBlock) {
        this.localBlock = localBlock;
    }

    @Override
    public int checkNum(Block block) {
        //本地区块大于请求生成的号，则拒绝，并返回自己的区块号
        if (localBlock != null && localBlock.getBlockHeader().getNumber() >= block.getBlockHeader().getNumber()) {
            //同意生成区块
            return localBlock.getBlockHeader().getNumber();
        }

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
        //新区块的生成时间比本地的还小
        if (localBlock != null && block.getBlockHeader().getTimeStamp() <= localBlock.getBlockHeader().getTimeStamp()) {
            //拒绝
            return -1;
        }
        return 0;
    }
}
