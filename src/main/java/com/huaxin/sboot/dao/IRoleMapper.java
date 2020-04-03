package com.huaxin.sboot.dao;

import java.util.List;
import java.util.Map;

import com.huaxin.sboot.bean.Role;
import com.huaxin.sboot.bean.Tree;

public interface IRoleMapper {
	
    //增加
	public int add(Role role)throws Exception;
	
	//修改
    public int update(Role role)throws Exception;
	
	//删除 
	public int del(String id)throws Exception;
	
	//通过角色ID,删除角色菜单关系表数据 
	public int delRoleMenu(String id)throws Exception;
	
	//通过ID查询对象
	public Role getObjById(String id)throws Exception;
	
	//查询
	public List<Role> query(Map<String,Object> map);
	
	//分配角色，查询角色信息
	public List<Role> getRoles() throws Exception;
	
	//通过用户ID，查询已分配的角色
	public Role getRoleByUserId(String userid)throws Exception;
	
	//通过角色 查询分配好的菜单
	public List<Tree> getAuthMenu(String roleid, String pid) throws Exception;
	
	//保存角色分配好的菜单信息
    public int saveAuthMenu(Map<String,Object> map)throws Exception;
    
    //通过角色ID，删除分配好的菜单信息
    public int delMenuByRoleId(String roleid)throws Exception;
    

}
