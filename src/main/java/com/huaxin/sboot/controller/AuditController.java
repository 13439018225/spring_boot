package com.huaxin.sboot.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.huaxin.sboot.bean.LoanApply;
import com.huaxin.sboot.bean.User;
import com.huaxin.sboot.service.IAuditServcie;
import com.huaxin.sboot.service.ICustomerServcie;
import com.huaxin.sboot.service.ILoanServcie;
import com.huaxin.sboot.util.DateUtil;

@Controller
@RequestMapping("/audit")
public class AuditController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//注入service接口
	@Autowired
	private IAuditServcie auditServcie;
	
	//跳转到出借申审核页面
	@RequestMapping("/auditManage")
	public String auditManage(){
		return "auditlist";
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
			json = auditServcie.query(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	
	//通过ID查询出被修改的数据
	@RequestMapping("/getObjById")
	@ResponseBody
	public String getObjById(String id){
		String res=null;
		try {
			res=auditServcie.getObjById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//记日志
		logger.info("res===>"+res);
		return res;
	}
	
	//审核
	@RequestMapping("/toAudit")
	@ResponseBody
	public String toAudit(String id,String status,HttpSession session){
		User user=(User)session.getAttribute("userinfo");
		String username="admin";
		if(user!=null){
			username=user.getName();
		}
		int k=0;
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("auditor", username);
		map.put("status", status);
		map.put("auditordate", DateUtil.getCurrentDate());
		try {
			k=auditServcie.toAudit(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(k);
	}
}
