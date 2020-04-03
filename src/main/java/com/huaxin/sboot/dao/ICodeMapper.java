package com.huaxin.sboot.dao;

import java.util.Map;

public interface ICodeMapper {
	//修改
    public int updCode(Map<String,Object> map)throws Exception;
	
	//查询
	public int getCode(Map<String,Object> map)throws Exception;
	
	//每天插入数据
	public int insertCode(Map<String,Object> map)throws Exception;
}
