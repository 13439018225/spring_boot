package com.huaxin.sboot.service;

import java.util.Map;

import com.huaxin.sboot.bean.Customer;


public interface ICusbankServcie {
	
	//保存
	public int save(Customer customer) throws Exception;
	
	//删除 
	public int del(String id)throws Exception;
	
	//通过ID查询对象
	public Customer getObjById(String id)throws Exception;
	
	//查询
	public String query(Map<String,Object> map)throws Exception;
	
	//查询所有客户信息
	public String getCustomers()throws Exception;
	
	//激活
	public int toActive(String id,String cid)throws Exception;
	
	//取地址
	public String getAddress(String pid)throws Exception;
	

}
