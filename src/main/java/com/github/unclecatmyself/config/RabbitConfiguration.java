package com.github.unclecatmyself.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by MySelf on 2018/11/19.
 */
@Configuration
public class RabbitConfiguration {

    ConnectionFactory connectionFactory;

    @Bean
    public SimpleRabbitListenerContainerFactory myFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer){
        SimpleRabbitListenerContainerFactory factory =
                new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory,connectionFactory);
        factory.setMessageConverter(myMessageConverter());
        return factory;
    }

    private MessageConverter myMessageConverter() {
        //..
        return null;
    }

}
