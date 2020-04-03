package com.huaxin.sboot.service;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.huaxin.sboot.bean.User;


public interface IMenuServcie {
	
	public String getTreeAuthMenu(User user) throws Exception;

}
