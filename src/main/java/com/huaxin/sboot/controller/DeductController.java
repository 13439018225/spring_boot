package com.huaxin.sboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaxin.sboot.bean.Deduct;
import com.huaxin.sboot.service.IDeductServcie;

@Controller
@RequestMapping("/deduct")
public class DeductController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//注入service接口
	@Autowired
	private IDeductServcie deductServcie;
	
	//跳转到出借申审核页面
	@RequestMapping("/deductManage")
	public String auditManage(){
		return "deductlist";
	}

	@RequestMapping("/query")
	@ResponseBody
	public String query(HttpServletRequest request){
		//查询条件
		String loancode=request.getParameter("qname");
		//当前第几页
		String page=request.getParameter("page");
		//每页记录数
		String rows=request.getParameter("rows");
		
		//查询条件  与 分页参数同一放到map中
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("loancode", loancode);
        map.put("pageNum", page);
        map.put("pageSize", rows);
		String json="";
		try {
			json = deductServcie.query(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	//划扣方法
	@RequestMapping("/toDeduct")
	@ResponseBody
	public String toDeduct(String ids){
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("ids", ids);
		int suceess=0;
		int fail=0;
		try {
			//通过id字符串，获取待划扣的数据
			List<Deduct> list=deductServcie.getDeductListByIds(map);
			//单笔实时划扣
			for(Deduct deduct:list){
				//调用划扣方法
				String mess=deductServcie.toDeduct(deduct);
				if("OK".equalsIgnoreCase(mess)){
					suceess++;
				}else{
					fail++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String res="成功"+suceess+"条,失败"+fail;
		return res;
	}
	
	//查看划扣记录
	@RequestMapping("/getDeductRecord")
	@ResponseBody
	public String getDeductRecord(String id){
		String json="";
		try {
			json=deductServcie.getDeductRecord(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	//保存预约时间
	@RequestMapping("/saveAppiontDate")
	@ResponseBody
	public String saveAppiontDate(String ids,String appionDateTime){
		int k=0;
		try {
			k=deductServcie.saveAppiontDate(ids,appionDateTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return k+"";
	}
}
