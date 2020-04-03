package com.huaxin.sboot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.huaxin.sboot.bean.User;

@Controller
@RequestMapping("/wx")
public class WxController {

	@RequestMapping("/resWx")
	@ResponseBody
	public String resWx(String str){
		List<User> list=new ArrayList<User>();
		for(int i=0;i<3;i++){
			User u=new User();
			u.setName(str+(i+1));
			u.setSex("男");
			list.add(u);
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list", list);
		map.put("totle", list.size());
		String json=JSONObject.toJSONString(map);
		return json;
	}
}
