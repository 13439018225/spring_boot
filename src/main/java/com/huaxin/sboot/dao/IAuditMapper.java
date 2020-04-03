package com.huaxin.sboot.dao;

import java.util.List;
import java.util.Map;

import com.huaxin.sboot.bean.Customer;
import com.huaxin.sboot.bean.LoanApply;
import com.huaxin.sboot.bean.Product;

public interface IAuditMapper {
	
	//通过ID查询对象
	public LoanApply getObjById(String id)throws Exception;
	
	//查询
	public List<LoanApply> query(Map<String,Object> map);
	
	//审核
	public int toAudit(Map<String,Object> map)throws Exception;
	
	//通过产品ID,查询产品信息
	public Product getProductById(String pid)throws Exception;
	
	//通过客户ID，查询账号信息
	public Customer getCusBanktById(String cid)throws Exception;

}
