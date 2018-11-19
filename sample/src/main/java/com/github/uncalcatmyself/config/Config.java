package com.github.uncalcatmyself.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by MySelf on 2018/11/19.
 */
@Configuration
@EnableRabbit
public class Config {

    @Bean
    public MessageConverter MessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

}
