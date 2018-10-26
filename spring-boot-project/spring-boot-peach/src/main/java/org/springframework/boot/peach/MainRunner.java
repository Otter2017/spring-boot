package org.springframework.boot.peach;

import org.springframework.boot.peach.autoconfig.Computer;
import org.springframework.boot.peach.autoconfig.PeachVersion;
import org.springframework.boot.peach.conditional.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.peach.mybatis.entity.User;
import org.springframework.boot.peach.mybatis.service.UserService;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		testMybatis();
		testVersionInfo();
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

	@Autowired
	private UserService userService;

	public void testMybatis(){
		Map<String, String> params = new HashMap<>();
		params.put("UserName", "crab");
		params.put("Password", "123");
		List<User> users = userService.login(params);
		for (User user:users){
			System.out.println(user);
		}
	}

	private void testVersionInfo(){
		PeachVersion version =beanFactory.getBean(PeachVersion.class);
		System.out.println(version.getVersionInfo());
	}

}