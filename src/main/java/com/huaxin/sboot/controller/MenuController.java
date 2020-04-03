package com.huaxin.sboot.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaxin.sboot.bean.User;
import com.huaxin.sboot.service.IMenuServcie;
@Controller
@RequestMapping("/menu")
public class MenuController {
	
	@Autowired
	private IMenuServcie menuServcie;
	
	//加载tree菜单
	@RequestMapping("/getTreeAuthMenu")
	@ResponseBody
	public String getTreeAuthMenu(HttpSession session){
		User user=(User) session.getAttribute("userinfo");
		String menus="";
		try {
			 menus=menuServcie.getTreeAuthMenu(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menus;
	}

}
