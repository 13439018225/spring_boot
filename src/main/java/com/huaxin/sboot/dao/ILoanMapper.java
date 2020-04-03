package com.huaxin.sboot.dao;

import java.util.List;
import java.util.Map;

import com.huaxin.sboot.bean.LoanApply;

public interface ILoanMapper {
	
    //增加
	public int add(LoanApply loanapply)throws Exception;
	
	//修改
    public int update(LoanApply loanapply)throws Exception;
	
	//删除 
	public int del(Map<String,Object> map)throws Exception;
	
	//通过ID查询对象
	public LoanApply getObjById(String id)throws Exception;
	
	//查询
	public List<LoanApply> query(Map<String,Object> map);
	
	//提交
	public int toSub(Map<String,Object> map)throws Exception;


}
