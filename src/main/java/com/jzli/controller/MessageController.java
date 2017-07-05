package com.jzli.controller;

import com.jzli.bean.MailMessage;
import com.jzli.service.Service;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    private Service service;

    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public Object hello() {
        return "hello";
    }

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public Object test() {
        service.test();
        return "true";
    }

    @RequestMapping(path = "/send", method = RequestMethod.POST)
    @ApiOperation(value = "发送邮件", httpMethod = "POST", notes = "发送邮件")
    public Object send(@RequestBody MailMessage mm) {
       service.send(mm);
        return "true";
    }
}
