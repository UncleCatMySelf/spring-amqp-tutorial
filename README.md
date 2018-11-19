## Spring-amqp-tutorial

Spring AMQP项目将核心Spring概念应用于基于AMQP的消息传递解决方案的开发。它提供了一个“模板”作为发送和接收消息的高级抽象。它还为具有“侦听器容器”的消息驱动的POJO提供支持。这些库有助于管理AMQP资源，同时促进依赖注入和声明性配置的使用。在所有这些情况下，您将看到Spring Framework中与JMS支持的相似之处。

该项目由两部分组成; spring-amqp是基础抽象，spring-rabbit是RabbitMQ实现。

## 特征

* 用于异步处理入站消息的侦听器容器
* RabbitTemplate用于发送和接收消息
* RabbitAdmin用于自动声明队列，交换和绑定

## SpringBoot配置

### AMQP

高级消息队列协议（AMQP）是面向消息的中间件的平台中立的线级协议。Spring AMQP项目将核心Spring概念应用于基于AMQP的消息传递解决方案的开发。Spring Boot为通过RabbitMQ使用AMQP提供了一些便利，包括spring-boot-starter-amqp “Starter”。

#### RabbitMQ支持

RabbitMQ是一个基于AMQP协议的轻量级，可靠，可扩展且可移植的消息代理。Spring用于RabbitMQ通过AMQP协议进行通信。

RabbitMQ配置由外部配置属性控制 spring.rabbitmq.*。例如，您可以在以下部分声明以下部分 application.properties：

    spring.rabbitmq.host = localhost
    spring.rabbitmq.port = 5672
    spring.rabbitmq.username = guest
    spring.rabbitmq.password = guest


如果ConnectionNameStrategy上下文中存在bean，则它将自动用于命名由自动配置创建的连接ConnectionFactory。有关RabbitProperties更多支持的选项，请参阅 。

#### 发送消息

Spring的AmqpTemplate和AmqpAdmin被自动配置，您可以直接自动装配它们变成自己的豆类，如下面的例子：

```java
@Component
public class MyBean {

    private final AmqpAdmin amqpAdmin;
    private final AmqpTemplate amqpTemplate;

    @Autowired
    public MyBean(AmqpAdmin amqpAdmin,AmqpTemplate amqpTemplate){
        this.amqpAdmin = amqpAdmin;
        this.amqpTemplate = amqpTemplate;
    }

    //....
}
```

如有必要，任何org.springframework.amqp.core.Queue定义为bean的都会自动用于在RabbitMQ实例上声明相应的队列。

要重试操作，可以启用重试AmqpTemplate（例如，在代理连接丢失的情况下）：

    spring.rabbitmq.template.retry.enabled = true
    spring.rabbitmq.template.retry.initial-interval = 2s

默认情况下禁用重试。您还可以RetryTemplate 通过声明RabbitRetryTemplateCustomizerbean来以编程方式自定义。

#### 接收消息

当存在Rabbit基础结构时，可以使用任何bean来注释 @RabbitListener以创建侦听器端点。如果RabbitListenerContainerFactory 未定义，SimpleRabbitListenerContainerFactory则会自动配置默认值，您可以使用该spring.rabbitmq.listener.type属性切换到直接容器 。如果 定义了a MessageConverter或MessageRecovererbean，它将自动与默认工厂关联。

以下示例组件在someQueue队列上创建侦听器端点：

```java
@Component
public class MyBean {

    @RabbitListener(queues = "someQueue")
    public void processMessage(String content){
        //....
    }

}
```

如果您需要创建更多RabbitListenerContainerFactory实例，或者如果要覆盖默认实例，则Spring Boot提供了一个 SimpleRabbitListenerContainerFactoryConfigurer和一个 DirectRabbitListenerContainerFactoryConfigurer可用于初始化a SimpleRabbitListenerContainerFactory和a DirectRabbitListenerContainerFactory的设置，其设置与自动配置使用的工厂相同。

例如，以下配置类公开了另一个使用特定的工厂MessageConverter：

```java
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
```

然后您可以在任何@RabbitListener注释方法中使用工厂，如下所示：

```java
@Component
public class MyBean {

    @RabbitListener(queues = "someQueue",concurrency = "myFactory")
    public void processMessage(String content){
        //..
    }

}
```

您可以启用重试来处理侦听器抛出异常的情况。默认情况下，RejectAndDontRequeueRecoverer使用，但您可以定义MessageRecoverer 自己的。当重试耗尽时，如果代理配置了这样做，则拒绝该消息并将其丢弃或路由到死信交换。默认情况下，禁用重试。您还可以RetryTemplate通过声明RabbitRetryTemplateCustomizerbean来以编程方式自定义。

> 默认情况下，如果禁用重试并且侦听器抛出异常，则会无限期地重试传递。您可以通过两种方式修改此行为：将defaultRequeueRejected属性设置 为false以便尝试零重新传递，或抛出一个AmqpRejectAndDontRequeueException信号来拒绝该消息。后者是启用重试并且达到最大传递尝试次数时使用的机制。


## 快速开始

参考地址：

> [spring-amqp](https://github.com/spring-projects/spring-amqp)

> [spring-amqp-samples](https://github.com/spring-projects/spring-amqp-samples)

> [下文教程Github地址](https://github.com/UncleCatMySelf/spring-amqp-tutorial)

### 配置环境

```java
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

链接参数

```java
spring:
  rabbitmq:
    host: 192.168.192.132
    port: 5672
    username: guest
    password: guest
    template:
      retry:
        enabled: true # 代理链接丢失、启用重试AmqpTemplate
        initial-interval: 2s # 代理链接丢失、启用重试AmqpTemplate
```

### amqpAdmin

amqpAdmin可以用于交换器，队列以及绑定的申明，以下代码演示了amqpAdmin的主要用法。

```java
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
```

### amqpTemplate

快速使用的发送接收模板

```java
public class MyTemplate extends SampleApplicationTests {

    @Autowired
    AmqpTemplate amqpTemplate;

    @Test
    public void send(){
        amqpTemplate.convertAndSend("myself.queue","nihao");
    }

}
```

### 接收监听

```java
@Service
public class MyService {

    @RabbitListener(queues = "myself.queue")
    public void recevie(String result){
        System.out.println("监听到消息了");
        System.out.println(result);
    }

}
```
 
## Issues & Questions

https://github.com/UncleCatMySelf/spring-amqp-tutorial/issues

QQ Group：628793702

## About the author

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/%E5%85%AC%E4%BC%97%E5%8F%B7.png)

