package org.springframework.boot.peach.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FixedRateSchedule {
    private Logger log = LoggerFactory.getLogger(FixedRateSchedule.class);

    //* fixedRate 固定执行时长，每隔这个时长执行一次，不管整个方法执行了多久
    @Scheduled(fixedRate = 5000, initialDelay = 100)
    void sayHello() {
        log.info("Hello,Fixed Rate schedule.");
    }
}
