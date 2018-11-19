package com.github.uncalcatmyself;

import org.junit.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by MySelf on 2018/11/19.
 */
public class MyAdmin extends SampleApplicationTests {

    @Autowired
    AmqpAdmin amqpAdmin;

    @Test
    public void contextLoads(){
        //新建交换器
        amqpAdmin.declareExchange(new DirectExchange("myself.directExchange"));
        //新建队列
        amqpAdmin.declareQueue(new Queue("myself.queue",true));
        //绑定
        amqpAdmin.declareBinding(new Binding("myself.queue",Binding.DestinationType.QUEUE,"myself.directExchange","",null));
    }

    @Test
    public void remove(){
        //移除交换器
        amqpAdmin.deleteExchange("myself.directExchange");
        //移除队列
        amqpAdmin.deleteQueue("myself.queue");
    }
}
