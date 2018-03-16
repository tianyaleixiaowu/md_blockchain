package com.mindata.blockchain.core.sqlite;

import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.core.event.AddBlockEvent;
import com.mindata.blockchain.socket.holder.RequestResultHolder;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author wuweifeng wrote on 2018/3/15.
 */
@Component
public class SqliteManager {
    /**
     * 数据库里添加一个新的区块，执行数据库语句
     *
     * @param addBlockEvent
     *         addBlockEvent
     */
    @Order(2)
    @EventListener(AddBlockEvent.class)
    public void executeSql(AddBlockEvent addBlockEvent) {
        String hash = (String) addBlockEvent.getSource();
        Block block = RequestResultHolder.getTempBlock(hash);
        RequestResultHolder.clearTempBlock(hash);

    }
}
