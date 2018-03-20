package com.mindata.blockchain.socket.mapclean;

import com.mindata.blockchain.socket.client.RequestResponseMap;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时清理
 * @author wuweifeng wrote on 2018/3/20.
 */
@Component
public class RequestMapCleaner {
    /**
     * 10分钟清理一次
     */
    @Scheduled(cron = "*/600 * * * * ?")
    public void companyStateRefreshTask() {
        System.out.println("清理开始");
        RequestResponseMap.clearTimeOutKey();
    }
}
