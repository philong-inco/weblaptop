package com.dantn.weblaptop.config;

import com.dantn.weblaptop.service.impl.PhieuGiamGiaJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuarzConfig {
    @Bean
    public JobDetail phieuGiamGiaJobDetail() {
        return JobBuilder.newJob(PhieuGiamGiaJob.class)
                .withIdentity("phieuGiamGiaJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger phieuGiamGiaTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(phieuGiamGiaJobDetail())
                .withIdentity("phieuGiamGiaTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10)
                        .repeatForever())
                .build();
    }
}
