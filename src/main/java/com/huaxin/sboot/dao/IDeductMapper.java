package com.huaxin.sboot.dao;

import java.util.List;
import java.util.Map;

import com.huaxin.sboot.bean.Deduct;
import com.huaxin.sboot.bean.LoanApply;

public interface IDeductMapper {
	//查询
	public List<LoanApply> query(Map<String,Object> map);
	
	//查看划扣记录
	public List<Deduct> getDeductRecord(String id)throws Exception;
	
	//插入划扣记录
	public int AddDeduct(Deduct deduct)throws Exception;
	
	//查询要划扣的记录
	public List<Deduct> getDeductListByIds(Map<String,Object> map)throws Exception;
	
	//更新出价申请状态
	public int upLoanAppState(Map<String,Object> map)throws Exception;
	
	public int saveAppiontDate(Map<String,Object> map) throws Exception;
	
	//定时划扣
	public List<Deduct> getAppiontRecord() throws Exception;
	
}
