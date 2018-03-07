package com.mindata.blockchain.common;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * @author wuweifeng wrote on 2018/2/27.
 */
public class Sha256 {
    public static String sha256(String input) {
        return DigestUtil.sha256Hex(input);
    }

}
