package com.huaxin.sboot.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaxin.sboot.bean.UserInfo;
import com.huaxin.sboot.dao.IUserInfoMapper;
import com.huaxin.sboot.service.IUserInfoServcie;
@Service
public class UserInfoServiceImpl implements IUserInfoServcie {

	//注入dao接口
	@Autowired
	private IUserInfoMapper userInfoMapper;
	
	@Override
	public int save(UserInfo userinfo) {
		int n=0;
		if(userinfo.getId()!=null && !"".equals(userinfo.getId())){
			//修改
			n=userInfoMapper.update(userinfo);
		}else{
			//增加
			n=userInfoMapper.add(userinfo);
		}
		return n;
	}

	@Override
	public int del(String id) {
		return userInfoMapper.del(id);
	}

	@Override
	public UserInfo getObjById(String id) {
		UserInfo userinfo = userInfoMapper.getObjById(id);
		return userinfo;
	}

	@Override
	public List<UserInfo> query(Map<String, Object> map) {
		List<UserInfo> list = userInfoMapper.query(map);
		return list;
	}

}
