package com.huaxin.sboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaxin.sboot.bean.User;
import com.huaxin.sboot.dao.ILoginMapper;
import com.huaxin.sboot.service.ILoginServcie;
import com.huaxin.sboot.util.MD5;

@Service
public class LoginServiceImpl implements ILoginServcie {

	@Autowired
	private ILoginMapper loginMapper;
	
	@Override
	public User login(User user) throws Exception {
		return loginMapper.login(user);
	}

	@Override
	public int reUpPwd(User user) throws Exception {
		//对密码加密
		String md5pwd=MD5.encryptPassword(user.getName(), user.getPassword(), null);
		user.setPassword(md5pwd);
		return loginMapper.reUpPwd(user);
	}

}
