package com.huaxin.sboot.dao;

import java.util.List;
import java.util.Map;

import com.huaxin.sboot.bean.Product;

public interface IProductMapper {
	
    //增加
	public int add(Product product)throws Exception;
	
	//修改
    public int update(Product product)throws Exception;
	
	//删除 
	public int del(String id)throws Exception;
	
	//查询
	public List<Product> query(Map<String,Object> map);
	
	//查询全部产品
	public List<Product> getProducts()throws Exception;
}
