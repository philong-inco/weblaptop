package com.dantn.weblaptop.util.expiredBill;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EnableScheduling
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class ExpiredBillScheduler {

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduledFixedDelayTask(){
       System.out.println("Chay 12 h đeme");
    }
}
