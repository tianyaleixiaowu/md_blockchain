package com.mindata.blockchain.block.check;

import com.mindata.blockchain.block.Block;
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

    public CheckBlockBody check(Block block) {
        int number = blockChecker.checkNum(block);
        if (number != 0) {
             return new CheckBlockBody(-1, "block的number不合法");
        }
        int time = blockChecker.checkTime(block);
        if (time != 0) {
            return new CheckBlockBody(-4, "block的时间错误");
        }

        return new CheckBlockBody(0, "OK");
    }

}
