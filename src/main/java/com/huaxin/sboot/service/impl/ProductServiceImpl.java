package com.huaxin.sboot.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.huaxin.sboot.bean.Product;
import com.huaxin.sboot.bean.Role;
import com.huaxin.sboot.dao.IProductMapper;
import com.huaxin.sboot.service.IProductServcie;

@Service
public class ProductServiceImpl  implements IProductServcie{

	@Autowired
	private IProductMapper productMapper;
	
	@Override
	public int save(Product product) throws Exception {
		int k=0;
		if(StringUtils.isNotEmpty(product.getId())){
			//修改
			k=productMapper.update(product);
		}else{
			//增加
			k=productMapper.add(product);
		}
		return k;
	}

	@Override
	public int del(String id) throws Exception {
		return productMapper.del(id);
	}

	@Override
	public String query(Map<String, Object> map) throws Exception {
		//当前第几页
		int pageNum=Integer.parseInt((String)map.get("pageNum"));
		//每页记录数
		int pageSize=Integer.parseInt((String)map.get("pageSize"));
		//分页
		Page<Object> page = PageHelper.startPage(pageNum, pageSize);
		
		List<Product> list = productMapper.query(map);
		Map<String,Object> rmap=new HashMap<String,Object>();
		rmap.put("total", page.getTotal());
		rmap.put("rows", list);
		String json=JSONObject.toJSONString(rmap);
		return json;
	}

	@Override
	public String getProducts() throws Exception {
		List<Product> products = productMapper.getProducts();
		String json=JSONObject.toJSONString(products);
		return json;
	}

}
