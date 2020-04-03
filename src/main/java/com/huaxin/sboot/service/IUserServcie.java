package com.huaxin.sboot.service;

import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.huaxin.sboot.bean.User;


public interface IUserServcie {
	
	//保存
	public int save(User user) throws Exception;
	
	//删除 
	public int del(String id)throws Exception;
	
	//通过ID查询对象
	public User getObjById(String id)throws Exception;
	
	//查询
	public String query(Map<String,Object> map)throws Exception;
	
	//导出excel
	public HSSFWorkbook exportExcel(Map<String,Object> map);
	
	//保存用户与角色
	public int saveUserRole(String userid,String roleid)throws Exception;
	
	//修改密码
	public int saveUpPwd(Map<String,Object> map)throws Exception;
	
	//通过用户名查询角色
	public Set<String> findRoles(String name)throws Exception;
	
	//通过用户名查询按钮权限
	public Set<String> findPermissions(String name)throws Exception;
	
	//通过用户名查询用户信息
	public User findByUsername(String name)throws Exception;
	
	//通过用户名修改用户锁定状态
	public int updateStateByUserName(String state,String name)throws Exception;

}
