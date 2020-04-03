package com.huaxin.sboot.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.huaxin.sboot.bean.User;


public interface IMainServcie {
	
	//取柱形图的数据
	public String getChartcolumn()throws Exception;
	
	
	//取饼图数据
    public String getChartpie()throws Exception;
    
    //折线图
  	public String getChartline()throws Exception;

}
