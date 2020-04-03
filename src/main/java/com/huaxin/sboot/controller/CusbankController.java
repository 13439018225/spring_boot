package com.huaxin.sboot.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.huaxin.sboot.bean.Customer;
import com.huaxin.sboot.service.ICusbankServcie;
import com.huaxin.sboot.service.ICustomerServcie;
import com.huaxin.sboot.util.DateUtil;

@Controller
@RequestMapping("/cusbank")
public class CusbankController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//注入service接口
	@Autowired
	private ICusbankServcie cusbankService;
	
	
	//跳转到客户账号管理页面
	@RequestMapping("/cusbankManage")
	public String touserlist(){
		return "cusbanklist";
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
			json = cusbankService.query(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public String save(Customer customer){
		int k=0;
		try {
			k = cusbankService.save(customer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(k);
	}
	//删除
	@RequestMapping("/del")
	@ResponseBody
	public String del(String ids){
		int k=0;
		try {
			k=cusbankService.del(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(k);
	}
	
	//通过ID查询出被修改的数据
	@RequestMapping("/getObjById")
	@ResponseBody
	public String getObjById(String id){
		Customer customer=null;
		try {
			customer=cusbankService.getObjById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String json=JSONObject.toJSONString(customer);
		return json;
	}
	
	@RequestMapping("/getCustomers")
	@ResponseBody
	public String getCustomers(){
		String json="";
		try {
			json=cusbankService.getCustomers();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	//激活账号
	@RequestMapping("/toActive")
	@ResponseBody
	public String toActive(String id,String cid){
		int k=0;
		try {
			k=cusbankService.toActive(id,cid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(k);
	}
	
	//取地址
	@RequestMapping("/getAddress")
	@ResponseBody
	public String getAddress(String pid){
		String json="";
		try {
			json=cusbankService.getAddress(pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
