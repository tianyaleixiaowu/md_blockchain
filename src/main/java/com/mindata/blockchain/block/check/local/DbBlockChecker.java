package com.mindata.blockchain.block.check.local;

import cn.hutool.core.util.StrUtil;
import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.block.check.BlockChecker;
import com.mindata.blockchain.core.manager.DbBlockManager;
import com.mindata.blockchain.core.manager.PermissionManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 使用本地存储的权限、Block信息对新来的block进行校验
 * @author wuweifeng wrote on 2018/3/13.
 */
@Component
public class DbBlockChecker implements BlockChecker {
    @Resource
    private DbBlockManager dbBlockManager;
    @Resource
    private PermissionManager permissionManager;

    @Override
    public int checkNum(Block block) {
        Block localBlock = getLastBlock();
        int localNum = 0;
        if (localBlock != null) {
            localNum = localBlock.getBlockHeader().getNumber();
        }
        //本地区块+1等于新来的区块时才同意
        if (localNum + 1 == block.getBlockHeader().getNumber()) {
            //同意生成区块
            return 0;
        }

        //拒绝
        return -1;
    }

    @Override
    public int checkPermission(Block block) {
        //校验对表的操作权限
        return permissionManager.checkPermission(block) ? 0 : -1;
    }

    @Override
    public int checkHash(Block block) {
        Block localLast = getLastBlock();
        //创世块可以，或者新块的prev等于本地的last hash也可以
        if (localLast == null && block.getBlockHeader().getHashPreviousBlock() == null) {
            return 0;
        }
        if (localLast != null && StrUtil.equals(localLast.getHash(), block.getBlockHeader().getHashPreviousBlock())) {
            return 0;
        }
        return -1;
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
