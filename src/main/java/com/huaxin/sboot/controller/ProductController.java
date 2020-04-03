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
import com.huaxin.sboot.bean.Product;
import com.huaxin.sboot.service.IProductServcie;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
	private IProductServcie productServcie;
    
	//跳转到产品管理页面
	@RequestMapping("/productManage")
	public String productManage(){
		return "productlist";
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
			json = productServcie.query(map);
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
			List<Product> list=JSON.parseArray(rows, Product.class);
			
			for(Product r:list){
				int k=productServcie.save(r);
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
			k=productServcie.del(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(k);
	}
	
	//查询全部产品
	@RequestMapping("/getProducts")
	@ResponseBody
	public String getProducts(){
		String json="";
		try {
			json=productServcie.getProducts();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
