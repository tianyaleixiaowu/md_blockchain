package com.mindata.blockchain.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author wuweifeng wrote on 2018/3/17.
 */
@Component
public class AppId {
    /**
     * 该客户的唯一标志
     */
    @Value("${appId}")
    private String appId;

    public static String value;

    @PostConstruct
    public void init() {
        value = appId;
    }
}
