package com.huaxin.sboot.service;

import java.util.List;
import java.util.Map;

import com.huaxin.sboot.bean.Deduct;


public interface IDeductServcie {
	//查询
	public String query(Map<String,Object> map)throws Exception;
	
	//查看划扣记录
	public String getDeductRecord(String id)throws Exception;
	
	//查询要划扣的记录
	public List<Deduct> getDeductListByIds(Map<String,Object> map)throws Exception;
	
	//划扣方法
	public String toDeduct(Deduct deduct)throws Exception;
	
	//保存预约时间
	public int saveAppiontDate(String ids,String appionDateTime)throws Exception;

}
