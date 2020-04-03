package com.huaxin.sboot.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.huaxin.sboot.bean.Tree;
import com.huaxin.sboot.bean.User;
import com.huaxin.sboot.dao.IMenuMapper;
import com.huaxin.sboot.service.IMenuServcie;
import com.huaxin.sboot.util.RedisUtil;

@Service
public class MenuServcieImpl implements IMenuServcie {

	
	@Autowired
	private IMenuMapper menuMapper;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Override
	public String getTreeAuthMenu(User user) throws Exception {
		
		String json="";
		if(user==null || user.getName().equals("admin")){
			//查看全部菜单
			json=getAllMenu();
		}else{
			String userid=user.getId();
			String username=user.getName();
			try {
				//从redis 查询当前用户对应的菜单信息
				json=(String)redisUtil.get(username);
				if(StringUtils.isEmpty(json)){
					//如果从redis取不到数据,查询关系型数据库
					//查询父节点
					List<Tree> plist = menuMapper.getTreeAuthMenuParent(userid);
					json=getMenu(plist);
					//将菜单信息放到redis中
					redisUtil.set(username, json, 7*24*60*60);
				}
			} catch (Exception e) {
				e.printStackTrace();
				List<Tree> plist = menuMapper.getTreeAuthMenuParent(userid);
				json=getMenu(plist);
			}
		}
		return json;
	}
	
    //admin 用户查询全部菜单
	private String getAllMenu(){
		String menus="";
		try {
			//1、从redis通过key取菜单信息
			menus=(String)redisUtil.get("admin");
			if(menus==null){
				//从关系型数据库中查询
				List<Tree> plist = menuMapper.getAllMenu("0");
				//查询所有parentid 为0的菜单
				menus=getMenu(plist);
				//将菜单信息放到redis中
				redisUtil.set("admin", menus, 7*24*60*60);
			}
		} catch (Exception e) {
			e.printStackTrace();
			List<Tree> plist;
			try {
				plist = menuMapper.getAllMenu("0");
				menus=getMenu(plist);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return menus;
	}
	
	private String getMenu(List<Tree> plist){
		String json="";
		try {
			for(Tree tree:plist){
				String id=tree.getId();
				List<Tree> children = menuMapper.getAllMenu(id);
				tree.setChildren(children);
			}
			json=JSONObject.toJSONString(plist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
