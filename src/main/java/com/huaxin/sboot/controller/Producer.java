package com.huaxin.sboot.controller;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huaxin.sboot.bean.User;


//@RestController
public class Producer {

	//注入jmstemplate
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @RequestMapping("/sendMsg")
    
    @Scheduled(cron="0/1 * * * * ? ")
    public void sendMsg() {
        jmsMessagingTemplate.convertAndSend("test-queue", "你好,我是文本信息");
        System.out.println("msg发送成功");
    }
    
    @Scheduled(cron="0/1 * * * * ? ")
    @RequestMapping("/sendObj")
    public void sendObj() {
        User user=new User();
        user.setName("陈向前");
        user.setEmail("fdz@qq.com");
        jmsMessagingTemplate.convertAndSend("test-obj", user);
        System.out.println("obj发送成功");
    }
    @Scheduled(cron="0/1 * * * * ? ")
    @RequestMapping("/sendMap")
    public void sendMap() {
        Map<String,String> map = new HashMap<String,String>();
        map.put("mobile", "13888888888");
        map.put("content", "王总喜提兰博基尼");
        jmsMessagingTemplate.convertAndSend("test-map", map);
        System.out.println("map发送成功");
    }
    
   
    

}
