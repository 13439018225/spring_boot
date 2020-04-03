package com.huaxin.sboot.dao;

import java.util.List;
import java.util.Map;

import com.huaxin.sboot.bean.Address;
import com.huaxin.sboot.bean.Customer;

public interface ICusbankMapper {
	
    //增加
	public int add(Customer customer)throws Exception;
	
	//修改
    public int update(Customer customer)throws Exception;
	
	//删除 
	public int del(Map<String,Object> map)throws Exception;
	
	//通过ID查询对象
	public Customer getObjById(String id)throws Exception;
	
	//查询
	public List<Customer> query(Map<String,Object> map);
	
	//查询所有客户信息
	public List<Customer> getCustomers() throws Exception;
	
	//激活
	public int toActive(String id,String cid)throws Exception;
	
	//取地址
	public List<Address> getAddress(String pid) throws Exception;

}
