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
import com.huaxin.sboot.bean.LoanApply;
import com.huaxin.sboot.service.ICustomerServcie;
import com.huaxin.sboot.service.ILoanServcie;
import com.huaxin.sboot.util.DateUtil;

@Controller
@RequestMapping("/apply")
public class LoanController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//注入service接口
	@Autowired
	private ILoanServcie loanServcie;
	
	@Autowired
	private ICustomerServcie customerService;
	
	
	//跳转到出借申请管理页面
	@RequestMapping("/applyManage")
	public String applyManage(){
		return "loanlist";
	}

	@RequestMapping("/query")
	@ResponseBody
	public String query(HttpServletRequest request){
		//查询条件
		String loancode=request.getParameter("qname");
		//当前第几页
		String page=request.getParameter("page");
		//每页记录数
		String rows=request.getParameter("rows");
		
		//查询条件  与 分页参数同一放到map中
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("loancode", loancode);
        map.put("pageNum", page);
        map.put("pageSize", rows);
		String json="";
		try {
			json = loanServcie.query(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public String save(LoanApply loanapply){
		int k=0;
		try {
			k = loanServcie.save(loanapply);
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
			k=loanServcie.del(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(k);
	}
	
	//通过ID查询出被修改的数据
	@RequestMapping("/getObjById")
	@ResponseBody
	public String getObjById(String id){
		LoanApply loanapply=null;
		try {
			loanapply=loanServcie.getObjById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String json=JSONObject.toJSONString(loanapply);
		return json;
	}
	
	//提交
	@RequestMapping("/toSub")
	@ResponseBody
	public String toSub(String ids){
		int k=0;
		try {
			k=loanServcie.toSub(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(k);
	}
	
	//获取客户编号
	@RequestMapping("/getCode")
	@ResponseBody
	public String getCode(){
		String code="";
		try {
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("reqdate", DateUtil.getCurrentDate());
			map.put("type", "1");
			int c=customerService.getCode(map);
			//获取单据编号
			code=DateUtil.getReqCode(String.valueOf(c), "L");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}
}
