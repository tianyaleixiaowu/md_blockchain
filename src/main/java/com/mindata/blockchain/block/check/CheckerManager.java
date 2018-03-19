package com.mindata.blockchain.block.check;

import cn.hutool.core.util.StrUtil;
import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.core.manager.DbBlockManager;
import com.mindata.blockchain.socket.body.CheckBlockBody;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/3/14.
 */
@Component
public class CheckerManager {
    @Resource
    private BlockChecker blockChecker;
    @Resource
    private DbBlockManager dbBlockManager;

    public CheckBlockBody check(Block block) {
        int number = blockChecker.checkNum(block);
        if (number != 0) {
             return new CheckBlockBody(-1, "block的number不合法");
        }
        int time = blockChecker.checkTime(block);
        if (time != 0) {
            return new CheckBlockBody(-4, "block的时间错误");
        }

        return new CheckBlockBody(0, "OK", block);
    }

    /**
     * 校验block可否做为本地的最新block的next block
     * @param block
     * block
     * @return
     * 是否OK
     */
    public CheckBlockBody checkIsNextBlock(Block block) {
        //基础格式、权限校验
        CheckBlockBody checkBlockBody = check(block);
        //校验能否作为本地最新区块的next block
        if (!StrUtil.equals(block.getBlockHeader().getHashPreviousBlock(), dbBlockManager.getLastBlock().getHash())) {
            return new CheckBlockBody(-10, "不能作为本地最新block的next block");
        }
        return checkBlockBody;
    }

}
