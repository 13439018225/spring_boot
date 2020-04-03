package com.huaxin.sboot.controller;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.huaxin.sboot.bean.User;


//@Controller 
public class Consumer {

	@JmsListener(destination = "test-queue")
    public void readMsg(String text) {
        System.out.println("接收文件消息：" + text);
    }

	//@JmsListener(destination = "test-obj")
    public void readEntity(ObjectMessage o) {
        try {
			System.out.println("接收到对象消息：" + o.getObject());
		} catch (JMSException e) {
			e.printStackTrace();
		}
    }
	
	@JmsListener(destination = "test-obj")
    public void readObj(ObjectMessage o) {
        try {
        	String json=JSONObject.toJSONString(o.getObject());
        	System.out.println("对象转json=="+json);
        	System.out.println("json转对象=="+JSONObject.parseObject(json, User.class).getName());
			System.out.println("接收到消息：" + o.getObject());
		} catch (JMSException e) {
			e.printStackTrace();
		}
    }
	
    @JmsListener(destination = "test-map")
    public void readMap(Map map) {
    	System.out.println("接收到MAP消息：" + map);
    }

}
