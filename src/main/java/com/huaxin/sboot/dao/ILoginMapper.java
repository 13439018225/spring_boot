package com.huaxin.sboot.dao;

import com.huaxin.sboot.bean.User;


public interface ILoginMapper {
	//登录
	public User login(User user) throws Exception;
	
	//重置密码
	public int reUpPwd(User user) throws Exception;
    

}
