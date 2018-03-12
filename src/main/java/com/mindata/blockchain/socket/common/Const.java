package com.mindata.blockchain.socket.common;

/**
 * @author wuweifeng wrote on 2018/3/9.
 */
public interface Const {
    /**
     * 服务器地址
     */
    final String SERVER = "127.0.0.1";
    /**
     * 服务器分组名
     */
    final String GROUP_NAME = "block_group";
    /**
     * 监听端口
     */
    final int PORT = 6789;

    /**
     * 心跳超时时间
     */
    final int TIMEOUT = 5000;

    final String CHARSET = "utf-8";
}
