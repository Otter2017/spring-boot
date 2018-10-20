package org.springframework.boot.peach.conditional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FoodFactory implements Factory {
    private Logger logger= LoggerFactory.getLogger(FoodFactory.class);
    @Override
    public void createProduct() {
        logger.info("FoodFactory create a bread.");
    }
}
