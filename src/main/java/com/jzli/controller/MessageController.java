package com.jzli.controller;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2017/7/5
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：提供消息的发送接口和异步处理消息的监听方法
 * ========================================================
 */
@RestController
@RequestMapping(value = "/message")
public class MessageController {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @RequestMapping(path="/hello")
    public Object hello(){
        return "hello";
    }

    @RequestMapping(path="/send")
    public Object send(){
        for(int i=1;i<101;i++){
            amqpTemplate.convertAndSend("hello",i+"");
        }
        return "true";
    }


    @RabbitListener(queues = {"hello"})
    @Async
    public void receive(Message message, Channel channel) throws Exception {
        TimeUnit.SECONDS.sleep(1);
        System.out.println(Thread.currentThread().getName()+"\t"+new String(message.getBody()));
    }
}
