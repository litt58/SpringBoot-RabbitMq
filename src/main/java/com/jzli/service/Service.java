package com.jzli.service;

import com.alibaba.fastjson.JSONObject;
import com.jzli.bean.MailMessage;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

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
@Component
public class Service {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void test() {
        for (int i = 1; i < 101; i++) {
            amqpTemplate.convertAndSend("mail", i + "");
        }
    }

    public void send(MailMessage mm) {
        amqpTemplate.convertAndSend("mail", JSONObject.toJSONString(mm));
    }

    @RabbitListener(queues = {"mail"})
    @Async
    public void receive(Message message, Channel channel) throws Exception {
        String s = new String(message.getBody(), "UTF-8");
        MailMessage mailMessage = JSONObject.parseObject(s, MailMessage.class);
        System.out.println(Thread.currentThread().getName() + "\t" + mailMessage);
        sendSimpleMail(mailMessage);
    }

    public void sendSimpleMail(MailMessage mailMessage) throws Exception {
        for (String to : mailMessage.getTo()) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("736056512@qq.com");
            message.setTo(to);
            message.setSubject(mailMessage.getSubject());
            message.setText(mailMessage.getContent());
            mailSender.send(message);
        }
    }
}
