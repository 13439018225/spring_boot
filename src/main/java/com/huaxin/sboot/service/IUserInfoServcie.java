package com.huaxin.sboot.service;

import java.util.List;
import java.util.Map;

import com.huaxin.sboot.bean.UserInfo;

public interface IUserInfoServcie {
	
	//保存
	public int save(UserInfo userinfo);
	
	//删除 
	public int del(String id);
	
	//通过ID查询对象
	public UserInfo getObjById(String id);
	
	//查询
	public List<UserInfo> query(Map<String,Object> map);

}
