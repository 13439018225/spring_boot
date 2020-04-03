package com.huaxin.sboot.service;

import java.util.Map;

import com.huaxin.sboot.bean.Message;


public interface IMessServcie {
	
	//查询
	public String query(Map<String,Object> map)throws Exception;
	
	//mongo查询
	public String mongoQuery(Map<String,Object> map)throws Exception;
	
	//保存
	public int save(Message message)throws Exception;
	
	//删除
	public int del(String ids)throws Exception;
	
	//通过ID查询要修改的数据
	public String getObjById(String id)throws Exception;
	
	//发送邮件
	public String sendEmail(String ids)throws Exception;
	
	//发送短信
	public String sendEms(String ids)throws Exception;

}
