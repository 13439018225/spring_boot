package com.huaxin.sboot.dao;

import java.util.List;
import java.util.Map;

import com.huaxin.sboot.bean.Customer;

public interface ICustomerMapper {
	
    //增加
	public int add(Customer customer)throws Exception;
	
	//修改
    public int update(Customer customer)throws Exception;
	
	//删除 
	public int del(Map<String,Object> map)throws Exception;
	
	//删除该客户对于的账号信息
	public int delCustomerBank(Map<String,Object> map)throws Exception;
	
	//通过ID查询对象
	public Customer getObjById(String id)throws Exception;
	
	//查询
	public List<Customer> query(Map<String,Object> map);
	
	
	

}
