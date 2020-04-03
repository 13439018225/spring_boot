package com.huaxin.sboot.service;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.huaxin.sboot.bean.User;


public interface ILoginServcie {
	
	public User login(User user) throws Exception;
	
	//重置密码
	public int reUpPwd(User user)throws Exception;

}
