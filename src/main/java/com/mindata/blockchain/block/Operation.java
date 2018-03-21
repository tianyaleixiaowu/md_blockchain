package com.mindata.blockchain.block;

/**
 * @author wuweifeng wrote on 2018/3/20.
 */
public interface Operation {
    byte ADD = 1;
    byte DELETE = -1;
    byte UPDATE = 2;

}
