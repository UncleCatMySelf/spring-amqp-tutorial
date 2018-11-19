package com.github.uncalcatmyself.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by MySelf on 2018/11/19.
 */
@Service
public class MyService {

    @RabbitListener(queues = "myself.queue")
    public void recevie(String result){
        System.out.println("监听到消息了");
        System.out.println(result);
    }

}
