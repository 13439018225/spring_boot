package com.huaxin.sboot.dao;

import java.util.List;
import java.util.Map;

import com.huaxin.sboot.bean.Message;

public interface IMessMapper {

	public List<Message> query(Map<String, Object> map) throws Exception;
}
