package com.huaxin.sboot.webservice;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.huaxin.sboot.bean.User;
import com.huaxin.sboot.dao.IUserMapper;

@Service
//@WebService
public class WebUserServiceImpl implements IWebUserServcie{

	@Autowired
	private IUserMapper userMapper;
	
	@Override
	public User getObjById(String id) {
		User u=null;
		try {
			u=userMapper.getObjById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}

	@Override
	public int save(String user) {
		int k=0;
		try {
			//将json格式字符串转化成实体类对象
			User u=JSONObject.parseObject(user, User.class);
			k=userMapper.add(u);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return k;
	}

}
