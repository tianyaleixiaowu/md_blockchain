package com.mindata.blockchain.block.check;

import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.block.check.local.DbBlockChecker;
import com.mindata.blockchain.core.manager.DbBlockManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/3/14.
 */
@Configuration
public class CheckerConfig {
    @Resource
    private DbBlockManager dbBlockManager;

    @Bean
    public BlockChecker initChecker() {
        Block localBlock = dbBlockManager.getLastBlock();
        return new DbBlockChecker(localBlock);
    }

}
