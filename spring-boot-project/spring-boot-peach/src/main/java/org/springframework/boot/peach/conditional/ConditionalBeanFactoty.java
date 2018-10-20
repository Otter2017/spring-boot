package org.springframework.boot.peach.conditional;


import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionalBeanFactoty {

    // 当配置文件中的 factory.PhoneFactory = true 时
    @Bean
    @ConditionalOnProperty("factory.PhoneFactory")
    public Factory createPhone() {
        return new PhoneFactory();
    }

    // 当配置文件中的 factory.FoodFactory = true 时，因为和上面的使用的不是同一个配置，有可能因为配置冲突产生错误
    @Bean
    @ConditionalOnExpression(value = "${factory.FoodFactory:true}")
    public Factory createFood() {
        return new FoodFactory();
    }
}
