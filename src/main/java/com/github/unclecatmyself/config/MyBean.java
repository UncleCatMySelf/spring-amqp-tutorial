package com.github.unclecatmyself.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by MySelf on 2018/11/19.
 */
@Component
public class MyBean {

    @RabbitListener(queues = "someQueue",concurrency = "myFactory")
    public void processMessage(String content){
        //..
    }

}
