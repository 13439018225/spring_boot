package com.huaxin.sboot.service;

import java.util.Map;

import com.huaxin.sboot.bean.LoanApply;


public interface ILoanServcie {
	
	//保存
	public int save(LoanApply loanapply) throws Exception;
	
	//删除 
	public int del(String id)throws Exception;
	
	//通过ID查询对象
	public LoanApply getObjById(String id)throws Exception;
	
	//查询
	public String query(Map<String,Object> map)throws Exception;
	
	//提交
	public int toSub(String id)throws Exception;

}
