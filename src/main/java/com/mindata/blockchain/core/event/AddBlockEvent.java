package com.mindata.blockchain.core.event;

import com.mindata.blockchain.block.Block;
import org.springframework.context.ApplicationEvent;

/**
 * 确定生成block的Event（添加到rocksDB，执行sqlite语句，发布给其他节点）
 * @author wuweifeng wrote on 2018/3/15.
 */
public class AddBlockEvent extends ApplicationEvent {
    public AddBlockEvent(Block block) {
        super(block);
    }
}
