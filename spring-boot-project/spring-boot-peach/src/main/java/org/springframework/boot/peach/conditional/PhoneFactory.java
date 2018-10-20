package org.springframework.boot.peach.conditional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhoneFactory implements Factory {
    private Logger logger= LoggerFactory.getLogger(PhoneFactory.class);

    @Override
    public void createProduct() {
        logger.info("Phone factory create an iPhone.");
    }
}
