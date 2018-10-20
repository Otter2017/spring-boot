package org.springframework.boot.peach.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FixedDelaySchedule {

    private Logger log = LoggerFactory.getLogger(FixedDelaySchedule.class);

    // fixedDelay 固定等候时长,执行完之后再等待设定的时长
    @Scheduled(fixedDelay = 5000, initialDelay = 100)
    void sayHello() {
        log.info("Hello,Fixed Delay schedule.");
        try {
            Thread.currentThread().sleep(1000);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
