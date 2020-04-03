package com.huaxin.sboot.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huaxin.sboot.dao.IMainMapper;
import com.huaxin.sboot.service.IMainServcie;
@Service
public class MainServcieImpl implements IMainServcie {

	@Autowired
	private IMainMapper mainMapper;
	
	@Override
	public String getChartcolumn() throws Exception {
		List<Map<String, Object>> list = mainMapper.getChartcolumn();
		String json=JSONObject.toJSONStringWithDateFormat(list, "yyyy-MM-dd");
		json=json.toLowerCase();
		return json;
	}

	@Override
	public String getChartpie() throws Exception {
		List<Map<String, Object>> list = mainMapper.getChartpie();
		for(int i=0;i<list.size();i++){
			Map<String,Object> map=list.get(i);
			if(i==0){
				map.put("sliced", true);
				map.put("selected", true);
			}
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("title", "各省份投资者数量统计");
		map.put("list", list);
		String json=JSON.toJSONString(map).toLowerCase();
		return json;
	}

	@Override
	public String getChartline() throws Exception {
		List<Map<String, Object>> list = mainMapper.getChartline();
		String[] zh_months={"一月","二月","三月","四月","五月","六月","七月"};
		
		String[] months=new String[list.size()];
		BigDecimal[] values=new BigDecimal[list.size()];
		for(int i=0;i<list.size();i++){
			Map<String,Object> map=list.get(i);
			months[i]=(String)map.get("MONTH");
			values[i]=(BigDecimal)map.get("VALUE");
		}
		
		for(int i=0;i<months.length;i++){
			months[i]=zh_months[Integer.parseInt(months[i])-1];
		}
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("categories", months);
		map.put("data", values);
		String json=JSON.toJSONString(map);
		return json;
	}

}
