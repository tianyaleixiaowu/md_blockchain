package com.mindata.blockchain.core.service;

import com.mindata.blockchain.block.PairKey;
import com.mindata.blockchain.common.TrustSDK;
import com.mindata.blockchain.common.exception.TrustSDKException;
import org.springframework.stereotype.Service;

/**
 * @author wuweifeng wrote on 2018/3/7.
 */
@Service
public class PairKeyService {

    /**
     * 生成公私钥对
     * @return PairKey
     * @throws TrustSDKException TrustSDKException
     */
    public PairKey generate() throws TrustSDKException {
        return TrustSDK.generatePairKey(true);
    }
}
