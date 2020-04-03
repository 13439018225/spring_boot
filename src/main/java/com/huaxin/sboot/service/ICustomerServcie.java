package com.huaxin.sboot.service;

import java.util.Map;

import com.huaxin.sboot.bean.Customer;


public interface ICustomerServcie {
	
	//保存
	public int save(Customer customer) throws Exception;
	
	//删除 
	public int del(String id)throws Exception;
	
	//通过ID查询对象
	public Customer getObjById(String id)throws Exception;
	
	//查询
	public String query(Map<String,Object> map)throws Exception;
	
	//获取单据编号
	public int getCode(Map<String,Object> map)throws Exception;

}
