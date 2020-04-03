package com.huaxin.sboot.dao;

import java.util.List;
import java.util.Map;

public interface IMainMapper {

	//取柱形图的数据
	public List<Map<String,Object>> getChartcolumn()throws Exception;
	
	//取饼图数据
	public List<Map<String,Object>> getChartpie()throws Exception;
	
	//折线图
	public List<Map<String,Object>> getChartline()throws Exception;
}
