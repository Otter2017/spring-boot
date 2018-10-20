package org.springframework.boot.peach.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronSchedule {
    private Logger log = LoggerFactory.getLogger(FixedRateSchedule.class);

    //* cron 设置任务开始时间点和间隔,在秒数为0 15和35时执行
    @Scheduled(cron = "0,15,35 * * * * ?")
    void sayHello() {
        log.info("Hello,Cron schedule schedule.");
        try {
            Thread.currentThread().sleep(1000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
