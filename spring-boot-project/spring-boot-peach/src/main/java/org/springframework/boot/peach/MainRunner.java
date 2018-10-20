package org.springframework.boot.peach;

import org.springframework.boot.peach.autoconfig.Computer;
import org.springframework.boot.peach.conditional.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;

@Component
public class MainRunner implements CommandLineRunner {
    @Autowired
    private Computer computer;

    @Autowired
    private BeanFactory beanFactory;

    Logger log = LoggerFactory.getLogger(MainRunner.class);

    @Override
    public void run(String... args) throws Exception {
        testAutoConfig();
        testAutowiredConditional();
        testNumberFormat();
    }

    // 测试根据配置文件注入实体类
    public void testAutoConfig() {
        log.info(computer.buy());
    }

    // 测试按条件装配
    public void testAutowiredConditional() {
        Factory factory = beanFactory.getBean(Factory.class);
        factory.createProduct();
    }


    // 测试数字格式化,和系统语言及设置有关
    public void testNumberFormat() {

        NumberFormat numberFormat = NumberFormat.getNumberInstance();

        // 315,464,154
        String longNumber = numberFormat.format(315464154L);
        // 3.142
        String doubleNumber = numberFormat.format(3.1415926535);
        log.info(longNumber);
        log.info(doubleNumber);
    }

}