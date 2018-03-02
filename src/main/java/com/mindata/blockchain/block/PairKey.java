/**
 * Project Name:trustsql_sdk
 * File Name:PairKey.java
 * Package Name:com.tencent.trustsql.sdk.bean
 * Date:Jul 26, 201710:27:04 AM
 * Copyright (c) 2017, Tencent All Rights Reserved.
 */

package com.mindata.blockchain.block;

/**
 * ClassName:PairKey <br/>
 * Date:     Jul 26, 2017 10:27:04 AM <br/>
 * @author Rony
 * @since JDK 1.7
 */
public class PairKey {

    private String publicKey;
    private String privateKey;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

}

