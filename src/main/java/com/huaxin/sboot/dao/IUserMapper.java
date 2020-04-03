package com.huaxin.sboot.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.huaxin.sboot.bean.User;

public interface IUserMapper {
	
    //增加
	public int add(User user)throws Exception;
	
	//修改
    public int update(User user)throws Exception;
	
	//删除 
	public int del(Map<String,Object> map)throws Exception;
	
	//通过ID查询对象
	public User getObjById(String id)throws Exception;
	
	//查询
	public List<User> query(Map<String,Object> map);
	
	//保存用户与角色
	public int saveUserRole(String userid, String roleid) throws Exception;
	
	//通过用户ID，删除用户对应的角色
	public int delUserRole(String userid) throws Exception;
	
	//通过用户ID,删除用户角色关联表信息
	public int delUserRoleRel(Map<String,Object> map) throws Exception;
	
	//修改密码
	public int saveUpPwd(Map<String, Object> map) throws Exception;
	
	//通过用户名查询角色信息
	public Set<String> findRoles(String name)throws Exception;
	
	//通过用户名查询按钮权限信息
	public Set<String> findPermissions(String name)throws Exception;
	
	//通过用户名查询用户信息
	public User findByUsername(String name)throws Exception;
	
	//通过用户名修改用户锁定状态
	public int updateStateByUserName(String state,String name) throws Exception;
	
	//定时任务 修改用户的锁定状态
	public int updateState(Map<String,Object> map) throws Exception;
}
