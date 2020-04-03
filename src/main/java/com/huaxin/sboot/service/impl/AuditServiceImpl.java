package com.huaxin.sboot.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.huaxin.sboot.bean.Customer;
import com.huaxin.sboot.bean.LoanApply;
import com.huaxin.sboot.bean.Product;
import com.huaxin.sboot.dao.IAuditMapper;
import com.huaxin.sboot.dao.ICustomerMapper;
import com.huaxin.sboot.service.IAuditServcie;
@Service
public class AuditServiceImpl implements IAuditServcie {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IAuditMapper auditMapper;
	
	@Autowired
	private ICustomerMapper customerMapper;

	@Override
	public String getObjById(String id) throws Exception {
		
		Map<String,Object> map=new HashMap<String,Object>();
		//1、出借单据
		LoanApply loan=auditMapper.getObjById(id);
		map.put("loan", loan);
		//2、产品信息
		Product product=auditMapper.getProductById(loan.getPid());
		map.put("product",product);
		//3、客户信息
		Customer custom = customerMapper.getObjById(loan.getCid());
		map.put("custom", custom);
		//4、客户账号信息
		Customer cusbank = auditMapper.getCusBanktById(custom.getCid());
		map.put("cusbank", cusbank);
		String json=JSONObject.toJSONString(map);		
		return json;
	}

	@Override
	public String query(Map<String, Object> map) throws Exception {
		//当前第几页
		int pageNum=Integer.parseInt((String)map.get("pageNum"));
		//每页记录数
		int pageSize=Integer.parseInt((String)map.get("pageSize"));
		//分页
		Page<Object> page = PageHelper.startPage(pageNum, pageSize);
		
		List<LoanApply> list = auditMapper.query(map);
		//记录日志 
		logger.info("总记录数==="+page.getTotal()+",每页记录数==="+page.getPageSize()+",总页数==="+page.getPages()+","
						+ "当前第几页=="+page.getPageNum());
		
		Map<String,Object> rmap=new HashMap<String,Object>();
		rmap.put("total", page.getTotal());
		rmap.put("rows", list);
		String json=JSONObject.toJSONString(rmap);
		return json;
	}

	//审核
	@Override
	public int toAudit(Map<String,Object> map) throws Exception {
		return auditMapper.toAudit(map);
	}
	
}
