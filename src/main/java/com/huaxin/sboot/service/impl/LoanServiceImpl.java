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
import com.huaxin.sboot.bean.LoanApply;
import com.huaxin.sboot.dao.ICodeMapper;
import com.huaxin.sboot.dao.ILoanMapper;
import com.huaxin.sboot.service.ILoanServcie;
@Service
public class LoanServiceImpl implements ILoanServcie {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//注入dao接口
	@Autowired
	private ILoanMapper loanMapper;
	
	//注入获取单据编号的接口
	@Autowired
	private ICodeMapper codeMapper;
	
	@Override
	public int save(LoanApply loanapply) throws Exception {
		
		if(StringUtils.isNotEmpty(loanapply.getId())){
			//id 有值
			return loanMapper.update(loanapply);
		}else{
			return loanMapper.add(loanapply);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={Exception.class,RuntimeException.class},isolation=Isolation.READ_COMMITTED)
	public int del(String ids) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("ids", ids);
		return loanMapper.del(map);
	}

	@Override
	public LoanApply getObjById(String id) throws Exception {
		return loanMapper.getObjById(id);
	}

	@Override
	public String query(Map<String, Object> map) throws Exception {
		//当前第几页
		int pageNum=Integer.parseInt((String)map.get("pageNum"));
		//每页记录数
		int pageSize=Integer.parseInt((String)map.get("pageSize"));
		//分页
		Page<Object> page = PageHelper.startPage(pageNum, pageSize);
		
		List<LoanApply> list = loanMapper.query(map);
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
	public int toSub(String ids) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("ids", ids);
		return loanMapper.toSub(map);
	}
	
}
