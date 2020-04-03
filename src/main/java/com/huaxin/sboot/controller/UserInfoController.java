package com.huaxin.sboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.huaxin.sboot.bean.UserInfo;
import com.huaxin.sboot.service.IUserInfoServcie;

@Controller
public class UserInfoController {
	
	@Autowired
	private IUserInfoServcie userInfoServcie;
	
	//查询并且跳转页面
	@RequestMapping("/query")
	public String query(Model model,String qname){
		//将数据放到模型中
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("name", qname);
		List<UserInfo> list=userInfoServcie.query(map);
		
		model.addAttribute("ulist", list);
		model.addAttribute("map", map);
		return "userinfolist";
	}
	//删除
	@RequestMapping("/del")
    public String del(String id){
		int k=userInfoServcie.del(id);
		if(k==0){
			return "userinfolist";
		}
		//重新查询
		return "redirect:/query";
	}
	
	//跳转到增加表单
	@RequestMapping("/toAdd")
    public String toAdd(){
		return "userAdd";
	}
	//保存
	@RequestMapping("/save")
	public String save(UserInfo userinfo){
		int k=userInfoServcie.save(userinfo);
		if(k>0){
			return "redirect:/query";
		}else{
			if(userinfo.getId()!=null && !"".equals(userinfo.getId())){
				return "userEdit";
			}
			return "userAdd";
		}
	}
	
	
	
	//模态框保存
	@RequestMapping("/modelsave")
	public String modelsave(UserInfo userinfo){
		int k=userInfoServcie.save(userinfo);
		if(k>0){
			return "redirect:/query";
		}else{
			return "userinfolist";
		}
	}
	
	//通过id查询待修改的数据
	@RequestMapping("/getObjByid")
	public String getObjByid(String id,ModelMap modelmap){
		UserInfo userinfo=userInfoServcie.getObjById(id);
		//放到模型
		modelmap.addAttribute("userinfo", userinfo);
		return "userEdit";
	}
	//模态框通过ID查询对象
	@RequestMapping("/modelGetObjById")
	@ResponseBody
	public String modelGetObjById(String id){
		UserInfo userinfo=userInfoServcie.getObjById(id);
		String json=JSONObject.toJSONString(userinfo);
		return json;
	}
	
	//返回信息到浏览器
	@RequestMapping(value={"/show","/getMess"})
	@ResponseBody
	public String getMess(){
		return "hello";
	}
}
