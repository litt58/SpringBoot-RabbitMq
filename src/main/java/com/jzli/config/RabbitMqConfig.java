package com.jzli.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2017/7/5
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：
 * ========================================================
 */
@Configuration
public class RabbitMqConfig {
    @Bean
    public Queue queue() {
        return new Queue("mail");
    }
}
