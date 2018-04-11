package com.mindata.blockchain.socket.mapclean;

import com.mindata.blockchain.socket.holder.RequestResponseMap;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时清理发出的Message的缓存，避免长时间没回应没处理的message占空间
 * @author wuweifeng wrote on 2018/3/20.
 */
@Component
public class RequestMapCleaner {
    /**
     * 10分钟清理一次，单位毫秒
     */
    @Scheduled(fixedRate = 600000)
    public void companyStateRefreshTask() {
        RequestResponseMap.clearTimeOutKey();
    }
}
