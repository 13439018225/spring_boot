package com.huaxin.sboot.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.huaxin.sboot.bean.Role;
import com.huaxin.sboot.bean.Tree;
import com.huaxin.sboot.dao.IRoleMapper;
import com.huaxin.sboot.service.IRoleServcie;
@Service
public class RoleServiceImpl implements IRoleServcie {

	@Autowired
	private IRoleMapper roleMapper;
	
	@Override
	public int save(Role role) throws Exception {
		int k=0;
		if(StringUtils.isNotEmpty(role.getId())){
			//修改
			 k=roleMapper.update(role);
		}else{
			if(StringUtils.isNotEmpty(role.getName())){
				 k=roleMapper.add(role);
			}
		}
		return k;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={Exception.class,RuntimeException.class},isolation=Isolation.READ_COMMITTED)
	public int del(String id) throws Exception {
		roleMapper.delRoleMenu(id);
		return roleMapper.del(id);
	}

	@Override
	public Role getObjById(String id) throws Exception {
		return null;
	}

	@Override
	public String query(Map<String, Object> map) throws Exception {
		//当前第几页
		int pageNum=Integer.parseInt((String)map.get("pageNum"));
		//每页记录数
		int pageSize=Integer.parseInt((String)map.get("pageSize"));
		//分页
		Page<Object> page = PageHelper.startPage(pageNum, pageSize);
		List<Role> list = roleMapper.query(map);
		Map<String,Object> rmap=new HashMap<String,Object>();
		rmap.put("total", page.getTotal());
		rmap.put("rows", list);
		String json=JSONObject.toJSONString(rmap);
		return json;
	}

	@Override
	public String getRoles(String userid) throws Exception {
		//查询所有角色，供分配角色时使用
		List<Role> list=roleMapper.getRoles();
		//通过用户ID，查询该用户已分配的角色信息
		Role role=roleMapper.getRoleByUserId(userid);
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list", list);
		map.put("role", role);
		String json=JSONObject.toJSONString(map);
		return json;
	}

	@Override
	public List<Tree> getAuthMenu(String roleid, String pid) throws Exception {
		//pid=0 ,查询的是一级菜单
		List<Tree> list=roleMapper.getAuthMenu(roleid, pid);
		if(list!=null && list.size()>0){
			for(Tree tree:list){
				//根据父节点的id值,查询子节点
				List<Tree> children= getAuthMenu(roleid,tree.getId());
				if(children!=null && children.size()>0){
					for(Tree t:children){
						if("true".equals(t.getAttributes())){
							t.setChecked(true);
						}else{
							t.setChecked(false);
						}
					}
					tree.setChildren(children);
				}
			}
		}
		return list;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={Exception.class,RuntimeException.class},isolation=Isolation.READ_COMMITTED)
	public int saveAuthMenu(String roleid, String mids) throws Exception {
		//通过角色ID，删除原来已经分配好的菜单信息
		roleMapper.delMenuByRoleId(roleid);
		String[] ids=mids.split(",");
		int k=0;
		Map<String,Object> map=new HashMap<String,Object>();
		for(String mid:ids){
			//重新给角色分配菜单信息
			map.put("roleid", roleid);
			map.put("mid", mid);
			roleMapper.saveAuthMenu(map);
			k++;
		}
		return k;
	}

}
