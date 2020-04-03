package com.huaxin.sboot.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.huaxin.sboot.bean.Address;
import com.huaxin.sboot.bean.Customer;
import com.huaxin.sboot.dao.ICodeMapper;
import com.huaxin.sboot.dao.ICusbankMapper;
import com.huaxin.sboot.dao.ICustomerMapper;
import com.huaxin.sboot.service.ICusbankServcie;
import com.huaxin.sboot.service.ICustomerServcie;
@Service
public class CusbankServiceImpl implements ICusbankServcie {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//注入dao接口
	@Autowired
	private ICusbankMapper cusbankMapper;
	
	//注入获取单据编号的接口
	@Autowired
	private ICodeMapper codeMapper;
	
	@Override
	public int save(Customer customer) throws Exception {
		
		if(StringUtils.isNotEmpty(customer.getId())){
			//id 有值
			return cusbankMapper.update(customer);
		}else{
			return cusbankMapper.add(customer);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={Exception.class,RuntimeException.class},isolation=Isolation.READ_COMMITTED)
	public int del(String ids) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("ids", ids);
		return cusbankMapper.del(map);
	}

	@Override
	public Customer getObjById(String id) throws Exception {
		return cusbankMapper.getObjById(id);
	}

	@Override
	public String query(Map<String, Object> map) throws Exception {
		//当前第几页
		int pageNum=Integer.parseInt((String)map.get("pageNum"));
		//每页记录数
		int pageSize=Integer.parseInt((String)map.get("pageSize"));
		//分页
		Page<Object> page = PageHelper.startPage(pageNum, pageSize);
		
		List<Customer> list = cusbankMapper.query(map);
		//记录日志 
		logger.info("总记录数==="+page.getTotal()+",每页记录数==="+page.getPageSize()+",总页数==="+page.getPages()+","
						+ "当前第几页=="+page.getPageNum());
		
		Map<String,Object> rmap=new HashMap<String,Object>();
		rmap.put("total", page.getTotal());
		rmap.put("rows", list);
		String json=JSONObject.toJSONString(rmap);
		return json;
	}

	@Override
	public String getCustomers() throws Exception {
		List<Customer> list=cusbankMapper.getCustomers();
		String json=JSONObject.toJSONString(list);
		return json;
	}

	@Override
	public int toActive(String id, String cid) throws Exception {
		return cusbankMapper.toActive(id,cid);
	}

	@Override
	public String getAddress(String pid) throws Exception {
		List<Address> list = cusbankMapper.getAddress(pid);
		String json=JSONObject.toJSONString(list);
		return json;
	}
	
}
