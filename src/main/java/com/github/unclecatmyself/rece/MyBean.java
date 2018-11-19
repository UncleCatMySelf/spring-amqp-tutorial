package com.github.unclecatmyself.rece;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by MySelf on 2018/11/19.
 */
@Component
public class MyBean {

    @RabbitListener(queues = "someQueue")
    public void processMessage(String content){
        //....
    }

}
