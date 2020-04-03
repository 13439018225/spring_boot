package com.huaxin.sboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaxin.sboot.service.IMainServcie;

@Controller
@RequestMapping("/main")
public class MainController {
	
	@Autowired
	private IMainServcie mainServcie;
	
	@RequestMapping("/tomain")
	public String tomain(){
		return "main";
	}
	
	//柱形图
	@RequestMapping("/chartcolumn")
	@ResponseBody
	public String chartcolumn(){
		String json="";
		try {
		    json=mainServcie.getChartcolumn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	//饼图
	@RequestMapping("/chartpie")
	@ResponseBody
	public String chartpie(){
		String json="";
		try {
		    json=mainServcie.getChartpie();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	//折线图
	@RequestMapping("/chartline")
	@ResponseBody
	public String chartline(){
		String json="";
		try {
		    json=mainServcie.getChartline();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
