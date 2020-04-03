package com.huaxin.sboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huaxin.sboot.bean.Role;
import com.huaxin.sboot.bean.Tree;
import com.huaxin.sboot.service.IRoleServcie;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
	private IRoleServcie roleService;
    
	//跳转到用户管理页面
	@RequestMapping("/roleManage")
	public String torolelist(){
		return "rolelist";
	}
	
	@RequestMapping("/query")
	@ResponseBody
	public String query(HttpServletRequest request){
		//查询条件
		String qname=request.getParameter("qname");
		//当前第几页
		String page=request.getParameter("page");
		//每页记录数
		String rows=request.getParameter("rows");
		
		//查询条件  与 分页参数同一放到map中
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("name", qname);
        map.put("pageNum", page);
        map.put("pageSize", rows);
		String json="";
		try {
			json = roleService.query(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public String save(String rows){
		//[{"id":"","name":"aa"},{"id":"","name":"bbb"},{"id":"10000","name":"理财经理1"},{"id":"10001","name":"业务主管2"}]
		int n=0;
		try {
			List<Role> list=JSON.parseArray(rows, Role.class);
			for(Role r:list){
				int k=roleService.save(r);
				if(k>0){
					n++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(n);
	}
	
	//删除
	@RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		int k=0;
		try {
			k=roleService.del(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(k);
	}
	
	//角色分配菜单
	@RequestMapping("/getauthmenu")
	@ResponseBody
	public String getauthmenu(String roleid){
		String json="";
		try {
			List<Tree> menulist = roleService.getAuthMenu(roleid,"0");
			json=JSONObject.toJSONString(menulist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	//保存角色对应的菜单
	@RequestMapping("/saveAuthMenu")
	@ResponseBody
	public String saveAuthMenu(String roleid,String mids){
		int k=0;
		try {
			k=roleService.saveAuthMenu(roleid,mids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(k);
	}
	
}
