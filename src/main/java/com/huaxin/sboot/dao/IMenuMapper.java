package com.huaxin.sboot.dao;

import java.util.List;

import com.huaxin.sboot.bean.Tree;


public interface IMenuMapper {
	//查询所有授权父节点菜单信息
	public List<Tree> getTreeAuthMenuParent(String userid) throws Exception;
	
	//通过父节点ID，查询所有授权子节点菜单信息
	public List<Tree> getTreeAuthMenuByPid(String userid,String pid) throws Exception;
	
	//如果是admin则查看全部菜单
	public List<Tree> getAllMenu(String pid) throws Exception;
    

}
