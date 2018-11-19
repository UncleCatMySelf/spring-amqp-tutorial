package com.github.uncalcatmyself;

import org.junit.Before;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by MySelf on 2018/11/19.
 */
public class MyTemplate extends SampleApplicationTests {

    @Autowired
    AmqpTemplate amqpTemplate;

    @Test
    public void send(){
        amqpTemplate.convertAndSend("myself.queue","nihao");
    }

}
