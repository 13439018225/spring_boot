package com.huaxin.sboot.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.huaxin.sboot.bean.User;
import com.huaxin.sboot.dao.IUserMapper;
import com.huaxin.sboot.service.IUserServcie;
import com.huaxin.sboot.util.ExcelUtil;
import com.huaxin.sboot.util.MD5;
@Service
public class UserServiceImpl implements IUserServcie {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//注入dao接口
	@Autowired
	private IUserMapper userMapper;
	
	@Override
	public int save(User user) throws Exception {
		//密码 明文加密
		String pwd=MD5.encryptPassword(user.getName(), user.getPassword(), null);
		user.setPassword(pwd);
		
		if(StringUtils.isNotEmpty(user.getId())){
			//id 有值
			return userMapper.update(user);
		}else{
			return userMapper.add(user);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={Exception.class,RuntimeException.class},isolation=Isolation.READ_COMMITTED)
	public int del(String ids) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("ids", ids);
		userMapper.delUserRoleRel(map);
		return userMapper.del(map);
	}

	@Override
	public User getObjById(String id) throws Exception {
		return userMapper.getObjById(id);
	}

	@Override
	public String query(Map<String, Object> map) throws Exception {
		//当前第几页
		int pageNum=Integer.parseInt((String)map.get("pageNum"));
		//每页记录数
		int pageSize=Integer.parseInt((String)map.get("pageSize"));
		//分页
		Page<Object> page = PageHelper.startPage(pageNum, pageSize);
		
		List<User> list = userMapper.query(map);
		//记录日志 
		logger.info("总记录数==="+page.getTotal()+",每页记录数==="+page.getPageSize()+",总页数==="+page.getPages()+","
						+ "当前第几页=="+page.getPageNum());
		
		Map<String,Object> rmap=new HashMap<String,Object>();
		rmap.put("total", page.getTotal());
		rmap.put("rows", list);
		String json=JSONObject.toJSONString(rmap);
		return json;
	}
	
	public  HSSFWorkbook exportExcel(Map<String,Object> map) {
		 //定义表头
		 String[] headers={"用户名","邮件","性别","QQ","微信","注册日期"};
		 //获取数据集合
		 List<User> list=userMapper.query(map);
		 
		 String [][] values=new String[list.size()][headers.length];
		 for (int i = 0; i < list.size(); i++) {
            User u = list.get(i);
            values[i][0] = u.getName();
            values[i][1] = u.getEmail();
            values[i][2] = u.getSex();
            values[i][3] = u.getQq();
            values[i][4] = u.getWeixin();
            values[i][5] = u.getRegtime();
	     }
		 HSSFWorkbook workbook=ExcelUtil.getHSSFWorkbook("用户管理", headers, values, null);
		 return workbook;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={Exception.class,RuntimeException.class},isolation=Isolation.READ_COMMITTED)
	public int saveUserRole(String userid, String roleid) throws Exception{
		//将用户对应的角色删除
		userMapper.delUserRole(userid);
		//int k=1/0;
		return userMapper.saveUserRole(userid,roleid);
	}
    
	@Override
	public int saveUpPwd(Map<String, Object> map) throws Exception {
		return userMapper.saveUpPwd(map);
	}

	@Override
	public Set<String> findRoles(String name) throws Exception {
		Set<String> set=userMapper.findRoles(name);
		return set;
	}

	@Override
	public Set<String> findPermissions(String name) throws Exception {
		Set<String> set=userMapper.findPermissions(name);
		return set;
	}

	@Override
	public User findByUsername(String name) throws Exception {
		User user=userMapper.findByUsername(name);
		return user;
	}

	@Override
	public int updateStateByUserName(String state,String name) throws Exception {
		int k=userMapper.updateStateByUserName(state,name);
		return k;
	}

}
