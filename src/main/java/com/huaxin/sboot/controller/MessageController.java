package com.huaxin.sboot.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaxin.sboot.bean.Message;
import com.huaxin.sboot.service.IMessServcie;

@Controller
@RequestMapping("/mess")
public class MessageController {
    
	@Autowired
	private IMessServcie messServcie;
	
	@RequestMapping("/messManage")
	public String messManage(){
		return "messlist";
	}
	
	@RequestMapping("/query")
	@ResponseBody
	public String query(HttpServletRequest request) {
		// 查询条件
		String loancode = request.getParameter("loancode");
		// 当前第几页
		String page = request.getParameter("page");
		// 每页记录数
		String rows = request.getParameter("rows");
		
		// 查询条件 与 分页参数同一放到map中
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loancode", loancode);
		map.put("pageNum", page);
		map.put("pageSize", rows);
		String json = "";
		try {
			json = messServcie.mongoQuery(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	@RequestMapping("/save")
	@ResponseBody
	public String save(Message message){
		int k=0;
		try {
			k=messServcie.save(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return k+"";
	}
	
	//删除
	@RequestMapping("/del")
	@ResponseBody
	public String del(String ids){
		int k=0;
		try {
			k=messServcie.del(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return k+"";
	}
	
	//getObjById
	//通过ID查询要修改的记录
	@RequestMapping("/getObjById")
	@ResponseBody
	public String getObjById(String id){
		String json="";
		try {
			json=messServcie.getObjById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	//发送邮件
	@RequestMapping("/sendEmail")
	@ResponseBody
	public String sendEmail(String ids){
		String str="";
		try {
			str = messServcie.sendEmail(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	//发送短信
	@RequestMapping("/sendEms")
	@ResponseBody
	public String sendEms(String ids){
		String str="";
		try {
			str = messServcie.sendEms(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
}
