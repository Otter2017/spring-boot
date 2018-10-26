package org.springframework.boot.peach.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
//@Configuration
public class FixedDelaySchedule {
	@Value("${fixedDelaySchedule.fixedDelay}")
	private static final int fixedDelayInterval=80;

	@Value("${fixedDelaySchedule.message}")
	private String message;

    private Logger log = LoggerFactory.getLogger(FixedDelaySchedule.class);

    // fixedDelay 固定等候时长,执行完之后再等待设定的时长
    @Scheduled(fixedDelay = fixedDelayInterval, initialDelay = 100)
    void sayHello() {
        log.info("Hello,Fixed Delay schedule,fixed delay = "+fixedDelayInterval);
        try {
            Thread.currentThread().sleep(1000);
        } catch (Exception ex){
            ex.printStackTrace();
        }
		log.info(message);
    }
}
