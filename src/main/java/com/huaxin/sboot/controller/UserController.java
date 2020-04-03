package com.huaxin.sboot.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.alibaba.fastjson.JSONObject;
import com.huaxin.sboot.bean.Role;
import com.huaxin.sboot.bean.User;
import com.huaxin.sboot.service.IRoleServcie;
import com.huaxin.sboot.service.IUserServcie;
import com.huaxin.sboot.util.MD5;

@Controller
@RequestMapping("/user")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//注入service接口
	@Autowired
	private IUserServcie userService;
	
	@Autowired
	private IRoleServcie roleService;
	
	//跳转到用户管理页面
	@RequestMapping("/userManage")
	public String touserlist(){
		return "userlist";
	}

	@RequestMapping("/query")
	@ResponseBody
	public String query(HttpServletRequest request){
		//查询条件
		String qname=request.getParameter("qname");
		//当前第几页
		String page=request.getParameter("page");
		//每页记录数
		String rows=request.getParameter("rows");
		/*
			{"total":10,"rows":[
			    {"email":"123@qq.com","name":"用户0","password":"0"},
			    {"email":"123@qq.com","name":"用户2","password":"2"}
			]}
		 * 
		 */
		//查询条件  与 分页参数同一放到map中
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("name", qname);
        map.put("pageNum", page);
        map.put("pageSize", rows);
		String json="";
		try {
			json = userService.query(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public String save(User user,HttpServletRequest req){
		MultipartRequest mreq=(MultipartRequest)req;
		MultipartFile file = mreq.getFile("userfile");
		int k=0;
		try {
			if(file!=null){
				String filename=file.getOriginalFilename();
				String filepath="E:\\upload";
				//上传
				if(!new File(filepath).exists()){
					new File(filepath).mkdir();
				}
				File f=new File(filepath,filename);
				file.transferTo(f);
				//将表单基本信息保存到数据库
				user.setFilepath(filepath+"\\"+filename);
			}
			k=userService.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(k);
	}
	
	//删除
	@RequestMapping("/del")
	@ResponseBody
	@RequiresPermissions("user:del")//用户查询权限
	public String del(String ids){
		int k=0;
		try {
			k=userService.del(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(k);
	}
	
	//通过ID查询出被修改的数据
	@RequestMapping("/getObjById")
	@ResponseBody
	public String getObjById(String id){
		User user=null;
		try {
			user=userService.getObjById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String json=JSONObject.toJSONString(user);
		return json;
	}
	
	//下载附件
	@RequestMapping("/downfile")
	public void downfile(String id,HttpServletResponse response){
		try {
			User user=userService.getObjById(id);
			String filepatname=user.getFilepath();
			
			String filename=filepatname.substring(filepatname.lastIndexOf("\\")+1,filepatname.length());
			File file=new File(filepatname);
			if(file==null || !file.exists()){
				return;
			}
			filename = new String(filename.getBytes(),"ISO8859-1");
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ filename);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            OutputStream out = response.getOutputStream();
            out.write(FileUtils.readFileToByteArray(file));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	//导出excel
	@RequestMapping("/exportExcel")
	@ResponseBody
	public void exportExcel(HttpServletRequest req,HttpServletResponse response){
		//查询条件
		String qname=req.getParameter("name");
		logger.info("查询条件qname=="+qname);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("name", qname);
		HSSFWorkbook wb=userService.exportExcel(map);
		
		String fileName = "用户信息表"+System.currentTimeMillis()+".xls";
        try {
			fileName = new String(fileName.getBytes(),"ISO8859-1");
			response.setContentType("application/octet-stream;charset=ISO8859-1");
	        response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
	        response.addHeader("Pargam", "no-cache");
	        response.addHeader("Cache-Control", "no-cache");
	        OutputStream os = response.getOutputStream();
	        wb.write(os);
	        os.flush();
	        os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//分配角色
	@RequestMapping("/getRoles")
	@ResponseBody
	public String getRoles(String userid){
		String json="";
		try {
			json=roleService.getRoles(userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	//保存用户 与 角色 到用户角色表
	@RequestMapping("/saveUserRole")
	@ResponseBody
	public String saveUserRole(String userid,String roleid){
		int k=0;
		try {
			k=userService.saveUserRole(userid,roleid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(k);
	}
	
	//修改密码
	@RequestMapping("/saveUpPwd")
	@ResponseBody
	public String saveUpPwd(String oldpass,String pass,HttpSession session){
		User user=(User)session.getAttribute("userinfo");
		int k=0;
		if(user!=null && StringUtils.isNotEmpty(user.getId())){
			try {
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("id", user.getId());
				map.put("oldpwd", MD5.encryptPassword(user.getName(), oldpass, null));
				map.put("pwd", MD5.encryptPassword(user.getName(), pass, null));
				k=userService.saveUpPwd(map);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return String.valueOf(k);
	}
}
