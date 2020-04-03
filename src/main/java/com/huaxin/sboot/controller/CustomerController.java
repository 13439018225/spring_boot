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
import com.huaxin.sboot.service.ICustomerServcie;
import com.huaxin.sboot.util.DateUtil;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//注入service接口
	@Autowired
	private ICustomerServcie customerService;
	
	
	//跳转到客户管理页面
	@RequestMapping("/customerManage")
	public String touserlist(){
		return "cuslist";
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
			json = customerService.query(map);
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
			k = customerService.save(customer);
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
			k=customerService.del(ids);
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
			customer=customerService.getObjById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String json=JSONObject.toJSONString(customer);
		return json;
	}
	
	//获取客户编号
	@RequestMapping("/getCode")
	@ResponseBody
	public String getCode(){
		String code="";
		try {
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("reqdate", DateUtil.getCurrentDate());
			map.put("type", "0");
			int c=customerService.getCode(map);
			//获取单据编号
			code=DateUtil.getReqCode(String.valueOf(c), "C");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}
}
