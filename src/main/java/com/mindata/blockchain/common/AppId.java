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
     * 节点的唯一标志
     */
    @Value("${appId}")
    private String appId;
    /**
     * 该客户的唯一标志
     */
    @Value("${name}")
    private String name;

    public static String value;
    public static String nameValue;

    @PostConstruct
    public void init() {
        value = appId;
        nameValue = name;
    }
}
