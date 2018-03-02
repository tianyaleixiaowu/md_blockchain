package com.mindata.blockchain.common;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * @author wuweifeng wrote on 2018/2/27.
 */
public class Sha {
    public static void sha256(String input) {
        System.out.println(DigestUtil.sha256Hex(input));
    }

}
